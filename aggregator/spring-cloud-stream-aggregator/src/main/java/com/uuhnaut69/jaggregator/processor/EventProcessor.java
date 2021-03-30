package com.uuhnaut69.jaggregator.processor;

import com.uuhnaut69.jaggregator.event.Dish;
import com.uuhnaut69.jaggregator.event.DishWithRestaurant;
import com.uuhnaut69.jaggregator.event.Restaurant;
import com.uuhnaut69.jaggregator.event.RestaurantWithDishes;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
public class EventProcessor {

  @Bean
  public BiFunction<
          KTable<String, Dish>, KTable<String, Restaurant>, KStream<String, RestaurantWithDishes>>
      process() {
    return (dishes, restaurants) ->
        dishes
            .join(
                restaurants,
                Dish::getRestaurantId,
                DishWithRestaurant::new,
                Materialized.with(Serdes.String(), new JsonSerde<>(DishWithRestaurant.class)))
            .groupBy(
                (dishId, dishWithRestaurant) ->
                    KeyValue.pair(dishWithRestaurant.getRestaurant().getId(), dishWithRestaurant),
                Grouped.with(Serdes.String(), new JsonSerde<>(DishWithRestaurant.class)))
            .aggregate(
                RestaurantWithDishes::new,
                (restaurantId, dishWithRestaurant, aggregate) ->
                    aggregate.addDish(dishWithRestaurant),
                (restaurantId, dishWithRestaurant, aggregate) ->
                    aggregate.removeDish(dishWithRestaurant),
                Materialized.with(Serdes.String(), new JsonSerde<>(RestaurantWithDishes.class)))
            .toStream();
  }
}
