package com.work.restaurant.view.home.daum_maps.presenter

import com.work.restaurant.data.model.KakaoSearchModel
import com.work.restaurant.data.repository.bookmark.BookmarkRepository
import com.work.restaurant.data.repository.bookmark.BookmarkRepositoryCallback
import com.work.restaurant.data.repository.kakao.KakaoRepository
import com.work.restaurant.data.repository.kakao.KakaoRepositoryCallback
import com.work.restaurant.network.api.KakaoApi
import com.work.restaurant.network.model.kakaoSearch.KakaoSearchDocuments
import com.work.restaurant.network.model.kakaoSearch.KakaoSearchResponse
import com.work.restaurant.network.room.entity.BookmarkEntity
import com.work.restaurant.util.App

class MapPresenter(
    private val mapView: MapContract.View,
    private val kakaoRepository: KakaoRepository,
    private val bookmarkRepository: BookmarkRepository
) : MapContract.Presenter {


    private var page = 0
    private var toggleLastPageCheck = false


    override fun getThisPositionData(
        currentX: Double,
        currentY: Double,
        radius: Int,
        itemCount: Int
    ) {
        getKakaoRankList(currentX, currentY, SORT_ACCURACY, itemCount, radius)
    }


    private fun getKakaoRankList(
        currentX: Double,
        currentY: Double,
        sort: String,
        itemCount: Int,
        radius: Int
    ) {

        if (!toggleLastPageCheck) {
            if (itemCount >= (page * 15)) {
                ++page
                kakaoRepository.getKakaoResult(
                    currentX,
                    currentY,
                    page,
                    sort,
                    radius,
                    object : KakaoRepositoryCallback {
                        override fun onSuccess(
                            kakaoList: KakaoSearchResponse
                        ) {

                            if (kakaoList.documents.isEmpty()) {
                                val toKakaoSearchModelList =
                                    kakaoList.documents.map { it.toKakaoModel() }
                                mapView.showSearchData(toKakaoSearchModelList, 0)
                            } else {
                                if (App.prefs.login_state && App.prefs.login_state_id.isNotEmpty()) {

                                    if (!kakaoList.kakaoSearchMeta.isEnd) {

                                        val toKakaoSearchModelList =
                                            kakaoList.documents.map { it.toKakaoModel() }

                                        mapView.showSearchData(toKakaoSearchModelList, 2)

                                    } else {
                                        val toKakaoSearchModelList =
                                            kakaoList.documents.map { it.toKakaoModel() }

                                        toggleLastPageCheck = kakaoList.kakaoSearchMeta.isEnd
                                        mapView.showSearchData(toKakaoSearchModelList, 1)
                                        mapView.showKakaoData(toKakaoSearchModelList)

                                    }

                                } else {
                                    if (!kakaoList.kakaoSearchMeta.isEnd) {

                                        val toKakaoSearchModelList =
                                            kakaoList.documents.map { it.toKakaoModel() }

                                        mapView.showSearchData(toKakaoSearchModelList, 2)

                                    } else {
                                        val toKakaoSearchModelList =
                                            kakaoList.documents.map { it.toKakaoModel() }

                                        toggleLastPageCheck = kakaoList.kakaoSearchMeta.isEnd
                                        mapView.showSearchData(toKakaoSearchModelList, 1)

                                        mapView.showKakaoData(toKakaoSearchModelList)
                                    }
                                }
                            }
                        }

                        override fun onFailure(message: String) {
                            val nullKakaoKList = mutableListOf<KakaoSearchModel>()
                            mapView.showSearchData(nullKakaoKList, 0)
                        }
                    })
            }
        } else {

        }
    }


    override fun getMarkerData(x: Double, y: Double, markerName: String) {
        kakaoRepository.getKakaoItemInfo(x, y, markerName,
            object : KakaoRepositoryCallback.KakaoItemInfoCallback {
                override fun onSuccess(item: List<KakaoSearchDocuments>) {
                    val toKakaoSearchModel = item.map { it.toKakaoModel() }

                    if (toKakaoSearchModel.isNotEmpty()) {
                        if (App.prefs.login_state && App.prefs.login_state_id.isNotEmpty()) {
                            displayAlreadyBookmark(toKakaoSearchModel)
                        } else {
                            val toDisplayBookmarkKakaoModel = toKakaoSearchModel.map {
                                it.toDisplayBookmarkKakaoModel(false)
                            }
                            mapView.showMarkerData(toDisplayBookmarkKakaoModel)
                        }

                    }
                }

                override fun onFailure(message: String) {
                }
            })
    }

    override fun getKakaoData(currentX: Double, currentY: Double) {
        kakaoRepository.getKakaoResult(
            currentX,
            currentY,
            PAGENUM,
            SORT_ACCURACY,
            KakaoApi.RADIUS,
            object : KakaoRepositoryCallback {
                override fun onSuccess(kakaoList: KakaoSearchResponse) {
                    val toKakaoModelList = kakaoList.documents.map { it.toKakaoModel() }

                    val toDistanceArrayList = toKakaoModelList.sortedBy { it.distance.toInt() }

                    mapView.showKakaoData(
                        toDistanceArrayList
                    )
                }

                override fun onFailure(message: String) {
                }
            }
        )
    }

    private fun displayAlreadyBookmark(searchKakaoList: List<KakaoSearchModel>) {
        bookmarkRepository.getAllList(
            App.prefs.login_state_id,
            object : BookmarkRepositoryCallback.GetAllList {
                override fun onSuccess(list: List<BookmarkEntity>) {

                    val convertFromKakaoListToBookmarkModel =
                        searchKakaoList.map { it.toBookmarkModel(App.prefs.login_state_id) }

                    val convertFromBookmarkEntityToBookmarkModel =
                        list.map { it.toBookmarkModel() }

                    val displayBookmarkKakaoList = convertFromKakaoListToBookmarkModel.map {
                        if (convertFromBookmarkEntityToBookmarkModel.contains(it)) {
                            it.toDisplayBookmarkKakaoList(true)
                        } else {
                            it.toDisplayBookmarkKakaoList(false)
                        }
                    }

                    mapView.showMarkerData(displayBookmarkKakaoList)

                }

                override fun onFailure() {

                }
            })

    }


    fun resetData() {
        page = 0
        toggleLastPageCheck = false
    }


    companion object {
        private const val PAGENUM = 1
        private const val SORT_DISTANCE = "distance"
        private const val SORT_ACCURACY = "accuracy"
    }
}