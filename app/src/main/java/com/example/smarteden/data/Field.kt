package com.example.smarteden.data

data class Field (
    val id: String,
    val humidity: Int,
    val plant: String,
    val planted: Long,
    val duration: Number,
    val watering: Boolean,
    val requiredHumidity: Number,
    val requiredFrequencyHumidity: Number
    )
