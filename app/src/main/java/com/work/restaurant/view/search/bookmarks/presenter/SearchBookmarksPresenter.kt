package com.work.restaurant.view.search.bookmarks.presenter

import com.work.restaurant.data.model.BookmarkModel
import com.work.restaurant.data.repository.bookmark.BookmarkRepository
import com.work.restaurant.data.repository.bookmark.BookmarkRepositoryCallback
import com.work.restaurant.network.room.entity.BookmarkEntity

class SearchBookmarksPresenter(
    private val searchBookmarksView: SearchBookmarksContract.View,
    private val bookmarkRepository: BookmarkRepository
) : SearchBookmarksContract.Presenter {

    override fun deleteBookmark(bookmarkModel: BookmarkModel) {

        val toBookmarkEntity =
            bookmarkModel.toBookmarkEntity()

        bookmarkRepository.deleteBookmark(
            toBookmarkEntity,
            object : BookmarkRepositoryCallback.DeleteBookmarkCallback {
                override fun onSuccess() {
                    searchBookmarksView.showBookmarkDeleteResult(RESULT_SUCCESS)
                }

                override fun onFailure() {
                    searchBookmarksView.showBookmarkDeleteResult(RESULT_FAILURE)
                }
            })
    }

    override fun getBookmarksList(userId: String) {

        if (userId.isNotEmpty()) {
            searchBookmarksView.showLoginState(true)

            bookmarkRepository.getAllList(
                userId, object : BookmarkRepositoryCallback.GetAllList {
                    override fun onSuccess(list: List<BookmarkEntity>) {
                        if (list.isNotEmpty()) {

                            val toBookmarkModelList =
                                list.map { it.toBookmarkModel() }
                            searchBookmarksView.showBookmarksList(toBookmarkModelList)
                            searchBookmarksView.showBookmarkPresenceOrAbsence(true)

                        } else {
                            searchBookmarksView.showBookmarkPresenceOrAbsence(false)
                        }
                    }

                    override fun onFailure() {

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