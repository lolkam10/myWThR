package com.example.mywthr.Api.DataClasses

data class WeatherData(
    val coord: Coord,
    val weather: Array<Weather>,
    val base: String,
    val main: Main,
    val visibility: Int,
    val wind: Wind,
    val clouds: Clouds,
    val dt: Long,
    val sys: Sys,
    val timezone: Int,
    val id: Long,
    val name: String,
    val cod: Int
)