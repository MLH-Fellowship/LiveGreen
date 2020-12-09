package com.project.livegreen.verdict

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.project.livegreen.R
import com.project.livegreen.databinding.FragmentVerdictBinding


class VerdictFragment : Fragment() {

    private var _binding: FragmentVerdictBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val fossilPercentage = arguments?.get("Fossil") as Float
        Log.d("FFP: ", fossilPercentage.toString())

        _binding = FragmentVerdictBinding.inflate(inflater, container, false)


        if (fossilPercentage >= 50) {
            binding.verdictTextview.text = "greater"
        } else {
            binding.verdictTextview.text = "less"
        }

        binding.whateverButton.setOnClickListener {
            findNavController().navigate(R.id.action_verdictFragment_to_webFragment)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}