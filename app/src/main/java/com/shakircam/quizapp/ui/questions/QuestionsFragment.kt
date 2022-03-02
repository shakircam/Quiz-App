package com.shakircam.quizapp.ui.questions

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.shakircam.quizapp.R
import com.shakircam.quizapp.data.repository.QuestionRepository
import com.shakircam.quizapp.databinding.FragmentQuestionsBinding
import com.shakircam.quizapp.ui.viewmodel.QuestionsViewModel
import com.shakircam.quizapp.ui.viewmodel.QuestionsViewModelFactory
import com.shakircam.quizapp.utils.Resource
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit


class QuestionsFragment : Fragment() {
    private var _binding : FragmentQuestionsBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: QuestionsViewModel
    private val TAG = "QuestionsFragment"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentQuestionsBinding.inflate(inflater, container, false)

        val questionRepository = QuestionRepository()
        val viewModelProviderFactory = QuestionsViewModelFactory(questionRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(QuestionsViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.questionList.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is Resource.Success -> {
                   // hideProgressBar()
                    response.data?.let { newsResponse ->
                        Log.d(TAG, "An error occurred: $newsResponse")
                    }
                }
                is Resource.Error -> {
                   // hideProgressBar()
                    response.message?.let { message ->
                        Log.e(TAG, "An error occurred: $message")
                    }
                }
                is Resource.Loading -> {
                   // showProgressBar()
                }
            }
        })
    }





    fun Question(){
       /* CoroutineScope(Dispatchers.Main).launch {

            for (i in questionList.indices){
                binding.questionTx.text = questionList[i]
                waitForTime()

                withContext(Dispatchers.Main) {
                    Log.i("TAG", "this will be called after 3 seconds")

                }
            }
        }*/
    }

    private suspend fun waitForTime(){
        delay(TimeUnit.SECONDS.toMillis(5))
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}