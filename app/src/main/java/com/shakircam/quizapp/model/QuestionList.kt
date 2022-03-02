package com.shakircam.quizapp.model

data class QuestionList(
    val questions: List<Question>
) {
    data class Question(
        val answers: Answers,
        val correctAnswer: String,
        val question: String,
        val questionImageUrl: Any,
        val score: Int
    ) {
        data class Answers(
            val A: String,
            val B: String,
            val C: String,
            val D: String
        )
    }
}