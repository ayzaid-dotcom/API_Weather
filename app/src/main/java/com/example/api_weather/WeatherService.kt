package com.example.api_weather

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherService {
    companion object{
        const val API_KEY="ed3694e25ff852c113c84271c38b6b9c"
    }



    @GET("?units=metric&appid=$API_KEY")
    fun getWeatherByCity(@Query("q") city: String): Call<WeatherResult>

}