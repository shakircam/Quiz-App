package com.shakircam.quizapp.ui.questions

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.shakircam.quizapp.MainActivity
import com.shakircam.quizapp.R
import com.shakircam.quizapp.data.repository.QuestionRepository
import com.shakircam.quizapp.databinding.FragmentQuestionsBinding
import com.shakircam.quizapp.model.QuestionList
import com.shakircam.quizapp.ui.home.HomeFragmentDirections
import com.shakircam.quizapp.ui.viewmodel.QuestionsViewModel
import com.shakircam.quizapp.ui.viewmodel.QuestionsViewModelFactory
import com.shakircam.quizapp.utils.AppPreference
import com.shakircam.quizapp.utils.AppPreferenceImpl
import com.shakircam.quizapp.utils.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*


class QuestionsFragment : Fragment() {
    private var _binding : FragmentQuestionsBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: QuestionsViewModel
    private val TAG = "QuestionsFragment"
    private val list = mutableListOf<QuestionList.Question>()
    private var mTimerRunning = false
    lateinit var mCountDownTimer: CountDownTimer
    private var mTimeLeftInMillis = START_TIME_IN_MILLIS.toLong()
    var flag = 0
    var number = 0
    private var questionCounter = 0
    private var questionCountTotal = 0
    private var currentQuestion: QuestionList.Question? = null
    companion object{
        const val START_TIME_IN_MILLIS = 60000L
    }
    private lateinit var appPreference: AppPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentQuestionsBinding.inflate(inflater, container, false)
        appPreference = AppPreferenceImpl(requireActivity())
        val questionRepository = QuestionRepository()
        val viewModelProviderFactory = QuestionsViewModelFactory(questionRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(QuestionsViewModel::class.java)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.questionList.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {

                    response.data?.let { questionResponse ->
                        list.addAll(questionResponse.questions)
                        // list.shuffle()
                        Log.d(TAG, "Success: $questionResponse")
                        showNextQuestion()
                       // clickToQuestion()

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
        }

       /* binding.nextBt.setOnClickListener {
            saveHighestScore()
            val action = QuestionsFragmentDirections.actionQuestionsFragmentToHomeFragment()
            view.findNavController().navigate(action)
            activity?.finish()
        }*/

        binding.nextBt.setOnClickListener{
            showNextQuestion()
            clickToQuestion()
        }
    }


    @SuppressLint("SetTextI18n")
    private fun showNextQuestion() {

        questionCountTotal = list.size
        if (questionCounter < questionCountTotal) {
            enabledOption()
            currentQuestion = list[questionCounter]
            binding.questionTx.text = currentQuestion!!.question
            binding.questionPoint.text= currentQuestion!!.score.toString()
            binding.optionOne.text = currentQuestion!!.answers.A
            binding.optionTwo.text = currentQuestion!!.answers.B
            binding.optionThree.text = currentQuestion!!.answers.C
            binding.optionFour.text = currentQuestion!!.answers.D
            Log.d("this", " timer status::$mTimerRunning")
            if (!mTimerRunning){
                startTimer()
                Log.d("this","first check for start timer")
            }
            if (flag == 1){
                Log.d("this","timer flag is 1")
                mCountDownTimer.start()
            }
            questionCounter++
            binding.questionNumberTv.text = "Question: $questionCounter/$questionCountTotal"
            if (questionCounter == list.size){

                lifecycleScope.launch {
                    delay(60000)
                    hideOption()
                }
            }
        } else {
            mCountDownTimer.cancel()
            Toast.makeText(requireActivity(),"finish the task",Toast.LENGTH_SHORT).show()
        }
    }



   /* @SuppressLint("SetTextI18n")
    private  fun  clickToQuestion(){
        binding.optionOneItem.setOnClickListener {

            if (currentQuestion!!.correctAnswer == "A"){
                number += currentQuestion!!.score
                binding.scoreNumberTv.text = "Score: $number"
                binding.cardOptionOne.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.green))

            }else{
                binding.cardOptionOne.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.red))
                when (currentQuestion!!.correctAnswer) {
                    "B" -> {
                        binding.cardOptionOne.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.green))
                    }
                    "C" -> {
                        binding.cardOptionTwo.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.green))
                    }
                    "D" -> {
                        binding.cardOptionFour.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.green))
                    }
                }
            }

            mCountDownTimer.cancel()
            mTimeLeftInMillis = START_TIME_IN_MILLIS
            resetCountDownText()

            lifecycleScope.launch {
                delay(3000)
                Log.d("this","click item cancel timer & show question")
                flag = 1
                resetOptionColor()
                showNextQuestion()
            }
        }


        binding.optionTwoItem.setOnClickListener {

            if (currentQuestion!!.correctAnswer == "B"){
                number += currentQuestion!!.score
                binding.scoreNumberTv.text = "Score: $number"
                binding.cardOptionTwo.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.green))
            }else{
                binding.cardOptionTwo.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.red))
                when (currentQuestion!!.correctAnswer) {
                    "A" -> {
                        binding.cardOptionOne.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.green))
                    }
                    "C" -> {
                        binding.cardOptionTwo.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.green))
                    }
                    "D" -> {
                        binding.cardOptionFour.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.green))
                    }
                }
            }
            mCountDownTimer.cancel()
            mTimeLeftInMillis = START_TIME_IN_MILLIS
            resetCountDownText()
            lifecycleScope.launch {

                delay(3000)
                Log.d("this","click item cancel timer & show question")
                flag = 1
                resetOptionColor()
                showNextQuestion()

            }
        }

        binding.optionThreeItem.setOnClickListener {

            if (currentQuestion!!.correctAnswer == "C"){
                number += currentQuestion!!.score
                binding.scoreNumberTv.text = "Score: $number"
                binding.cardOptionThree.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.green))
            }else{
                binding.cardOptionThree.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.red))
                when (currentQuestion!!.correctAnswer) {
                    "A" -> {
                        binding.cardOptionOne.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.green))
                    }
                    "B" -> {
                        binding.cardOptionTwo.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.green))
                    }
                    "D" -> {
                        binding.cardOptionFour.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.green))
                    }
                }
            }
            mCountDownTimer.cancel()
            mTimeLeftInMillis = START_TIME_IN_MILLIS
            resetCountDownText()
            lifecycleScope.launch {

                delay(3000)
                Log.d("this","click item cancel timer & show question")
                flag = 1
                resetOptionColor()
                showNextQuestion()
            }
        }

        binding.optionFourItem.setOnClickListener {

            if (currentQuestion!!.correctAnswer == "D"){
                number += currentQuestion!!.score
                binding.scoreNumberTv.text = "Score: $number"
                binding.cardOptionFour.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.green))
            }else{
                binding.cardOptionFour.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.red))
                when (currentQuestion!!.correctAnswer) {
                    "A" -> {
                        binding.cardOptionOne.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.green))
                    }
                    "B" -> {
                        binding.cardOptionTwo.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.green))
                    }
                    "C" -> {
                        binding.cardOptionFour.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.green))
                    }
                }
            }

            mCountDownTimer.cancel()
            mTimeLeftInMillis = START_TIME_IN_MILLIS
            resetCountDownText()
            lifecycleScope.launch {

                delay(3000)
                Log.d("this","click item cancel timer & show question")
                flag = 1
                resetOptionColor()
                showNextQuestion()
            }
        }

    }*/

    @SuppressLint("SetTextI18n")
    private  fun  clickToQuestion(){
        when {
            binding.one.isChecked -> {

                if (currentQuestion!!.correctAnswer == "A"){
                    number += currentQuestion!!.score
                    binding.scoreNumberTv.text = "Score: $number"
                    binding.cardOptionOne.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.green))

                }else{
                    binding.cardOptionOne.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.red))
                    when (currentQuestion!!.correctAnswer) {
                        "B" -> {
                            binding.cardOptionOne.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.green))
                        }
                        "C" -> {
                            binding.cardOptionTwo.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.green))
                        }
                        "D" -> {
                            binding.cardOptionFour.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.green))
                        }
                    }
                }

                mCountDownTimer.cancel()
                mTimeLeftInMillis = START_TIME_IN_MILLIS
                resetCountDownText()

                lifecycleScope.launch {
                    delay(3000)
                    Log.d("this","click item cancel timer & show question")
                    flag = 1
                    resetOptionColor()
                    showNextQuestion()
                }
            }
            binding.two.isChecked -> {

                if (currentQuestion!!.correctAnswer == "B"){
                    number += currentQuestion!!.score
                    binding.scoreNumberTv.text = "Score: $number"
                    binding.cardOptionTwo.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.green))
                }else{
                    binding.cardOptionTwo.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.red))
                    when (currentQuestion!!.correctAnswer) {
                        "A" -> {
                            binding.cardOptionOne.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.green))
                        }
                        "C" -> {
                            binding.cardOptionTwo.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.green))
                        }
                        "D" -> {
                            binding.cardOptionFour.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.green))
                        }
                    }
                }
                mCountDownTimer.cancel()
                mTimeLeftInMillis = START_TIME_IN_MILLIS
                resetCountDownText()
                lifecycleScope.launch {

                    delay(3000)
                    Log.d("this","click item cancel timer & show question")
                    flag = 1
                    resetOptionColor()
                    showNextQuestion()

                }
            }
            binding.three.isChecked -> {

                if (currentQuestion!!.correctAnswer == "C"){
                    number += currentQuestion!!.score
                    binding.scoreNumberTv.text = "Score: $number"
                    binding.cardOptionThree.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.green))
                }else{
                    binding.cardOptionThree.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.red))
                    when (currentQuestion!!.correctAnswer) {
                        "A" -> {
                            binding.cardOptionOne.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.green))
                        }
                        "B" -> {
                            binding.cardOptionTwo.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.green))
                        }
                        "D" -> {
                            binding.cardOptionFour.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.green))
                        }
                    }
                }
                mCountDownTimer.cancel()
                mTimeLeftInMillis = START_TIME_IN_MILLIS
                resetCountDownText()
                lifecycleScope.launch {

                    delay(3000)
                    Log.d("this","click item cancel timer & show question")
                    flag = 1
                    resetOptionColor()
                    showNextQuestion()
                }
            }
            binding.four.isChecked -> {

                if (currentQuestion!!.correctAnswer == "D"){
                    number += currentQuestion!!.score
                    binding.scoreNumberTv.text = "Score: $number"
                    binding.cardOptionFour.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.green))
                }else{
                    binding.cardOptionFour.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.red))
                    when (currentQuestion!!.correctAnswer) {
                        "A" -> {
                            binding.cardOptionOne.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.green))
                        }
                        "B" -> {
                            binding.cardOptionTwo.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.green))
                        }
                        "C" -> {
                            binding.cardOptionFour.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.green))
                        }
                    }
                }

                mCountDownTimer.cancel()
                mTimeLeftInMillis = START_TIME_IN_MILLIS
                resetCountDownText()
                lifecycleScope.launch {

                    delay(3000)
                    Log.d("this","click item cancel timer & show question")
                    flag = 1
                    resetOptionColor()
                    showNextQuestion()
                }
            }
        }

    }

    private fun resetOptionColor(){
        binding.cardOptionOne.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.white))
        binding.cardOptionTwo.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.white))
        binding.cardOptionThree.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.white))
        binding.cardOptionFour.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.white))
    }

    private fun startTimer(){

        mCountDownTimer = object : CountDownTimer(mTimeLeftInMillis,1000) {
            override fun onTick(millisUntilFinished: Long) {
                mTimeLeftInMillis = millisUntilFinished
                updateCountDownText()
                if (mTimeLeftInMillis<11000){
                    binding.countDownTimeTx.setTextColor(ContextCompat.getColor(requireContext(),R.color.red))
                }
                Log.d("this","timer tick")
            }

            override fun onFinish() {
                Log.d("this","timer finish & show question")
                flag = 1
                mTimerRunning = true
                showNextQuestion()
            }

        }.start()

    }


    fun updateCountDownText(){
        val minutes = (mTimeLeftInMillis / 1000).toInt() / 60
        val seconds = (mTimeLeftInMillis / 1000).toInt() % 60

        val timeLeftFormatted: String =
            java.lang.String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
        binding.countDownTimeTx.text = timeLeftFormatted
    }

    private fun resetCountDownText(){
        binding.countDownTimeTx.text = resources.getString(R.string.reset)
    }

    private fun saveHighestScore(){
        val value = appPreference.getString("number")
        if (value == null){
            appPreference.setInt("number",0)
        }else{
            if (value< number.toString()){
                appPreference.setString("number",number.toString())
            }
        }

    }

   private fun hideOption(){

       binding.cardOptionOne.isVisible = false
       binding.cardOptionTwo.isVisible = false
       binding.cardOptionThree.isVisible = false
       binding.cardOptionFour.isVisible = false
    }


    private fun disableOption(){
        binding.cardOptionOne.isClickable = false
        binding.cardOptionTwo.isClickable = false
        binding.cardOptionThree.isClickable = false
        binding.cardOptionFour.isClickable = false
    }


    private fun enabledOption(){
        binding.cardOptionOne.isClickable = true
        binding.cardOptionTwo.isClickable = true
        binding.cardOptionThree.isClickable = true
        binding.cardOptionFour.isClickable = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}