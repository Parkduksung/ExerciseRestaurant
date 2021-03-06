package com.work.restaurant.view.search.rank.presenter

import com.work.restaurant.data.model.BookmarkModel
import com.work.restaurant.data.model.KakaoSearchModel
import com.work.restaurant.data.repository.bookmark.BookmarkRepository
import com.work.restaurant.data.repository.kakao.KakaoRepository
import com.work.restaurant.network.api.KakaoApi
import com.work.restaurant.util.RelateLogin
import com.work.restaurant.view.search.rank.SearchRankFragment

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
            callback = { item ->
                if (item != null) {
                    val toKakaoLocationToAddressModel =
                        item.map { it.toKakaoLocationToAddressModel() }
                    searchRankView.showCurrentLocation(toKakaoLocationToAddressModel[0].addressName)
                } else {
                    searchRankView.showKakaoResult(LOAD_LOCATION_ERROR)
                }
            })
    }


    override fun getCurrentLocation(addressName: String, itemCount: Int, sort: String) {
        kakaoRepository.getKakaoAddressLocation(
            addressName,
            callback = { item ->
                if (item != null) {
                    val currentX = (item[0].x).toDouble()
                    val currentY = (item[0].y).toDouble()
                    getKakaoRankList(currentX, currentY, sort, itemCount)
                } else {
                    searchRankView.showKakaoResult(LOAD_DATA_ERROR)
                }
            })
    }

    override fun addBookmarkKakaoItem(bookmarkModel: BookmarkModel, selectPosition: Int) {

        val toBookmarkEntity =
            bookmarkModel.toBookmarkEntity()

        bookmarkRepository.addBookmark(
            toBookmarkEntity,
            callback = { isSuccess ->
                if (isSuccess) {
                    searchRankView.showBookmarkResult(ADD_BOOKMARK, selectPosition)
                } else {
                    searchRankView.showBookmarkResult(FAIL_ADD, NOT_SELECT)
                }
            })
    }

    override fun deleteBookmarkKakaoItem(bookmarkModel: BookmarkModel, selectPosition: Int) {
        val toBookmarkEntity =
            bookmarkModel.toBookmarkEntity()

        bookmarkRepository.deleteBookmark(
            toBookmarkEntity,
            callback = { isSuccess ->
                if (isSuccess) {
                    searchRankView.showBookmarkResult(DELETE_BOOKMARK, selectPosition)
                } else {
                    searchRankView.showBookmarkResult(FAIL_DELETE, NOT_SELECT)
                }
            })

    }

    private fun getKakaoRankList(currentX: Double, currentY: Double, sort: String, itemCount: Int) {

        if (!toggleLastPageCheck) {
            if (itemCount >= (page * SearchRankFragment.INIT_COUNT)) {
                ++page
                searchRankView.showLoadingState(true)

                kakaoRepository.getData(
                    currentX,
                    currentY,
                    page,
                    sort,
                    KakaoApi.RADIUS,
                    callback = { kakaoResponse ->
                        if (kakaoResponse != null) {
                            if (RelateLogin.loginState()) {
                                if (!kakaoResponse.kakaoSearchMeta.isEnd) {

                                    val toKakaoSearchModelList =
                                        kakaoResponse.documents.map { it.toKakaoModel() }

                                    displayAlreadyBookmark(toKakaoSearchModelList)

                                } else {
                                    val toKakaoSearchModelList =
                                        kakaoResponse.documents.map { it.toKakaoModel() }

                                    displayAlreadyBookmark(toKakaoSearchModelList)

                                    toggleLastPageCheck = kakaoResponse.kakaoSearchMeta.isEnd
                                }

                            } else {
                                if (!kakaoResponse.kakaoSearchMeta.isEnd) {

                                    val toKakaoSearchModelList =
                                        kakaoResponse.documents.map { it.toKakaoModel() }

                                    val toDisplayBookmarkKakaoModel =
                                        toKakaoSearchModelList.map {
                                            it.toDisplayBookmarkKakaoModel(false)
                                        }
                                    searchRankView.showKakaoList(toDisplayBookmarkKakaoModel)

                                } else {
                                    val toKakaoSearchModelList =
                                        kakaoResponse.documents.map { it.toKakaoModel() }

                                    val toDisplayBookmarkKakaoModel =
                                        toKakaoSearchModelList.map {
                                            it.toDisplayBookmarkKakaoModel(false)
                                        }
                                    searchRankView.showKakaoList(toDisplayBookmarkKakaoModel)

                                    toggleLastPageCheck = kakaoResponse.kakaoSearchMeta.isEnd
                                }
                            }
                        } else {
                            searchRankView.showKakaoResult(LOAD_DATA_ERROR)
                        }
                    })
            }
        } else {
            searchRankView.showKakaoResult(NOT_REMAIN_DATA)
        }
    }

    private fun displayAlreadyBookmark(searchKakaoList: List<KakaoSearchModel>) {
        bookmarkRepository.getAllList(
            RelateLogin.getLoginId(),
            callback = { getList ->

                if (getList != null) {
                    val toDisplayBookmarkModel =
                        searchKakaoList.map { it.toDisplayBookmarkKakaoModel(false) }

                    val getUserBookmarkModel =
                        getList.map { it.toBookmarkModel() }

                    for (i in 0 until toDisplayBookmarkModel.size) {
                        for (j in 0 until getList.size) {
                            if (toDisplayBookmarkModel[i].displayAddress == getUserBookmarkModel[j].bookmarkAddress &&
                                toDisplayBookmarkModel[i].displayName == getUserBookmarkModel[j].bookmarkName &&
                                toDisplayBookmarkModel[i].displayUrl == getUserBookmarkModel[j].bookmarkUrl
                            ) {
                                toDisplayBookmarkModel[i].toggleBookmark = true
                            }
                        }
                    }
                    searchRankView.showKakaoList(toDisplayBookmarkModel)
                }
            })
    }

    override fun resetData() {
        page = 0
        toggleLastPageCheck = false
    }


    companion object {

        const val LOAD_LOCATION_ERROR = 0
        const val LOAD_DATA_ERROR = 1
        const val NOT_REMAIN_DATA = 2

        const val NOT_SELECT = 0

        const val FAIL_ADD = 0
        const val FAIL_DELETE = 1
        const val ADD_BOOKMARK = 2
        const val DELETE_BOOKMARK = 3
    }

}