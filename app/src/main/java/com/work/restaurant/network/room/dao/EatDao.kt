package com.work.restaurant.network.room.dao

import androidx.room.*
import com.work.restaurant.network.room.entity.EatEntity

@Dao
interface EatDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun registerEat(eatEntity: EatEntity) : Long

    @Query("SELECT COUNT(*) FROM eat")
    fun getAllCount(): Int

    @Query("SELECT * FROM eat")
    fun getAll(): List<EatEntity>

    @Query("SELECT * FROM eat WHERE date = (:today)")
    fun getTodayItem(today: String): List<EatEntity>

    @Delete
    fun deleteEat(eatEntity: EatEntity) : Int

}