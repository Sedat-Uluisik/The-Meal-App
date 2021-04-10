package com.example.themealapp.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.themealapp.database.MyDatabase
import com.example.themealapp.model.favourite.Favourite
import com.example.themealapp.model.food.Food
import com.example.themealapp.model.food.Meal
import com.example.themealapp.service.FoodAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FoodListViewModel(application: Application): BaseViewModel(application) {
    private val disposable = CompositeDisposable()  //uygulama kapandığında hafızadaki verilerin silinmesini sağlar.
    private val foodAPIService = FoodAPIService()

    val mealList = MutableLiveData<List<Meal>>()
    val favorites = MutableLiveData<List<Favourite>>()

    fun getFoodFromRegionId(region: String){
        disposable.add(
            foodAPIService.getMealDataForRegion(region)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Food>(){
                    override fun onSuccess(t: Food) {
                        showList(t.meals)
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }
                })
        )
    }

    fun getFoodFromCategoryId(category: String){
        disposable.add(
            foodAPIService.getMealDataForCategory(category)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object :DisposableSingleObserver<Food>(){
                    override fun onSuccess(t: Food) {
                        showList(t.meals)
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }

                })
        )
    }

    private fun showList(list: List<Meal>){
        mealList.value = list
    }

    //Room db ile ilgili işlemler.
    fun saveFavorite(favourite: Favourite){
        launch {
            val dao = MyDatabase(getApplication()).favouriteDao()
            dao.insertFavourite(favourite)
            getAllFavorite()
        }
    }

    fun deleteFavorite(favourite: Favourite){
        launch {
            val dao = MyDatabase(getApplication()).favouriteDao()
            dao.deleteFavourite(favourite)
            getAllFavorite()
        }
    }

    fun getAllFavorite(){
        launch {
            val dao = MyDatabase(getApplication()).favouriteDao()
            favorites.value = dao.getAllFavorite()
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}