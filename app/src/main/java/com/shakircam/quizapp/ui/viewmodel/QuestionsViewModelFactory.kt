package com.shakircam.quizapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shakircam.quizapp.data.repository.QuestionRepository

class QuestionsViewModelFactory(val newsRepository: QuestionRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return QuestionsViewModel(newsRepository) as T
    }

}