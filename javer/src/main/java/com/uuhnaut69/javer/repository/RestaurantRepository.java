package com.uuhnaut69.javer.repository;

import com.uuhnaut69.javer.model.Restaurant;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {

  @EntityGraph(attributePaths = {"dishes"})
  List<Restaurant> findAll();
}
