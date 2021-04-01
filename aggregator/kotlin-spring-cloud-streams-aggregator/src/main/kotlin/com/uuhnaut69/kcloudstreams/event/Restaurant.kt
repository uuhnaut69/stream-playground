package com.uuhnaut69.kcloudstreams.event

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class Restaurant(
    val id: String,
    val name: String,
    val address: String,
    @JsonProperty(value = "created_date") val createdDate: Long
)
