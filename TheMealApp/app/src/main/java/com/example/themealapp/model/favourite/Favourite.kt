package com.example.themealapp.model.favourite

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "T_FAVOURITE")
data class Favourite(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "MEAL_ID")
    val mealId: String
)
/*
    Favorilenen yemeğin id'si room database'ye kaydedilir.
    Daha sonra api ile veriler alınırken database'deki id'ler ile karşılaştırılır.
    Id'ler eşitse favori ikonu aktif olur.
*/