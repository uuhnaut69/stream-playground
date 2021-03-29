package com.uuhnaut69.kover.service.impl

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.convertValue
import com.uuhnaut69.kover.model.Dish
import com.uuhnaut69.kover.repository.DishRepository
import com.uuhnaut69.kover.resource.dto.DishRequest
import com.uuhnaut69.kover.resource.exception.NotFoundException
import com.uuhnaut69.kover.service.DishService
import com.uuhnaut69.kover.service.RestaurantService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class DishServiceImpl(
    private val objectMapper: ObjectMapper,
    private val dishRepository: DishRepository,
    private val restaurantService: RestaurantService
) : DishService {

    @Transactional(readOnly = true)
    override fun findAllByRestaurantId(restaurantId: UUID): List<Dish> =
        dishRepository.findAllByRestaurantId(restaurantId)

    override fun create(restaurantId: UUID, dishRequest: DishRequest): Dish {
        val restaurant = restaurantService.findById(restaurantId)
        val dish = objectMapper.convertValue<Dish>(dishRequest)
        dish.restaurant = restaurant
        return dishRepository.save(dish)
    }

    override fun update(restaurantId: UUID, dishId: UUID, dishRequest: DishRequest): Dish {
        val dish = dishRepository.findById(dishId).orElseThrow { NotFoundException() }
        dish.name = dishRequest.name
        dish.price = dishRequest.price
        return dishRepository.save(dish)
    }

    override fun delete(restaurantId: UUID, dishId: UUID) {
        dishRepository.deleteById(dishId)
    }
}