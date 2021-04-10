package com.example.themealapp.service

import com.example.themealapp.model.category.Category
import com.example.themealapp.model.food.Food
import com.example.themealapp.model.fooddetail.FoodDetail
import com.example.themealapp.model.region.Region
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class FoodAPIService {
    private val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"
    private val api = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(FoodAPI::class.java)

    fun getRegionData(): Single<Region> {
        return api.getRegion()
    }

    fun getCategoriesData(): Single<Category> {
        return api.getCategories()
    }

    fun getMealDataForRegion(region: String): Single<Food> {
        return api.getMealFromRegion(region)
    }

    fun getMealDataForCategory(categoryName: String): Single<Food>{
        return api.getMealFromCategory(categoryName)
    }

    fun getFoodDetail(foodId: String): Single<FoodDetail>{
        return api.getFoodDetail(foodId)
    }


    //https://www.themealdb.com/api/json/v1/1/categories.php
    //https://www.themealdb.com/api/json/v1/1/list.php?a=list
    //https://www.themealdb.com/api/json/v1/1/filter.php?a=Canadian
    //https://www.themealdb.com/api/json/v1/1/filter.php?c=Dessert
    //https://www.themealdb.com/api/json/v1/1/lookup.php?i=52767
}