package com.work.restaurant.data.source.remote.kakao

import android.util.Log
import com.work.restaurant.network.api.KakaoApi
import com.work.restaurant.network.model.KakaoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KakaoRemoteDataSourceImpl private constructor(private val kakaoApi: KakaoApi) :
    KakaoRemoteDataSource {


    override fun getData(callback: KakaoRemoteDataSourceCallback) {

        kakaoApi.keywordSearch("카카오프렌즈").enqueue(object :
            Callback<KakaoResponse> {
            override fun onFailure(call: Call<KakaoResponse>?, t: Throwable?) {
                callback.onFailure("${t?.message}")
                Log.d("카카오결과133", "${t?.message}")
            }

            override fun onResponse(
                call: Call<KakaoResponse>?,
                response: Response<KakaoResponse>?
            ) {

                Log.d("카카오결과1", call.toString())

                Log.d("카카오결과1", response?.body().toString())

                val t = response?.body()?.documents

                Log.d("카카오결과1", t.toString())


            }


        })


    }


    companion object {

        private var instance: KakaoRemoteDataSourceImpl? = null

        fun getInstance(kakaoApi: KakaoApi): KakaoRemoteDataSourceImpl =
            instance ?: KakaoRemoteDataSourceImpl(kakaoApi).also {
                instance = it
            }


    }
}
