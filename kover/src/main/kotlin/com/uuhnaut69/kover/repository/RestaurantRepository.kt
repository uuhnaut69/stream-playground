package com.uuhnaut69.kover.repository

import com.uuhnaut69.kover.model.Restaurant
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RestaurantRepository : JpaRepository<Restaurant, UUID> {

    @EntityGraph(attributePaths = ["dishes"])
    override fun findAll(): List<Restaurant>
}