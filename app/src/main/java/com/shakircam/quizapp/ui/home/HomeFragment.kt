package com.shakircam.quizapp.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.shakircam.quizapp.R
import com.shakircam.quizapp.databinding.FragmentHomeBinding
import com.shakircam.quizapp.utils.AppPreference
import com.shakircam.quizapp.utils.AppPreferenceImpl


class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var appPreference: AppPreference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        appPreference = AppPreferenceImpl(requireActivity())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showHighScore()
        binding.gk.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToQuestionsFragment()
            view.findNavController().navigate(action)
        }




    }


    @SuppressLint("SetTextI18n")
    private fun showHighScore(){
        val value = appPreference.getString("number")
        if (value != null) {
            if (value> ((-1).toString())){
                binding.score.text = value.toString()+" "+resources.getString(R.string.point)
            }
        }

    }

}