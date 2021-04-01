package com.uuhnaut69.kcloudstreams.event

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.math.BigDecimal

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class Dish(
    val id: String,
    val name: String,
    val price: BigDecimal,
    val createdDate: Long,
    val restaurantId: String
)
