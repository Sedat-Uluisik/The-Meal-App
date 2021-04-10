package com.example.themealapp.database

import androidx.room.*
import com.example.themealapp.model.favourite.Favourite

@Dao
interface FavouriteDao {
    @Insert
    suspend fun insertFavourite(vararg favourite: Favourite)

    @Delete
    suspend fun deleteFavourite(vararg favourite: Favourite)

    @Query("SELECT * FROM T_FAVOURITE")
    suspend fun getAllFavorite(): List<Favourite>
}