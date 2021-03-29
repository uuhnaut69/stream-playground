package com.uuhnaut69.kover.resource

import com.uuhnaut69.kover.model.Dish
import com.uuhnaut69.kover.model.Restaurant
import com.uuhnaut69.kover.resource.dto.DishRequest
import com.uuhnaut69.kover.resource.dto.RestaurantRequest
import com.uuhnaut69.kover.service.DishService
import com.uuhnaut69.kover.service.RestaurantService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/restaurants")
class RestaurantResource(private val dishService: DishService, private val restaurantService: RestaurantService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createRestaurant(@RequestBody @Valid restaurantRequest: RestaurantRequest): Restaurant =
        restaurantService.create(restaurantRequest)

    @GetMapping
    fun findAllRestaurants(): List<Restaurant> = restaurantService.findAll()

    @PutMapping("/{restaurantId}")
    fun updateRestaurant(
        @PathVariable restaurantId: UUID,
        @RequestBody @Valid restaurantRequest: RestaurantRequest
    ): Restaurant = restaurantService.update(restaurantId, restaurantRequest)

    @DeleteMapping("/{restaurantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteRestaurant(
        @PathVariable restaurantId: UUID
    ) = restaurantService.delete(restaurantId)

    @PostMapping("/{restaurantId}/dishes")
    @ResponseStatus(HttpStatus.CREATED)
    fun createDish(@PathVariable restaurantId: UUID, @RequestBody @Valid dishRequest: DishRequest): Dish =
        dishService.create(restaurantId, dishRequest)

    @PutMapping("/{restaurantId}/dishes/{dishId}")
    fun updateDish(
        @PathVariable restaurantId: UUID,
        @PathVariable dishId: UUID,
        @RequestBody @Valid dishRequest: DishRequest
    ): Dish = dishService.update(restaurantId, dishId, dishRequest)

    @DeleteMapping("/{restaurantId}/dishes/{dishId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteDish(
        @PathVariable restaurantId: UUID,
        @PathVariable dishId: UUID
    ) = dishService.delete(restaurantId, dishId)

    @GetMapping("/{restaurantId}/dishes")
    fun findAllByRestaurantId(@PathVariable restaurantId: UUID): List<Dish> =
        dishService.findAllByRestaurantId(restaurantId)
}