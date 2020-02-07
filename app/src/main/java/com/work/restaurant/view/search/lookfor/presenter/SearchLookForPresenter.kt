package com.work.restaurant.view.search.lookfor.presenter

import com.work.restaurant.data.model.BookmarkModel
import com.work.restaurant.data.repository.bookmark.BookmarkRepository
import com.work.restaurant.data.repository.bookmark.BookmarkRepositoryCallback
import com.work.restaurant.data.repository.kakao.KakaoRepository
import com.work.restaurant.data.repository.kakao.KakaoRepositoryCallback
import com.work.restaurant.network.model.kakaoSearch.KakaoSearchDocuments

class SearchLookForPresenter(
    private val searchLookForView: SearchLookForContract.View,
    private val kakaoRepository: KakaoRepository,
    private val bookmarkRepository: BookmarkRepository
) :
    SearchLookForContract.Presenter {
    override fun addBookmark(bookmarkModel: BookmarkModel) {
        val toBookmarkEntity = bookmarkModel.toBookmarkEntity()

        bookmarkRepository.addBookmark(
            toBookmarkEntity,
            object : BookmarkRepositoryCallback.AddBookmarkCallback {
                override fun onSuccess(msg: String) {
                    searchLookForView.showBookmarkResult(msg)
                }

                override fun onFailure(msg: String) {
                    searchLookForView.showBookmarkResult(msg)
                }
            })
    }

    override fun deleteBookmark(bookmarkModel: BookmarkModel) {

        val toBookmarkEntity = bookmarkModel.toBookmarkEntity()

        bookmarkRepository.deleteBookmark(
            toBookmarkEntity,
            object : BookmarkRepositoryCallback.DeleteBookmarkCallback {
                override fun onSuccess(msg: String) {
                    searchLookForView.showBookmarkResult(msg)
                }

                override fun onFailure(msg: String) {
                    searchLookForView.showBookmarkResult(msg)
                }

            })

    }

    override fun backPage() {
        searchLookForView.showBackPage()
    }

    override fun searchLook(searchItem: String) {

        kakaoRepository.getKakaoItemInfo(searchItem,
            object : KakaoRepositoryCallback.KakaoItemInfoCallback {
                override fun onSuccess(item: List<KakaoSearchDocuments>) {
                    val toKakaoSearchModelList = item.map {
                        it.toKakaoModel()
                    }
                    searchLookForView.showSearchLook(toKakaoSearchModelList)
                }

                override fun onFailure(message: String) {
                    searchLookForView.showSearchNoFind()
                }
            })

    }
}