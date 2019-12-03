package com.work.restaurant.data.repository.mypage

interface MyPageRegisterRepository {
    fun getRegisterData(nickName : String , email : String, pass : String , callback : MyPageRegisterRepositoryCallback)
}