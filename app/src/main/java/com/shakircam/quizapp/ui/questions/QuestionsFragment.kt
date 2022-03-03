package com.shakircam.quizapp.ui.questions

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.shakircam.quizapp.R
import com.shakircam.quizapp.data.repository.QuestionRepository
import com.shakircam.quizapp.databinding.FragmentQuestionsBinding
import com.shakircam.quizapp.model.QuestionList
import com.shakircam.quizapp.ui.viewmodel.QuestionsViewModel
import com.shakircam.quizapp.ui.viewmodel.QuestionsViewModelFactory
import com.shakircam.quizapp.utils.Resource
import java.util.*


class QuestionsFragment : Fragment() {
    private var _binding : FragmentQuestionsBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: QuestionsViewModel
    private val TAG = "QuestionsFragment"
    private val list = mutableListOf<QuestionList.Question>()
    var number = 0
    private var mTimerRunning = false
    lateinit var mCountDownTimer: CountDownTimer

    companion object{
        const val START_TIME_IN_MILLIS = 15000
    }

    private var mTimeLeftInMillis = START_TIME_IN_MILLIS.toLong()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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

                    response.data?.let { questionResponse ->
                        list.addAll(questionResponse.questions)
                       // list.shuffle()
                        Log.d(TAG, "Success: $questionResponse")
                        setQuestionToUi()
                    }
                }
                is Resource.Error -> {

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

    private fun startTimer(){

        mCountDownTimer = object : CountDownTimer(mTimeLeftInMillis,1000) {
            override fun onTick(millisUntilFinished: Long) {
                mTimeLeftInMillis = millisUntilFinished
                updateCountDownText()
            }

            override fun onFinish() {
                Log.d("this","finish the time")
            }

        }.start()

        mTimerRunning = true
    }


    fun updateCountDownText(){
        val minutes = (mTimeLeftInMillis / 1000).toInt() / 60
        val seconds = (mTimeLeftInMillis / 1000).toInt() % 60

        val timeLeftFormatted: String =
            java.lang.String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
        binding.countDownTimeTx.text = timeLeftFormatted
    }


    @SuppressLint("SetTextI18n")
    fun setQuestionToUi(){

        for (i in list.indices){
            binding.questionTx.text = list[i].question
            binding.questionPoint.text = list[i].score.toString()+" point"
            Glide.with(binding.questionPic)
                .load(list[i].questionImageUrl)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(binding.questionPic)

            binding.optionOne.text = list[i].answers.A
            binding.optionTwo.text = list[i].answers.B
            binding.optionThree.text = list[i].answers.C
            binding.optionFour.text = list[i].answers.D
            val i = i+1
            binding.questionNumberTv.text = i.toString()+"/"+"${list.size}"


            binding.optionOneItem.setOnClickListener {
                if (list[i].correctAnswer == "A"){
                    number += list[i].score
                    binding.scoreNumberTv.text = "Score: "+number.toString()
                    binding.cardOptionOne.setBackgroundColor(resources.getColor(R.color.green))
                }else{
                    binding.cardOptionOne.setBackgroundColor(resources.getColor(R.color.red))
                }
            }
            binding.optionTwoItem.setOnClickListener {
                if (list[i].correctAnswer == "B"){
                    number += list[i].score
                    binding.scoreNumberTv.text = "Score: $number"
                    binding.cardOptionTwo.setBackgroundColor(resources.getColor(R.color.green))
                }else{
                    binding.cardOptionTwo.setBackgroundColor(resources.getColor(R.color.red))
                }
            }
            binding.optionThreeItem.setOnClickListener {
                if (list[i].correctAnswer == "C"){
                    number += list[i].score
                    binding.scoreNumberTv.text = "Score: $number"
                    binding.cardOptionThree.setBackgroundColor(resources.getColor(R.color.green))
                }else{
                    binding.cardOptionThree.setBackgroundColor(resources.getColor(R.color.red))
                }
            }
            binding.optionFourItem.setOnClickListener {
                if (list[i].correctAnswer == "D"){
                    number += list[i].score
                    binding.scoreNumberTv.text = "Score: $number"
                    binding.cardOptionFour.setBackgroundColor(resources.getColor(R.color.green))
                }else{
                    binding.cardOptionFour.setBackgroundColor(resources.getColor(R.color.red))
                }
            }

            startTimer()
            //after waiting time card background will be white...
            binding.cardOptionOne.setBackgroundColor(resources.getColor(R.color.white))
            binding.cardOptionTwo.setBackgroundColor(resources.getColor(R.color.white))
            binding.cardOptionThree.setBackgroundColor(resources.getColor(R.color.white))
            binding.cardOptionFour.setBackgroundColor(resources.getColor(R.color.white))
        }
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}