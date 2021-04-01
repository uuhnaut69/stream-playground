package com.uuhnaut69.kcloudstreams.event

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class DishWithRestaurant(val dish: Dish, val restaurant: Restaurant)