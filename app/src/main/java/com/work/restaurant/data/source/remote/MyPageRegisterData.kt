package com.work.restaurant.data.source.remote

interface MyPageRegisterData {

    fun getRegisterData(nickName : String , email : String, pass : String , callback : MyPageRegisterDataCallback)
}