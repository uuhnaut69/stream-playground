package com.uuhnaut69.kcloudstreams.processor

import com.uuhnaut69.kcloudstreams.event.Dish
import com.uuhnaut69.kcloudstreams.event.DishWithRestaurant
import com.uuhnaut69.kcloudstreams.event.Restaurant
import com.uuhnaut69.kcloudstreams.event.RestaurantWithDishes
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.KeyValue
import org.apache.kafka.streams.kstream.Grouped
import org.apache.kafka.streams.kstream.KStream
import org.apache.kafka.streams.kstream.KTable
import org.apache.kafka.streams.kstream.Materialized
import org.springframework.context.annotation.Bean
import org.springframework.kafka.support.serializer.JsonSerde
import org.springframework.stereotype.Component
import java.util.function.BiFunction

@Component
class EventProcessor {

    @Bean
    fun process(): BiFunction<KTable<String, Dish>, KTable<String, Restaurant>, KStream<String, RestaurantWithDishes>> =
        BiFunction { dishes: KTable<String, Dish>, restaurants: KTable<String, Restaurant> ->
            dishes.join(
                restaurants, { it.restaurantId },
                { dish, restaurant -> DishWithRestaurant(dish, restaurant) },
                Materialized.with(Serdes.String(), JsonSerde(DishWithRestaurant::class.java))
            ).groupBy({ dishId, dishWithRestaurant ->
                KeyValue.pair(
                    dishWithRestaurant.restaurant.id,
                    dishWithRestaurant
                )
            }, Grouped.with(Serdes.String(), JsonSerde(DishWithRestaurant::class.java))).aggregate(
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
                Materialized.with(Serdes.String(), JsonSerde(RestaurantWithDishes::class.java))
            ).toStream()
        }
}