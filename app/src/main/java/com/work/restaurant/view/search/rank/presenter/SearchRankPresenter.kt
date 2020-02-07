package com.work.restaurant.view.search.rank.presenter

import android.util.Log
import com.work.restaurant.data.model.BookmarkModel
import com.work.restaurant.data.repository.bookmark.BookmarkRepository
import com.work.restaurant.data.repository.bookmark.BookmarkRepositoryCallback
import com.work.restaurant.data.repository.kakao.KakaoRepository
import com.work.restaurant.data.repository.kakao.KakaoRepositoryCallback
import com.work.restaurant.network.model.kakaoSearch.KakaoSearchDocuments

class SearchRankPresenter(
    private val searchRankView: SearchRankContract.View,
    private val kakaoRepository: KakaoRepository,
    private val bookmarkRepository: BookmarkRepository
) :
    SearchRankContract.Presenter {
    override fun addBookmarkKakaoItem(bookmarkModel: BookmarkModel) {

        val toBookmarkEntity = bookmarkModel.toBookmarkEntity()

        bookmarkRepository.addBookmark(
            toBookmarkEntity,
            object : BookmarkRepositoryCallback.AddBookmarkCallback {
                override fun onSuccess(msg: String) {
                    searchRankView.showBookmarkResult(msg)
                }

                override fun onFailure(msg: String) {
                    searchRankView.showBookmarkResult(msg)
                }
            })
    }


    override fun getKakaoList() {

        kakaoRepository.getKakaoResult(
            126.73694610595703,
            37.538692474365234,
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