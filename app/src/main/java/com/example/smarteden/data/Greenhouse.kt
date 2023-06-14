package com.example.smarteden.data

data class Greenhouse(
    val id: String,
    val name: String,
    val temperature: Double,
    val humidity: Int,
    val rollo: String,
    val boost: Boolean,
    val window: String,
    val fan: String
    )
