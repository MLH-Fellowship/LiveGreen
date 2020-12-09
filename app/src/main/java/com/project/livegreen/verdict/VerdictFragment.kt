package com.project.livegreen.verdict

import android.annotation.SuppressLint
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

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val fossilPercentage = arguments?.get("Fossil") as Float
        Log.d("FFP: ", fossilPercentage.toString())

        _binding = FragmentVerdictBinding.inflate(inflater, container, false)


        if (fossilPercentage >= 50) {
            binding.verdictTextview.text =
                "Your carbon intensity (C.I) is too high, you are advised to not use your electricity at this point. Be aware of what effects a high C.I has on your environment so you can take necessary steps to change the world. Click the button below to learn more."
        } else {
            binding.verdictTextview.text =
                "Based on these results, it looks like your carbon intensity (C.I) is low, you can go ahead and use your electricity. Be aware of what effects a high C.I has on your environment so you can take necessary steps to change the world. Click the button below to learn more."
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