package com.work.restaurant.db.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.work.restaurant.db.room.entity.AddressEntity

@Dao
interface AddressDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun registerAddress(addressEntity: AddressEntity)

    @Query("SELECT COUNT(*) FROM address")
    fun getAllCount(): Int

    @Query("SELECT * FROM address")
    fun getAll(): List<AddressEntity>

}