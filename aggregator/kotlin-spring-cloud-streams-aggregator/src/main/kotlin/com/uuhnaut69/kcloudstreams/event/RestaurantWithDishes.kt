package com.uuhnaut69.kcloudstreams.event

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
class RestaurantWithDishes {

    var restaurant: Restaurant? = null

    var dishes: MutableSet<Dish> = mutableSetOf()

    fun addDish(dishWithRestaurant: DishWithRestaurant): RestaurantWithDishes {
        restaurant = dishWithRestaurant.restaurant
        dishes.add(dishWithRestaurant.dish)
        return this
    }

    fun removeDish(dishWithRestaurant: DishWithRestaurant): RestaurantWithDishes {
        dishes.removeIf { it.id == dishWithRestaurant.dish.id }
        return this
    }
}