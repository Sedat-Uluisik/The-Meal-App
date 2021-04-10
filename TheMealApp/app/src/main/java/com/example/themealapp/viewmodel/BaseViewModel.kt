package com.example.themealapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel(application: Application): AndroidViewModel(application), CoroutineScope {
    private var job = Job() //arkaplanda yapılacak işi oluşturur.

    override val coroutineContext: CoroutineContext
        get() =  job + Dispatchers.Main  //önce işini yap sonra main thread'a dön

    override fun onCleared() {
        super.onCleared()
        job.cancel()  //app kapanırsa ya da sorun çıkarsa işi iptal et.
    }
}