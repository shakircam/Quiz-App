package com.shakircam.quizapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shakircam.quizapp.data.repository.QuestionRepository
import com.shakircam.quizapp.model.QuestionList
import com.shakircam.quizapp.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class QuestionsViewModel(private val questionRepository: QuestionRepository)
    : ViewModel() {

    val questionList: MutableLiveData<Resource<QuestionList>> = MutableLiveData()

    init {
        getQuestionList()
    }

    private fun getQuestionList() = viewModelScope.launch {
        questionList.postValue(Resource.Loading())
        val response = questionRepository.getQuestions()
        questionList.postValue(handleQuestionListResponse(response))
    }

    private fun handleQuestionListResponse(response: Response<QuestionList>) : Resource<QuestionList> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

}