package com.work.restaurant.network.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.work.restaurant.network.room.entity.EatEntity

@Dao
interface EatDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun registerEat(eatEntity: EatEntity)

    @Query("SELECT COUNT(*) FROM eat")
    fun getAllCount(): Int

    @Query("SELECT * FROM eat")
    fun getAll(): List<EatEntity>
}