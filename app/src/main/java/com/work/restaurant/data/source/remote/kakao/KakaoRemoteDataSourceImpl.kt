package com.work.restaurant.data.source.remote.kakao

import com.work.restaurant.network.api.KakaoApi
import com.work.restaurant.network.model.kakaoImage.KakaoImageDocuments
import com.work.restaurant.network.model.kakaoImage.KakaoImageResponse
import com.work.restaurant.network.model.kakaoSearch.KakaoSearchDocuments
import com.work.restaurant.network.model.kakaoSearch.KakaoSearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KakaoRemoteDataSourceImpl private constructor(private val kakaoApi: KakaoApi) :
    KakaoRemoteDataSource {


    override fun getKakaoImage(
        placeName: String,
        callback: KakaoRemoteDataSourceCallback.KakaoImageCallback
    ) {

        kakaoApi.kakaoItemImage(placeName).enqueue(object : Callback<KakaoImageResponse> {
            override fun onFailure(call: Call<KakaoImageResponse>?, t: Throwable?) {
                callback.onFailure("${t?.message}")
            }

            override fun onResponse(
                call: Call<KakaoImageResponse>?,
                response: Response<KakaoImageResponse>?
            ) {
                if (response != null) {
                    if (response.isSuccessful) {


                        val list = response.body().kakaoImageDocuments

                        val toKakaoDocuments = mutableSetOf<KakaoImageDocuments>()

                        list.forEach {

                            toKakaoDocuments.add(it)

                        }
                        callback.onSuccess(toKakaoDocuments.toList())
                    } else {
                        callback.onFailure(response.message())
                    }
                }
            }
        })

    }

    override fun getKakaoItemInfo(
        placeName: String,
        callback: KakaoRemoteDataSourceCallback.KakaoItemInfoCallback
    ) {
        kakaoApi.kakaoItemSearch(placeName).enqueue(object : Callback<KakaoSearchResponse> {
            override fun onFailure(call: Call<KakaoSearchResponse>?, t: Throwable?) {
                callback.onFailure("${t?.message}")
            }

            override fun onResponse(
                call: Call<KakaoSearchResponse>?,
                response: Response<KakaoSearchResponse>?
            ) {
                if (response != null) {
                    if (response.isSuccessful) {

                        val toSortDocuments = mutableListOf<KakaoSearchDocuments>()

                        response.body().documents.forEach {
                            if (it.categoryName.contains("스포츠,레저")) {
                                toSortDocuments.add(it)
                            }
                        }


                        callback.onSuccess(toSortDocuments)
                    } else {
                        callback.onFailure(response.message())
                    }
                }
            }
        })
    }

    override fun getData(
        currentX: Double,
        currentY: Double,
        callback: KakaoRemoteDataSourceCallback
    ) {
        kakaoApi.keywordSearch("헬스장", currentX, currentY).enqueue(object :
            Callback<KakaoSearchResponse> {
            override fun onFailure(call: Call<KakaoSearchResponse>?, t: Throwable?) {
                callback.onFailure("${t?.message}")
//                Log.d("카카오결과133", "${t?.message}")
            }

            override fun onResponse(
                call: Call<KakaoSearchResponse>?,
                response: Response<KakaoSearchResponse>?
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
