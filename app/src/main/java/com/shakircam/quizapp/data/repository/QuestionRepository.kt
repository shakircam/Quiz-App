package com.shakircam.quizapp.data.repository

import com.shakircam.quizapp.data.network.ApiService

class QuestionRepository {
    suspend fun getQuestions()=
        ApiService.api.getQuestions()
}