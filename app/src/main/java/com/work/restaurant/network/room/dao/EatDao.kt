package com.work.restaurant.network.room.dao

import androidx.room.*
import com.work.restaurant.network.room.entity.EatEntity

@Dao
interface EatDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun registerEat(eatEntity: EatEntity): Long

    @Query("SELECT COUNT(*) FROM eat")
    fun getAllCount(): Int

    @Query("SELECT * FROM eat WHERE userId = (:userId)")
    fun getAll(userId: String): List<EatEntity>

    @Query("SELECT * FROM eat WHERE userId = (:userId) AND date = (:today)")
    fun getTodayItem(userId: String, today: String): List<EatEntity>

    @Delete
    fun deleteEat(eatEntity: EatEntity): Int

    @Query("UPDATE eat SET time = (:changeTime) , type = (:changeType) , memo = (:changeMemo) WHERE  userId= (:currentUser) AND eatNum = (:currentNum)")
    fun updateEat(
        changeTime: String,
        changeType: Int,
        changeMemo: String,
        currentUser: String,
        currentNum: Int
    ): Int


}