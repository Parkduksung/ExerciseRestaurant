package com.work.restaurant.view.search.rank.presenter

import android.util.Log
import com.work.restaurant.data.model.BookmarkModel
import com.work.restaurant.data.repository.bookmark.BookmarkRepository
import com.work.restaurant.data.repository.bookmark.BookmarkRepositoryCallback
import com.work.restaurant.data.repository.kakao.KakaoRepository
import com.work.restaurant.data.repository.kakao.KakaoRepositoryCallback
import com.work.restaurant.network.model.kakaoAddress.KakaoAddressDocument
import com.work.restaurant.network.model.kakaoLocationToAddress.KakaoLocationToAddressDocument
import com.work.restaurant.network.model.kakaoSearch.KakaoSearchResponse

class SearchRankPresenter(
    private val searchRankView: SearchRankContract.View,
    private val kakaoRepository: KakaoRepository,
    private val bookmarkRepository: BookmarkRepository
) :
    SearchRankContract.Presenter {

    private var page = 0
    private var toggleLastPageCheck = false

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
                    searchRankView.showKakaoResult(0)
                }
            })
    }


    override fun getCurrentLocation(addressName: String, itemCount: Int, sort: String) {

        kakaoRepository.getKakaoAddressLocation(addressName,
            object : KakaoRepositoryCallback.KakaoAddressCallback {
                override fun onSuccess(item: List<KakaoAddressDocument>) {
                    if (item.isNotEmpty()) {
                        val currentX = (item[0].x).toDouble()
                        val currentY = (item[0].y).toDouble()
                        getKakaoRankList(currentX, currentY, sort, itemCount)
                    }
                }

                override fun onFailure(message: String) {
                    searchRankView.showKakaoResult(1)
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

    private fun getKakaoRankList(currentX: Double, currentY: Double, sort: String, itemCount: Int) {
        if (!toggleLastPageCheck) {
            if (itemCount >= (page * 15)) {
                searchRankView.showLoad()
                ++page
                kakaoRepository.getKakaoResult(
                    currentX,
                    currentY,
                    page,
                    sort,
                    object : KakaoRepositoryCallback {
                        override fun onSuccess(
                            kakaoList: KakaoSearchResponse
                        ) {
                            if (!kakaoList.kakaoSearchMeta.isEnd) {
                                val toKakaoSearchModelList =
                                    kakaoList.documents.map { it.toKakaoModel() }
                                searchRankView.showKakaoList(toKakaoSearchModelList)
                                searchRankView.showKakaoResult(-1)
                            } else {
                                val toKakaoSearchModelList =
                                    kakaoList.documents.map { it.toKakaoModel() }
                                searchRankView.showKakaoList(toKakaoSearchModelList)
                                searchRankView.showKakaoResult(-1)
                                toggleLastPageCheck = kakaoList.kakaoSearchMeta.isEnd
                            }
                        }

                        override fun onFailure(message: String) {
                            searchRankView.showKakaoResult(1)
                        }
                    })
            }
        } else {
            searchRankView.showKakaoResult(2)
        }
    }

    fun resetData() {
        page = 0
        toggleLastPageCheck = false
    }

}