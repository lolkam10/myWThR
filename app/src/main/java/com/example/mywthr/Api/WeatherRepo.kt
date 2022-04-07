package com.example.mywthr.Api

import com.example.mywthr.Api.DataClasses.WeatherData
import retrofit2.awaitResponse

class WeatherRepo {

    companion object{
        suspend fun getDataByCity(api_key:String, city_name:String): WeatherData? {
            return RetrofitInstance.api.getDataByCity(api_key,city_name).awaitResponse().body()
        }
    }

}