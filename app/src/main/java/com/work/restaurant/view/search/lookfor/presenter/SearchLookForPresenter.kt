package com.work.restaurant.view.search.lookfor.presenter

import com.work.restaurant.data.model.BookmarkModel
import com.work.restaurant.data.model.KakaoSearchModel
import com.work.restaurant.data.repository.bookmark.BookmarkRepository
import com.work.restaurant.data.repository.bookmark.BookmarkRepositoryCallback
import com.work.restaurant.data.repository.kakao.KakaoRepository
import com.work.restaurant.data.repository.kakao.KakaoRepositoryCallback
import com.work.restaurant.network.model.kakaoSearch.KakaoSearchResponse
import com.work.restaurant.network.room.entity.BookmarkEntity
import com.work.restaurant.util.App

class SearchLookForPresenter(
    private val searchLookForView: SearchLookForContract.View,
    private val kakaoRepository: KakaoRepository,
    private val bookmarkRepository: BookmarkRepository
) :
    SearchLookForContract.Presenter {

    private var toggleLastPageCheck = false
    private var page = 0
    private val searchList: MutableList<KakaoSearchModel> by lazy {
        mutableListOf<KakaoSearchModel>()
    }


    override fun addBookmark(bookmarkModel: BookmarkModel) {
        val toBookmarkEntity = bookmarkModel.toBookmarkEntity()

        bookmarkRepository.addBookmark(
            toBookmarkEntity,
            object : BookmarkRepositoryCallback.AddBookmarkCallback {
                override fun onSuccess() {
                    searchLookForView.showBookmarkResult(1)
                }

                override fun onFailure() {
                    searchLookForView.showBookmarkResult(3)
                }
            })
    }

    override fun deleteBookmark(bookmarkModel: BookmarkModel) {

        val toBookmarkEntity = bookmarkModel.toBookmarkEntity()

        bookmarkRepository.deleteBookmark(
            toBookmarkEntity,
            object : BookmarkRepositoryCallback.DeleteBookmarkCallback {
                override fun onSuccess() {
                    searchLookForView.showBookmarkResult(2)
                }

                override fun onFailure() {
                    searchLookForView.showBookmarkResult(3)
                }

            })
    }


    override fun searchLook(searchItem: String) {

        getSearchKakaoList(searchItem)

    }


    private fun getSearchKakaoList(searchItem: String) {

        if (!toggleLastPageCheck) {
            ++page
            kakaoRepository.getSearchKakaoList(searchItem, page, object : KakaoRepositoryCallback {
                override fun onSuccess(kakaoList: KakaoSearchResponse) {
                    if (!kakaoList.kakaoSearchMeta.isEnd) {
                        val toKakaoModel = mutableListOf<KakaoSearchModel>()
                        kakaoList.documents.forEach {
                            if (it.categoryName.contains("스포츠,레저 > 스포츠시설 > 헬스클럽")) {
                                toKakaoModel.add(it.toKakaoModel())
                            }
                        }
                        searchList.addAll(toKakaoModel)
                        getSearchKakaoList(searchItem)
                    } else {
                        val toKakaoModel = mutableListOf<KakaoSearchModel>()
                        kakaoList.documents.forEach {
                            if (it.categoryName.contains("스포츠,레저 > 스포츠시설 > 헬스클럽")) {
                                toKakaoModel.add(it.toKakaoModel())
                            }
                        }
                        searchList.addAll(toKakaoModel)
                        toggleLastPageCheck = kakaoList.kakaoSearchMeta.isEnd
                        getSearchKakaoList(searchItem)
                    }
                }

                override fun onFailure(message: String) {
                    resetData()
                }
            })
        } else {
            if (App.prefs.login_state && App.prefs.login_state_id.isNotEmpty()) {
                displayAlreadyBookmark(searchList)
            } else {
                val toDisplayBookmarkKakaoModel = searchList.map {
                    it.toDisplayBookmarkKakaoModel(false)
                }
                searchLookForView.showSearchLook(toDisplayBookmarkKakaoModel)
            }
        }

    }

    fun resetData() {
        page = 0
        toggleLastPageCheck = false
        searchList.clear()
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

                    searchLookForView.showSearchLook(displayBookmarkKakaoList)

                }

                override fun onFailure() {
                    searchLookForView.showSearchNoFind()
                }
            })

    }
}