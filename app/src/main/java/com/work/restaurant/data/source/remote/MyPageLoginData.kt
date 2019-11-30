package com.work.restaurant.data.source.remote

interface MyPageLoginData {

    fun getLoginData(email: String, pass: String, callback: MyPageLoginDataCallback)


}