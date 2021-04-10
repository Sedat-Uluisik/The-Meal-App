package com.example.themealapp.clicklistener

import android.view.View
import android.widget.RatingBar
import com.example.themealapp.model.food.Meal

interface ClickListener {
    fun onFavoriteClick(view: View, mealId: String){

    }

    fun onItemClick(view: View, mealId: String){

    }

    fun onRegionClick(view: View, area: String){

    }
}