package com.example.smarteden.data

data class User(
    val id: String,
    val name: String,
    val email: String,
    val greenhouseIds: ArrayList<String>
    )
