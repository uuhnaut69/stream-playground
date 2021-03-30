package com.uuhnaut69.kafkastream.processor;

import com.uuhnaut69.kafkastream.event.Dish;
import com.uuhnaut69.kafkastream.event.DishWithRestaurant;
import com.uuhnaut69.kafkastream.event.Restaurant;
import com.uuhnaut69.kafkastream.event.RestaurantWithDishes;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

@Component
public class EventProcessor {

  @Value("${topic.dishes-input}")
  private String dishesInput;

  @Value("${topic.restaurants-input}")
  private String restaurantsInput;

  @Value("${topic.enriched-restaurants-output}")
  private String enrichedRestaurantsOutput;

  @Autowired
  public void process(StreamsBuilder streamsBuilder) {
    var dishJsonSerde = new JsonSerde<>(Dish.class);
    var restaurantJsonSerde = new JsonSerde<>(Restaurant.class);
    var dishWithRestaurantJsonSerde = new JsonSerde<>(DishWithRestaurant.class);
    var restaurantWithDishesJsonSerde = new JsonSerde<>(RestaurantWithDishes.class);

    var dishes = streamsBuilder.table(dishesInput, Consumed.with(Serdes.String(), dishJsonSerde));

    var restaurants =
        streamsBuilder.table(restaurantsInput, Consumed.with(Serdes.String(), restaurantJsonSerde));

    dishes
        .join(
            restaurants,
            Dish::getRestaurantId,
            DishWithRestaurant::new,
            Materialized.with(Serdes.String(), dishWithRestaurantJsonSerde))
        .groupBy(
            (dishId, dishWithRestaurant) ->
                KeyValue.pair(dishWithRestaurant.getRestaurant().getId(), dishWithRestaurant),
            Grouped.with(Serdes.String(), dishWithRestaurantJsonSerde))
        .aggregate(
            RestaurantWithDishes::new,
            (restaurantId, dishWithRestaurant, aggregate) -> aggregate.addDish(dishWithRestaurant),
            (restaurantId, dishWithRestaurant, aggregate) ->
                aggregate.removeDish(dishWithRestaurant),
            Materialized.with(Serdes.String(), restaurantWithDishesJsonSerde))
        .toStream()
        .to(
            enrichedRestaurantsOutput,
            Produced.with(Serdes.String(), restaurantWithDishesJsonSerde));
  }
}
