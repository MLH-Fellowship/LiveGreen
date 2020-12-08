package com.project.livegreen.result


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.project.livegreen.repository.CarbonRepository
import kotlinx.coroutines.Dispatchers

class ResultViewModel(val lon : Double, val lat : Double) : ViewModel() {

    val repository : CarbonRepository = CarbonRepository()
    val carbonData = liveData(Dispatchers.IO) {
        try {
            //val data = repository.getData(6.8770394, 45.9162776)
            val data = repository.getData(lon, lat)
            emit(data)
        } catch (e : Exception) {
            Log.d("excepting", e.message!!)
        }
    }
}