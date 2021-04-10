package com.example.themealapp.service

import com.example.themealapp.model.category.Category
import com.example.themealapp.model.food.Food
import com.example.themealapp.model.fooddetail.FoodDetail
import com.example.themealapp.model.region.Region
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodAPI {
    @GET("list.php?a=list")
    fun getRegion(): Single<Region>

    @GET("categories.php")
    fun getCategories(): Single<Category>

    @GET("filter.php")
    fun getMealFromRegion(
        @Query("a") region: String
    ): Single<Food>

    @GET("filter.php")
    fun getMealFromCategory(
        @Query("c") categoryName: String
    ): Single<Food>

    @GET("lookup.php")
    fun getFoodDetail(
        @Query("i") foodId: String
    ): Single<FoodDetail>
}