package com.uuhnaut69.kover.service

import com.uuhnaut69.kover.model.Dish
import com.uuhnaut69.kover.resource.dto.DishRequest
import java.util.*

interface DishService {

    fun findAllByRestaurantId(restaurantId: UUID): List<Dish>

    fun create(restaurantId: UUID, dishRequest: DishRequest): Dish

    fun update(restaurantId: UUID, dishId: UUID, dishRequest: DishRequest): Dish

    fun delete(restaurantId: UUID, dishId: UUID)
}