package com.example.themealapp.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.themealapp.database.MyDatabase
import com.example.themealapp.model.favourite.Favourite
import com.example.themealapp.model.fooddetail.FoodDetail
import com.example.themealapp.service.FoodAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FoodDetailViewModel(application: Application): BaseViewModel(application) {
    private val disposable = CompositeDisposable()  //uygulama kapandığında hafızadaki verilerin silinmesini sağlar.
    private val foodAPIService = FoodAPIService()

    val foodDetail = MutableLiveData<FoodDetail>()

    fun getFoodDetailFromApi(foodId: String){
        disposable.add(
            foodAPIService.getFoodDetail(foodId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object :DisposableSingleObserver<FoodDetail>(){
                    override fun onSuccess(t: FoodDetail) {
                        foodDetail.value = t
                    }

                    override fun onError(e: Throwable) {

                    }

                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}