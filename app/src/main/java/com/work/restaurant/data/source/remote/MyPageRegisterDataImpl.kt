package com.work.restaurant.data.source.remote

import android.util.Log
import com.work.restaurant.network.api.UserApi
import com.work.restaurant.network.model.ResultModel
import com.work.restaurant.view.mypage.fragment.MyPageLoginFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyPageRegisterDataImpl : MyPageRegisterData {
    override fun getRegisterData(
        nickName: String,
        email: String,
        pass: String,
        callback: MyPageRegisterDataCallback
    ) {

        val retrofit = Retrofit.Builder()
            .baseUrl(MyPageLoginFragment.URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val userApi = retrofit.create(UserApi::class.java)

        userApi.register(
            nickName,
            email,
            pass
        ).enqueue(object : Callback<ResultModel> {
            override fun onFailure(call: Call<ResultModel>?, t: Throwable?) {
                callback.onFailure(t.toString())
            }

            override fun onResponse(
                call: Call<ResultModel>?,
                response: Response<ResultModel>?
            ) {
                val result = response?.body()?.result

                if (result.equals("ok")) {
                    Log.d("1111111", "성공")
                    callback.onSuccess()
                }
            }
        })
    }

    companion object {

        private var instance: MyPageRegisterDataImpl? = null

        fun getInstance(): MyPageRegisterDataImpl =
            instance ?: instance ?: MyPageRegisterDataImpl().also {
                instance = it
            }
    }


}