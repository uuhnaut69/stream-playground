package com.uuhnaut69.jaggregator.event;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.HashSet;
import java.util.Set;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class RestaurantWithDishes {

  private Restaurant restaurant;

  private Set<Dish> dishes = new HashSet<>();

  public RestaurantWithDishes addDish(DishWithRestaurant dishWithRestaurant) {
    restaurant = dishWithRestaurant.getRestaurant();
    dishes.add(dishWithRestaurant.getDish());
    return this;
  }

  public RestaurantWithDishes removeDish(DishWithRestaurant dishWithRestaurant) {
    dishes.removeIf(dish -> dish.getId().equals(dishWithRestaurant.getDish().getId()));
    return this;
  }
}
