package com.work.restaurant.view.home.main.presenter

import com.work.restaurant.data.model.BookmarkModel
import com.work.restaurant.data.repository.bookmark.BookmarkRepository

class HomePresenter(
    private val homeView: HomeContract.View,
    private val bookmarkRepository: BookmarkRepository
) : HomeContract.Presenter {
    override fun deleteBookmark(bookmarkModel: BookmarkModel) {

        val toBookmarkEntity =
            bookmarkModel.toBookmarkEntity()

        bookmarkRepository.deleteBookmark(
            toBookmarkEntity,
            callback = { isSuccess ->
                if (isSuccess) {
                    homeView.showBookmarkResult(DELETE_BOOKMARK)
                } else {
                    homeView.showBookmarkResult(FAIL_DELETE)
                }
            })
    }

    override fun addBookmark(bookmarkModel: BookmarkModel) {

        val toBookmarkEntity =
            bookmarkModel.toBookmarkEntity()

        bookmarkRepository.addBookmark(
            toBookmarkEntity,
            callback = { isSuccess ->
                if (isSuccess) {
                    homeView.showBookmarkResult(ADD_BOOKMARK)
                } else {
                    homeView.showBookmarkResult(FAIL_ADD)
                }
            })
    }

    companion object {

        const val FAIL_DELETE = 0
        const val FAIL_ADD = 1
        const val DELETE_BOOKMARK = 2
        const val ADD_BOOKMARK = 3

    }

}