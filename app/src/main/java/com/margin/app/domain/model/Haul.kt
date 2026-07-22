package com.margin.app.domain.model

data class Haul(
    val id: Long = 0,
    val name: String,
    val date: Long = System.currentTimeMillis(),
    val notes: String = "",
    val itemCount: Int = 0,
    val totalSpent: Double = 0.0
)
