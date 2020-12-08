package com.project.livegreen.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ResultViewModeFactory(val lon : Double, val lat : Double) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResultViewModel::class.java)) {
            return ResultViewModel(lon, lat) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}