package com.work.restaurant.view.search.bookmarks.presenter

import com.work.restaurant.data.model.BookmarkModel
import com.work.restaurant.data.repository.bookmark.BookmarkRepository

class SearchBookmarksPresenter(
    private val searchBookmarksView: SearchBookmarksContract.View,
    private val bookmarkRepository: BookmarkRepository
) : SearchBookmarksContract.Presenter {

    override fun deleteBookmark(bookmarkModel: BookmarkModel) {

        val toBookmarkEntity =
            bookmarkModel.toBookmarkEntity()

        bookmarkRepository.deleteBookmark(
            toBookmarkEntity,
            callback = { isSuccess ->
                if (isSuccess) {
                    searchBookmarksView.showBookmarkDeleteResult(RESULT_SUCCESS)
                } else {
                    searchBookmarksView.showBookmarkDeleteResult(RESULT_FAILURE)
                }
            })
    }

    override fun getBookmarksList(userId: String) {
        if (userId.isNotEmpty()) {
            searchBookmarksView.showLoginState(true)
            bookmarkRepository.getAllList(
                userId,
                callback = { getList ->
                    if (getList != null) {
                        if (getList.isNotEmpty()) {
                            val toBookmarkModelList =
                                getList.map { it.toBookmarkModel() }
                            searchBookmarksView.showBookmarksList(toBookmarkModelList)
                            searchBookmarksView.showBookmarkPresenceOrAbsence(true)

                        } else {
                            searchBookmarksView.showBookmarkPresenceOrAbsence(false)
                        }
                    }
                })
        } else {
            searchBookmarksView.showLoginState(false)
        }
    }

    companion object {

        const val RESULT_SUCCESS = true
        const val RESULT_FAILURE = false

    }


}