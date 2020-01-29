package com.work.restaurant.data.source.remote.kakao

import com.work.restaurant.network.api.KakaoApi
import com.work.restaurant.network.model.kakao.KakaoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KakaoRemoteDataSourceImpl private constructor(private val kakaoApi: KakaoApi) :
    KakaoRemoteDataSource {
    override fun getData(
        currentX: Double,
        currentY: Double,
        callback: KakaoRemoteDataSourceCallback
    ) {
        kakaoApi.keywordSearch("헬스장", currentX, currentY).enqueue(object :
            Callback<KakaoResponse> {
            override fun onFailure(call: Call<KakaoResponse>?, t: Throwable?) {
                callback.onFailure("${t?.message}")
//                Log.d("카카오결과133", "${t?.message}")
            }

            override fun onResponse(
                call: Call<KakaoResponse>?,
                response: Response<KakaoResponse>?
            ) {

//                Log.d("카카오결과1", response?.code().toString())
//                Log.d("카카오결과1", response?.message())
                if (response != null) {
                    if (response.isSuccessful) {
                        callback.onSuccess(response.body().documents)
                    } else {
                        callback.onFailure(response.message())
                    }
                }
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
