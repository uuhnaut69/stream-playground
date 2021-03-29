package com.uuhnaut69.kover.service

import com.uuhnaut69.kover.model.Restaurant
import com.uuhnaut69.kover.resource.dto.RestaurantRequest
import java.util.*

interface RestaurantService {

    fun findAll(): List<Restaurant>

    fun findById(restaurantId: UUID): Restaurant

    fun create(restaurantRequest: RestaurantRequest): Restaurant

    fun update(restaurantId: UUID, restaurantRequest: RestaurantRequest): Restaurant

    fun delete(restaurantId: UUID)
}