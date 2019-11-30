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

class MyPageLoginDataImpl  : MyPageLoginData {
    override fun getLoginData(email: String, pass: String, callback: MyPageLoginDataCallback) {



//        val call = userApi.login(email, pass).request().url()
//        Log.d("''''''''''''''''''''''''''''","$call")

        val retrofit = Retrofit.Builder()
            .baseUrl(MyPageLoginFragment.URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        Log.d("''''''''''''''''''''''''''''","$retrofit")

        val tt = retrofit.create(UserApi::class.java)

        Log.d("''''''''''''''''''''''''''''","$tt")


        tt.login(email, pass).enqueue(object :
            Callback<ResultModel> {
            override fun onFailure(call: Call<ResultModel>?, t: Throwable?) {
                callback.onFailure(t.toString())
            }

            override fun onResponse(call: Call<ResultModel>?, response: Response<ResultModel>?) {

                val result = response?.body()?.result

                val resultNickname = response?.body()?.resultNickname

                if (result != null && resultNickname != null) {
                    if (result.equals("ok")) {
                        callback.onSuccess(resultNickname)
                    } else {
                        callback.onFailure(result)
                    }
                } else {
                    callback.onFailure("로그인 실패")
                }

            }
        })


    }


    companion object {

        private var instance: MyPageLoginDataImpl? = null

        fun getInstance(): MyPageLoginDataImpl =
            instance ?: instance ?: MyPageLoginDataImpl().also {
                instance = it
            }
    }
}