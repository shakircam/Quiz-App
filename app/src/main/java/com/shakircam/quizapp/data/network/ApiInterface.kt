package com.shakircam.quizapp.data.network

import com.shakircam.quizapp.model.QuestionList
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    @GET("b/610267742ccb97077c144483/1")
   suspend fun getQuestions() : Response<QuestionList>
}