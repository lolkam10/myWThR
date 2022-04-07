package com.example.mywthr.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywthr.Api.DataClasses.WeatherData
import com.example.mywthr.Api.WeatherRepo
import kotlinx.coroutines.launch

const val MY_KEY="8f3cf4283c4ac37bac7e6933ed160d38"

class VM : ViewModel() {
    private val _all_data : MutableLiveData<WeatherData> = MutableLiveData()
    val all_data : LiveData<WeatherData>
    get() {
        return _all_data
    }

    fun getDataForCity(name : String){
        viewModelScope.launch {
            val newData = WeatherRepo.getDataByCity(name, MY_KEY)
            if (newData != null){
                _all_data.value = newData!!
            }
        }
    }
    fun convertDate(unixUTC: Long,timezone:Int):List<String>{
        val date = java.time.format.DateTimeFormatter.ISO_INSTANT.format(java.time.Instant.ofEpochSecond(unixUTC+timezone))
        return listOf<String>(date.substring(0,10),date.substring(11,16))
    }
}