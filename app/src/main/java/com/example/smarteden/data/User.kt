package com.example.smarteden.data

data class User(
    private val id: String,
    private val name: String,
    private val email: String,
    private val greenhouseIds: ArrayList<String>
    )
