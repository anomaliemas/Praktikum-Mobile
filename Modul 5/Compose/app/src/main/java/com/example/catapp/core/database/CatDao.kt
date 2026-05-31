package com.example.catapp.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CatDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCat(cat: CatEntity)

    @Query("SELECT * FROM cats ORDER BY id DESC")
    fun getAllCats(): Flow<List<CatEntity>>
}