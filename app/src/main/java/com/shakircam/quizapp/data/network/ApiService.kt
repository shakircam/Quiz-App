package com.shakircam.quizapp.data.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.shakircam.quizapp.utils.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class ApiService {

    companion object{

        val gson: Gson = GsonBuilder()
            .setLenient()
            .create()

        private val retrofit by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create().asLenient())
                .build()
        }
        val api by lazy {
            retrofit.create(ApiInterface::class.java)

        }
    }
}