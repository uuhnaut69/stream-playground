package com.uuhnaut69.kstreams.processor

import com.uuhnaut69.kstreams.event.Dish
import com.uuhnaut69.kstreams.event.DishWithRestaurant
import com.uuhnaut69.kstreams.event.Restaurant
import com.uuhnaut69.kstreams.event.RestaurantWithDishes
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.KeyValue
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.kstream.Consumed
import org.apache.kafka.streams.kstream.Grouped
import org.apache.kafka.streams.kstream.Materialized
import org.apache.kafka.streams.kstream.Produced
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.support.serializer.JsonSerde
import org.springframework.stereotype.Component

@Component
class EventProcessor {

    @Value("\${topic.dishes-input}")
    lateinit var dishesInput: String

    @Value("\${topic.restaurants-input}")
    lateinit var restaurantsInput: String

    @Value("\${topic.enriched-restaurants-output}")
    lateinit var enrichedRestaurantsOutput: String

    @Autowired
    fun process(streamsBuilder: StreamsBuilder) {
        val dishJsonSerde = JsonSerde(Dish::class.java)
        val restaurantJsonSerde = JsonSerde(Restaurant::class.java)
        val dishWithRestaurantJsonSerde = JsonSerde(DishWithRestaurant::class.java)
        val restaurantWithDishesJsonSerde = JsonSerde(RestaurantWithDishes::class.java)

        val dishes = streamsBuilder
            .table(dishesInput, Consumed.with(Serdes.String(), dishJsonSerde))

        val restaurants = streamsBuilder
            .table(restaurantsInput, Consumed.with(Serdes.String(), restaurantJsonSerde))

        dishes.join(
            restaurants,
            { it.restaurantId },
            { dish, restaurant -> DishWithRestaurant(dish, restaurant) },
            Materialized.with(Serdes.String(), dishWithRestaurantJsonSerde)
        ).groupBy({ dishId, dishWithRestaurant ->
            KeyValue.pair(
                dishWithRestaurant.restaurant.id,
                dishWithRestaurant
            )
        }, Grouped.with(Serdes.String(), dishWithRestaurantJsonSerde))
            .aggregate(
                { RestaurantWithDishes() },
                { restaurantId: String, dishWithRestaurant: DishWithRestaurant, aggregate: RestaurantWithDishes ->
                    aggregate.addDish(
                        dishWithRestaurant
                    )
                },
                { restaurantId: String, dishWithRestaurant: DishWithRestaurant, aggregate: RestaurantWithDishes ->
                    aggregate.removeDish(
                        dishWithRestaurant
                    )
                },
                Materialized.with(Serdes.String(), restaurantWithDishesJsonSerde)
            ).toStream().to(enrichedRestaurantsOutput, Produced.with(Serdes.String(), restaurantWithDishesJsonSerde))
    }
}