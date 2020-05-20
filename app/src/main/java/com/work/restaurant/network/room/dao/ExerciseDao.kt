package com.work.restaurant.network.room.dao

import androidx.room.*
import com.work.restaurant.network.room.entity.ExerciseEntity
import com.work.restaurant.network.room.entity.ExerciseSetResponse

@Dao
interface ExerciseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun registerExercise(exerciseEntity: ExerciseEntity): Long

    @Query("SELECT COUNT(*) FROM exercise")
    fun getAllCount(): Int

    @Query("SELECT * FROM exercise WHERE userId = (:userId)")
    fun getAll(userId: String): List<ExerciseEntity>


    @Query("SELECT * FROM exercise WHERE userId = (:userId) AND date = (:today)")
    fun getTodayItem(userId: String, today: String): List<ExerciseEntity>

    @Delete
    fun deleteExercise(exerciseEntity: ExerciseEntity): Int


    @Query("UPDATE exercise SET time = (:changeTime) , type = (:changeType) , exerciseName = (:changeExerciseName), exerciseSetList = (:exerciseSetList) WHERE  userId= (:currentUser) AND exerciseNum = (:currentNum)")
    fun updateEat(
        changeTime: String,
        changeType: String,
        changeExerciseName: String,
        exerciseSetList: List<ExerciseSetResponse>,
        currentUser: String,
        currentNum: Int
    ): Int


}