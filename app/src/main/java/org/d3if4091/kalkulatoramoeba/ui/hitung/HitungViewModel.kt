package org.d3if4091.kalkulatoramoeba.ui.hitung

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if4091.kalkulatoramoeba.MainActivity
import org.d3if4091.kalkulatoramoeba.db.AmoebaDao
import org.d3if4091.kalkulatoramoeba.db.AmoebaEntity
import org.d3if4091.kalkulatoramoeba.model.HasilAmoeba
import org.d3if4091.kalkulatoramoeba.model.hitungAmoeba
import org.d3if4091.kalkulatoramoeba.network.NotifierWorker
import java.util.concurrent.TimeUnit

class HitungViewModel(private val db: AmoebaDao): ViewModel() {

    private val hasilAmoeba = MutableLiveData<HasilAmoeba?>()

    fun hitungAmoeba(awalAmoeba : Float, pembelahanAmoeba: Float, rentangWaktu: Float, jangkaWaktu: Float, saveDatabase: Boolean){

        val dataAmoeba = AmoebaEntity(
            awalAmoeba = awalAmoeba,
            pembelahanAmoeba = pembelahanAmoeba,
            rentangWaktu = rentangWaktu,
            jangkaWaktu = jangkaWaktu,
        )

        hasilAmoeba.value = dataAmoeba.hitungAmoeba()

        if(saveDatabase){
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    db.insert(dataAmoeba)
                }
            }
        }
    }

    fun scheduleNotifier(app: Application) {
        val request = PeriodicWorkRequestBuilder<NotifierWorker>(1, TimeUnit.DAYS).setInitialDelay(1, TimeUnit.DAYS).build()
        WorkManager.getInstance(app).enqueueUniquePeriodicWork(
            MainActivity.CHANNEL_ID,
            ExistingPeriodicWorkPolicy.REPLACE,
            request
        )
    }

    fun getHasilAmoeba(): LiveData<HasilAmoeba?> = hasilAmoeba

    fun deleteHasilAmoeba(){
        hasilAmoeba.value = null
    }
}