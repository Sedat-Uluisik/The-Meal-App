package com.example.themealapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.themealapp.model.favourite.Favourite

@Database(entities = arrayOf(Favourite::class), version = 1)
abstract class MyDatabase: RoomDatabase() {
    abstract fun favouriteDao(): FavouriteDao

    companion object{
        // Volatile -> farklı thread'larda kullanılabilmesi, çağrılabilmesi için.
        @Volatile private var instance: MyDatabase ?= null

        //senkron işleminin kilitlenip kilitlenmemesini kontrol eder.
        private val lock = Any()

        //synchronized -> iki ya da daha fazla thread'ın burayı kullanmasını engeller.
        operator fun invoke(context: Context) = instance ?: synchronized(lock){
            //database yoksa oluştur.
            instance ?: createDatabase(context).also {
                //instance değişkenine database oluştu bilgisini ver.
                instance = it
            }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
                context.applicationContext,
                MyDatabase::class.java,
                "TheMealDB"
        ).build()
    }
}