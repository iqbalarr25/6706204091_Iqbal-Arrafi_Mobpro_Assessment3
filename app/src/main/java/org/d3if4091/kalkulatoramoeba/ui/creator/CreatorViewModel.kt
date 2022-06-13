package org.d3if4091.kalkulatoramoeba.ui.creator


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if4091.kalkulatoramoeba.ApiStatus
import org.d3if4091.kalkulatoramoeba.CreatorApi
import org.d3if4091.kalkulatoramoeba.model.Creator

class CreatorViewModel : ViewModel() {

    private val creator = MutableLiveData<Creator>()
    private val status = MutableLiveData<ApiStatus>()

    init {
        retrieveData()
    }

    private fun retrieveData() {
        viewModelScope.launch (Dispatchers.IO) {
            status.postValue(ApiStatus.LOADING)
            try {
                creator.postValue(CreatorApi.service.getCreator())
                Log.d("CreatorViewModel", creator.value.toString())
                status.postValue(ApiStatus.SUCCESS)
            } catch (e: Exception) {
                status.postValue(ApiStatus.FAILED)
                Log.d("CreatorViewModel", "Failure: ${e.message}")
            }
        }
    }

    fun getData(): LiveData<Creator> = creator

    fun getStatus(): LiveData<ApiStatus> = status
}