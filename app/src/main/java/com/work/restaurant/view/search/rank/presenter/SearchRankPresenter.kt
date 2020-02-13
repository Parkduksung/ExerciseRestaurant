package com.work.restaurant.view.search.rank.presenter

import android.util.Log
import com.work.restaurant.data.model.BookmarkModel
import com.work.restaurant.data.repository.bookmark.BookmarkRepository
import com.work.restaurant.data.repository.bookmark.BookmarkRepositoryCallback
import com.work.restaurant.data.repository.kakao.KakaoRepository
import com.work.restaurant.data.repository.kakao.KakaoRepositoryCallback
import com.work.restaurant.network.model.kakaoAddress.KakaoAddressDocument
import com.work.restaurant.network.model.kakaoLocationToAddress.KakaoLocationToAddressDocument
import com.work.restaurant.network.model.kakaoSearch.KakaoSearchDocuments

class SearchRankPresenter(
    private val searchRankView: SearchRankContract.View,
    private val kakaoRepository: KakaoRepository,
    private val bookmarkRepository: BookmarkRepository
) :
    SearchRankContract.Presenter {
    override fun getCurrentAddress(currentX: Double, currentY: Double) {
        kakaoRepository.getKakaoLocationToAddress(
            currentX,
            currentY,
            object : KakaoRepositoryCallback.KakaoLocationToAddress {
                override fun onSuccess(item: List<KakaoLocationToAddressDocument>) {
                    if (item.isNotEmpty()) {
                        val toKakaoLocationToAddressModel =
                            item.map { it.toKakaoLocationToAddressModel() }

                        searchRankView.showCurrentLocation(toKakaoLocationToAddressModel[0].addressName)
                    }
                }

                override fun onFailure(message: String) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            })


    }

    override fun getCurrentLocation(addressName: String) {

        kakaoRepository.getKakaoAddressLocation(addressName,
            object : KakaoRepositoryCallback.KakaoAddressCallback {

                override fun onSuccess(item: List<KakaoAddressDocument>) {

                    if (item.isNotEmpty()) {
                        val currentX = (item[0].x).toDouble()
                        val currentY = (item[0].y).toDouble()
                        getKakaoList(currentX, currentY)
                    }
                }

                override fun onFailure(message: String) {
                    Log.d("error", message)
                }
            })
    }

    override fun addBookmarkKakaoItem(bookmarkModel: BookmarkModel) {

        val toBookmarkEntity = bookmarkModel.toBookmarkEntity()

        bookmarkRepository.addBookmark(
            toBookmarkEntity,
            object : BookmarkRepositoryCallback.AddBookmarkCallback {
                override fun onSuccess() {
                    searchRankView.showBookmarkResult(true)
                }

                override fun onFailure() {
                    searchRankView.showBookmarkResult(false)
                }
            })
    }


    override fun getKakaoList(currentX: Double, currentY: Double) {

        kakaoRepository.getKakaoResult(
            currentX,
            currentY,
            object : KakaoRepositoryCallback {
                override fun onSuccess(kakaoList: List<KakaoSearchDocuments>) {
                    val toKakaoSearchModelList = kakaoList.map { it.toKakaoModel() }
                    searchRankView.showKakaoList(toKakaoSearchModelList)
                }

                override fun onFailure(message: String) {
                    Log.d("error", message)
                }
            })

    }

}