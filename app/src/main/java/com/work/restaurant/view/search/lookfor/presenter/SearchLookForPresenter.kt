package com.work.restaurant.view.search.lookfor.presenter

import com.work.restaurant.data.model.BookmarkModel
import com.work.restaurant.data.model.KakaoSearchModel
import com.work.restaurant.data.repository.bookmark.BookmarkRepository
import com.work.restaurant.data.repository.kakao.KakaoRepository
import com.work.restaurant.util.RelateLogin

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


    override fun addBookmark(bookmarkModel: BookmarkModel, selectPosition: Int) {

        val toBookmarkEntity =
            bookmarkModel.toBookmarkEntity()

        bookmarkRepository.addBookmark(
            toBookmarkEntity,
            callback = { isSuccess ->
                if (isSuccess) {
                    searchLookForView.showBookmarkResult(ADD_BOOKMARK, selectPosition)
                } else {
                    searchLookForView.showBookmarkResult(FAIL_ADD, NOT_SELECT)
                }
            })
    }

    override fun deleteBookmark(bookmarkModel: BookmarkModel, selectPosition: Int) {

        val toBookmarkEntity =
            bookmarkModel.toBookmarkEntity()

        bookmarkRepository.deleteBookmark(
            toBookmarkEntity,
            callback = { isSuccess ->
                if (isSuccess) {
                    searchLookForView.showBookmarkResult(DELETE_BOOKMARK, selectPosition)
                } else {
                    searchLookForView.showBookmarkResult(FAIL_DELETE, NOT_SELECT)
                }
            })

    }

    override fun searchLook(searchItem: String) {
        getSearchKakaoList(searchItem)
    }

    private fun getSearchKakaoList(searchItem: String) {

        if (!toggleLastPageCheck) {
            ++page
            kakaoRepository.getSearchKakaoList(
                searchItem,
                page,
                callback = { list ->
                    if (list != null) {
                        if (!list.kakaoSearchMeta.isEnd) {
                            val toKakaoModel =
                                mutableListOf<KakaoSearchModel>()

                            list.documents.forEach {
                                if (it.categoryName.contains(CATEGORY)) {
                                    toKakaoModel.add(it.toKakaoModel())
                                }
                            }
                            searchList.addAll(toKakaoModel)
                            getSearchKakaoList(searchItem)
                        } else {
                            val toKakaoModel =
                                mutableListOf<KakaoSearchModel>()

                            list.documents.forEach {
                                if (it.categoryName.contains(CATEGORY)) {
                                    toKakaoModel.add(it.toKakaoModel())
                                }
                            }
                            searchList.addAll(toKakaoModel)
                            toggleLastPageCheck = list.kakaoSearchMeta.isEnd
                            getSearchKakaoList(searchItem)
                        }
                    } else {
                        resetData()
                    }
                })
        } else {
            if (RelateLogin.loginState()) {
                displayAlreadyBookmark(searchList)
            } else {
                val toDisplayBookmarkKakaoModel =
                    searchList.map {
                        it.toDisplayBookmarkKakaoModel(false)
                    }
                searchLookForView.showSearchLook(toDisplayBookmarkKakaoModel)
            }
        }
    }

    override fun resetData() {
        page = 0
        toggleLastPageCheck = false
        searchList.clear()
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
                        convertFromKakaoListToBookmarkModel.map {
                            if (convertFromBookmarkEntityToBookmarkModel.contains(it)) {
                                it.toDisplayBookmarkKakaoList(true)
                            } else {
                                it.toDisplayBookmarkKakaoList(false)
                            }
                        }
                    searchLookForView.showSearchLook(displayBookmarkKakaoList)
                }
            })
    }

    companion object {

        private const val CATEGORY = "스포츠,레저 > 스포츠시설 > 헬스클럽"

        const val NOT_SELECT = 0

        const val FAIL_ADD = 0
        const val FAIL_DELETE = 1
        const val ADD_BOOKMARK = 2
        const val DELETE_BOOKMARK = 3

    }
}