package com.example.themealapp.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.themealapp.model.category.Category
import com.example.themealapp.model.region.Region
import com.example.themealapp.service.FoodAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class HomeViewModel(application: Application): BaseViewModel(application) {
    private val disposable = CompositeDisposable()  //uygulama kapandığında hafızadaki verilerin silinmesini sağlar.
    private val foodAPIService = FoodAPIService()

    val loading = MutableLiveData<Boolean>()
    val region = MutableLiveData<Region>()
    val categories = MutableLiveData<Category>()

    fun getRegionDataFromApi(){
        loading.value = true

        disposable.add(
            foodAPIService.getRegionData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Region>(){
                    override fun onSuccess(t: Region) {
                        region.value = t
                    }

                    override fun onError(e: Throwable) {
                        loading.value = true
                        e.printStackTrace()

                    }

                })
        )
    }
    fun getCategoriesFromApi(){
        loading.value = true

        disposable.add(
            foodAPIService.getCategoriesData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Category>(){
                    override fun onSuccess(t: Category) {
                        categories.value = t
                        loading.value = false
                    }

                    override fun onError(e: Throwable) {
                        loading.value = true
                    }

                })
        )
    }

    //App kapandığında hafızadaki verileri sil.
    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}