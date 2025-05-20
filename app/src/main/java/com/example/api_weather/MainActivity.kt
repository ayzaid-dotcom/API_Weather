package com.example.api_weather

import android.health.connect.datatypes.units.Temperature
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


class MainActivity : AppCompatActivity() {

    lateinit var editCityName: EditText
    lateinit var btnSearch: Button
    lateinit var imageWeather: ImageView
    lateinit var tvTemperature: TextView
    lateinit var tvCityName: TextView
    lateinit var layoutWeather: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editCityName=findViewById(R.id.editCity)
        btnSearch=findViewById(R.id.btnSearch)
        imageWeather=findViewById(R.id.imageWeather)
        tvTemperature=findViewById(R.id.tvTemperature)
        tvCityName=findViewById(R.id.tvCityName)
        layoutWeather=findViewById(R.id.layoutWeather)

        btnSearch.setOnClickListener {
            val city=editCityName.text.toString()
            if(city.isEmpty()){
                Toast.makeText(this,"City name can't be empty !!", Toast.LENGTH_SHORT).show()
            }else{
                getWeatherByCity(city)
            }

        }

    }
    fun getWeatherByCity(city: String){
        // TODO1: create retrofit instance
        val retrofit= Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/weather/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val weatherService=retrofit.create(WeatherService::class.java)


        // TODO2: call weather api
        val result=weatherService.getWeatherByCity(city)

        result.enqueue(object : Callback<WeatherResult>{
            override fun onResponse(call: Call<WeatherResult>, response: Response<WeatherResult>) {
                if (response.isSuccessful){
                    val result=response.body()

                    Picasso.get().load("https://openweathermap.org/img/w/${result?.weather?.get(0)?.icon}.png").into(imageWeather)


                    tvTemperature.text="${result?.main?.temp} °C"
                    tvCityName.text= result?.name
                    layoutWeather.visibility= View.VISIBLE
                }

            }

            override fun onFailure(call: Call<WeatherResult>, t: Throwable) {
                Toast.makeText(applicationContext,"Erreur serveur", Toast.LENGTH_SHORT).show()
            }


        })
    }
}