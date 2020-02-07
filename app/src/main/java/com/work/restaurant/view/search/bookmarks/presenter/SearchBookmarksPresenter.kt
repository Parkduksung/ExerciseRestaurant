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

        val toBookmarkEntity = bookmarkModel.toBookmarkEntity()

        bookmarkRepository.deleteBookmark(
            toBookmarkEntity,
            object : BookmarkRepositoryCallback.DeleteBookmarkCallback {
                override fun onSuccess(msg: String) {
                    searchBookmarksView.showBookmarkDeleteResult(msg)
                }

                override fun onFailure(msg: String) {
                    searchBookmarksView.showBookmarkDeleteResult(msg)
                }
            })

    }

    override fun getBookmarksList() {
        bookmarkRepository.getAllList(object : BookmarkRepositoryCallback.GetAllList {
            override fun onSuccess(list: List<BookmarkEntity>) {

                val toBookmarkModelList = list.map { it.toBookmarkModel() }
                searchBookmarksView.showBookmarksList(toBookmarkModelList)
            }

            override fun onFailure(msg: String) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

    }


}