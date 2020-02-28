package com.work.restaurant.network.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.work.restaurant.data.model.LoginModel

@Entity(tableName = "login")
data class LoginEntity(
    @PrimaryKey(autoGenerate = true)
    val loginNum: Int = 0,
    @ColumnInfo(name = "login_id")
    val loginId: String,
    @ColumnInfo(name = "login_pw")
    val loginPw: String,
    @ColumnInfo(name = "login_nickname")
    val loginNickname: String,
    @ColumnInfo(name = "login_state")
    val loginState: Boolean
) {

    fun toLoginModel(): LoginModel =
        LoginModel(loginId, loginPw, loginNickname, loginState)

}