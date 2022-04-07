package com.example.mywthr.Api.DataClasses

data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)