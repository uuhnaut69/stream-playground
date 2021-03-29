package com.uuhnaut69.javer.resource;

import com.uuhnaut69.javer.model.Dish;
import com.uuhnaut69.javer.model.Restaurant;
import com.uuhnaut69.javer.resource.dto.DishRequest;
import com.uuhnaut69.javer.resource.dto.RestaurantRequest;
import com.uuhnaut69.javer.service.DishService;
import com.uuhnaut69.javer.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurants")
public class RestaurantResource {

  private final DishService dishService;

  private final RestaurantService restaurantService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Restaurant createRestaurant(@RequestBody @Valid RestaurantRequest restaurantRequest) {
    return restaurantService.create(restaurantRequest);
  }

  @GetMapping
  public List<Restaurant> findAllRestaurants() {
    return restaurantService.findAll();
  }

  @PutMapping("/{restaurantId}")
  public Restaurant updateRestaurant(
      @PathVariable UUID restaurantId, @RequestBody @Valid RestaurantRequest restaurantRequest) {
    return restaurantService.update(restaurantId, restaurantRequest);
  }

  @DeleteMapping("/{restaurantId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteRestaurant(@PathVariable UUID restaurantId) {
    restaurantService.delete(restaurantId);
  }

  @PostMapping("/{restaurantId}/dishes")
  @ResponseStatus(HttpStatus.CREATED)
  public Dish createDish(
      @PathVariable UUID restaurantId, @RequestBody @Valid DishRequest dishRequest) {
    return dishService.create(restaurantId, dishRequest);
  }

  @PutMapping("/{restaurantId}/dishes/{dishId}")
  public Dish updateDish(
      @PathVariable UUID restaurantId,
      @PathVariable UUID dishId,
      @RequestBody @Valid DishRequest dishRequest) {
    return dishService.update(restaurantId, dishId, dishRequest);
  }

  @DeleteMapping("/{restaurantId}/dishes/{dishId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteDish(@PathVariable UUID restaurantId, @PathVariable UUID dishId) {
    dishService.delete(restaurantId, dishId);
  }

  @GetMapping("/{restaurantId}/dishes")
  public List<Dish> findAllByRestaurantId(@PathVariable UUID restaurantId) {
    return dishService.findAllByRestaurantId(restaurantId);
  }
}
