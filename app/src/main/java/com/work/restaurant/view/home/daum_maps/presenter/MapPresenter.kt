package com.work.restaurant.view.home.daum_maps.presenter

import com.work.restaurant.data.model.KakaoSearchModel
import com.work.restaurant.data.repository.bookmark.BookmarkRepository
import com.work.restaurant.data.repository.kakao.KakaoRepository
import com.work.restaurant.network.api.KakaoApi
import com.work.restaurant.util.RelateLogin

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
        sort: String = SORT_ACCURACY,
        itemCount: Int,
        radius: Int
    ) {

        if (!toggleLastPageCheck) {
            if (itemCount >= (page * 15)) {
                ++page
                kakaoRepository.getData(
                    currentX,
                    currentY,
                    page,
                    sort,
                    radius,

                    callback = { list ->

                        if (list != null) {
                            if (list.documents.isEmpty()) {
                                val toKakaoSearchModelList =
                                    list.documents.map { it.toKakaoModel() }
                                mapView.showSearchData(toKakaoSearchModelList, NO_NEW_RESULT)
                            } else {
                                if (RelateLogin.loginState()) {

                                    if (!list.kakaoSearchMeta.isEnd) {

                                        val toKakaoSearchModelList =
                                            list.documents.map { it.toKakaoModel() }

                                        mapView.showSearchData(toKakaoSearchModelList, FINAL_RESULT)

                                    } else {
                                        val toKakaoSearchModelList =
                                            list.documents.map { it.toKakaoModel() }

                                        toggleLastPageCheck =
                                            list.kakaoSearchMeta.isEnd

                                        mapView.showSearchData(
                                            toKakaoSearchModelList,
                                            REMAIN_RESULT
                                        )
                                        mapView.showKakaoData(toKakaoSearchModelList)

                                    }

                                } else {
                                    if (!list.kakaoSearchMeta.isEnd) {

                                        val toKakaoSearchModelList =
                                            list.documents.map { it.toKakaoModel() }

                                        mapView.showSearchData(toKakaoSearchModelList, FINAL_RESULT)

                                    } else {
                                        val toKakaoSearchModelList =
                                            list.documents.map { it.toKakaoModel() }

                                        toggleLastPageCheck =
                                            list.kakaoSearchMeta.isEnd

                                        mapView.showSearchData(
                                            toKakaoSearchModelList,
                                            REMAIN_RESULT
                                        )

                                        mapView.showKakaoData(toKakaoSearchModelList)
                                    }
                                }
                            }
                        } else {
                            mapView.showSearchData(emptyList(), NO_NEW_RESULT)
                        }
                    })
            }
        } else {

        }
    }


    override fun getMarkerData(x: Double, y: Double, markerName: String) {
        kakaoRepository.getKakaoItemInfo(
            x,
            y,
            markerName,
            callback = { item ->
                if (item != null) {
                    val toKakaoSearchModel =
                        item.map { it.toKakaoModel() }

                    if (toKakaoSearchModel.isNotEmpty()) {
                        if (RelateLogin.loginState()) {
                            displayAlreadyBookmark(toKakaoSearchModel)
                        } else {
                            val toDisplayBookmarkKakaoModel =
                                toKakaoSearchModel.map {
                                    it.toDisplayBookmarkKakaoModel(false)
                                }
                            mapView.showMarkerData(toDisplayBookmarkKakaoModel)
                        }
                    }
                }
            })
    }

    override fun getKakaoData(currentX: Double, currentY: Double) {
        kakaoRepository.getData(
            currentX,
            currentY,
            PAGE_NUM,
            SORT_ACCURACY,
            KakaoApi.RADIUS,
            callback = { list ->
                if (list != null) {
                    val toKakaoModelList =
                        list.documents.map { it.toKakaoModel() }

                    val toDistanceArrayList =
                        toKakaoModelList.sortedBy { it.distance.toInt() }

                    mapView.showKakaoData(
                        toDistanceArrayList
                    )
                }
            })
    }

    private fun displayAlreadyBookmark(searchKakaoList: List<KakaoSearchModel>) {
        bookmarkRepository.getAllList(
            RelateLogin.getLoginId(),
            callback = { getList ->
                if (getList != null) {
                    val convertFromKakaoListToBookmarkModel =
                        searchKakaoList.map { it.toBookmarkModel(RelateLogin.getLoginId()) }

                    val convertFromBookmarkEntityToBookmarkModel =
                        getList.map { it.toBookmarkModel() }

                    val displayBookmarkKakaoList =
                        convertFromKakaoListToBookmarkModel.map { bookmarkModel ->

                            bookmarkModel.toDisplayBookmarkKakaoList(
                                convertFromBookmarkEntityToBookmarkModel.contains(
                                    bookmarkModel
                                )
                            )
                        }

                    mapView.showMarkerData(displayBookmarkKakaoList)
                }
            })
    }


    override fun resetData() {
        page = 0
        toggleLastPageCheck = false
    }


    companion object {
        private const val PAGE_NUM = 1
        private const val SORT_DISTANCE = "distance"
        private const val SORT_ACCURACY = "accuracy"

        const val NO_NEW_RESULT = 0
        const val REMAIN_RESULT = 1
        const val FINAL_RESULT = 2

    }
}