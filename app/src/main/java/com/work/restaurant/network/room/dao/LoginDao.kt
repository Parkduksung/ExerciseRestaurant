package com.work.restaurant.network.room.dao

import androidx.room.*
import com.work.restaurant.network.room.entity.LoginEntity


@Dao
interface LoginDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun registerLogin(loginEntity: LoginEntity): Long

    @Query("SELECT * FROM login WHERE login_state = (:state)")
    fun getLoginState(state: Boolean): List<LoginEntity>

    @Query("SELECT * FROM login")
    fun getAllList(): List<LoginEntity>

    @Query("UPDATE login SET login_state = (:state) WHERE login_id = (:id)")
    fun stateChange(id: String, state: Boolean): Int

    @Query("DELETE FROM login WHERE login_id = (:id) AND login_nickname = (:nickname)")
    fun deleteLogin(id: String, nickname: String): Int

    @Delete
    fun delete(loginEntityList: List<LoginEntity>): Int

    @Query("SELECT * FROM login WHERE login_id = (:id) AND login_pw = (:pw) AND login_nickname = (:nickname)")
    fun findUser(id: String, pw: String, nickname: String): Int

}