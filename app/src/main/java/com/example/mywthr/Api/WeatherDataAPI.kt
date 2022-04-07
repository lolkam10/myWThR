package com.example.mywthr.Api

import com.example.mywthr.Api.DataClasses.WeatherData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface WeatherDataAPI {
    @GET("weather?units=metric&lang=pl")
    fun getDataByCity(@Query("q") city_name : String, @Query("appid") appid : String): Call<WeatherData>
}