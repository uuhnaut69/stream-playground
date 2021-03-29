package com.uuhnaut69.kover.service.impl

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.convertValue
import com.uuhnaut69.kover.model.Restaurant
import com.uuhnaut69.kover.repository.RestaurantRepository
import com.uuhnaut69.kover.resource.dto.RestaurantRequest
import com.uuhnaut69.kover.resource.exception.NotFoundException
import com.uuhnaut69.kover.service.RestaurantService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class RestaurantServiceImpl(
    private val objectMapper: ObjectMapper,
    private val restaurantRepository: RestaurantRepository
) : RestaurantService {

    @Transactional(readOnly = true)
    override fun findAll(): List<Restaurant> = restaurantRepository.findAll()

    @Transactional(readOnly = true)
    override fun findById(restaurantId: UUID): Restaurant =
        restaurantRepository.findById(restaurantId).orElseThrow { NotFoundException() }

    override fun create(restaurantRequest: RestaurantRequest): Restaurant {
        val restaurant = objectMapper.convertValue<Restaurant>(restaurantRequest)
        return restaurantRepository.save(restaurant)
    }

    override fun update(restaurantId: UUID, restaurantRequest: RestaurantRequest): Restaurant {
        val restaurant = findById(restaurantId)
        restaurant.name = restaurantRequest.name
        restaurant.address = restaurantRequest.address
        return restaurantRepository.save(restaurant)
    }

    override fun delete(restaurantId: UUID) {
        restaurantRepository.deleteById(restaurantId)
    }
}