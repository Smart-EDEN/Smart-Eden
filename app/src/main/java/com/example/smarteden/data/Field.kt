package com.example.smarteden.data

data class Field (
    val id: String,
    val humidity: Number,
    val plant: String,
    val planted: Long,
    val growDurationMonths: Number,
    val watering: Boolean,
    val requiredHumidity: Number,
    val requiredFrequencyHumidity: Number
    )
