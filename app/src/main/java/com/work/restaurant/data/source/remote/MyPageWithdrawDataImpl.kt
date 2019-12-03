package com.work.restaurant.data.source.remote

import com.work.restaurant.network.api.UserApi
import com.work.restaurant.network.model.ResultModel
import com.work.restaurant.view.mypage.fragment.MyPageLoginFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyPageWithdrawDataImpl : MyPageWithdrawData {
    override fun getWithdrawData(userNickname: String, callback: MyPageWithdrawDataCallback) {

        val retrofit = Retrofit.Builder()
            .baseUrl(MyPageLoginFragment.URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val userApi = retrofit.create(UserApi::class.java)


        userApi.delete(userNickname).enqueue(object :
            Callback<ResultModel> {
            override fun onFailure(call: Call<ResultModel>?, t: Throwable?) {
                callback.onFailure(t.toString())
            }

            override fun onResponse(call: Call<ResultModel>?, response: Response<ResultModel>?) {

                val result = response?.body()?.result

                if (result != null) {
                    if (result.equals("ok")) {
                        callback.onSuccess()
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

        private var instance: MyPageWithdrawDataImpl? = null

        fun getInstance(): MyPageWithdrawDataImpl =
            instance ?: instance ?: MyPageWithdrawDataImpl().also {
                instance = it
            }
    }

}