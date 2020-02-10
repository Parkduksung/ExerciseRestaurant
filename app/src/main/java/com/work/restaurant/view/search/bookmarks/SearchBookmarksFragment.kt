package com.work.restaurant.view.search.bookmarks

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.work.restaurant.Injection
import com.work.restaurant.R
import com.work.restaurant.data.model.BookmarkModel
import com.work.restaurant.view.adapter.AdapterDataListener
import com.work.restaurant.view.base.BaseFragment
import com.work.restaurant.view.search.bookmarks.adapter.BookMarkAdapter
import com.work.restaurant.view.search.bookmarks.presenter.SearchBookmarksContract
import com.work.restaurant.view.search.bookmarks.presenter.SearchBookmarksPresenter
import com.work.restaurant.view.search.lookfor.SearchLookForActivity
import kotlinx.android.synthetic.main.search_bookmarks_fragment.*

class SearchBookmarksFragment : BaseFragment(R.layout.search_bookmarks_fragment),
    View.OnClickListener, AdapterDataListener.GetBookmarkData,
    SearchBookmarksContract.View {

    private val bookMarkAdapter: BookMarkAdapter by lazy { BookMarkAdapter() }
    private lateinit var presenter: SearchBookmarksPresenter


    override fun showBookmarkDeleteResult(msg: String) {
        when (msg) {
            RESULT_SUCCESS -> {
                this.activity?.runOnUiThread {
                    Toast.makeText(this.context, "제거성공.", Toast.LENGTH_LONG).show()
                }

            }
            RESULT_FAILURE -> {
                this.activity?.runOnUiThread {
                    Toast.makeText(this.context, "제거실패.", Toast.LENGTH_LONG).show()
                }
            }
        }

    }

    override fun getBookmarkData(select: Int, data: BookmarkModel) {
        when (select) {
            SELECT_URL -> {
                val intent = Intent(activity?.application, SearchLookForActivity()::class.java)
                intent.putExtra(BOOKMARK_DATA, data.bookmarkUrl)
                intent.putExtra(BOOKMARK_TOGGLE, true)
                startActivity(intent)
            }
            SELECT_DELETE -> {
                presenter.deleteBookmark(data)
            }
        }
    }


    override fun showBookmarksList(bookmarkModelList: List<BookmarkModel>) {

        val bookmarkDeduplicationSet = mutableSetOf<BookmarkModel>()

        bookmarkDeduplicationSet.addAll(bookmarkModelList)

        this.activity?.runOnUiThread {
            recyclerview_bookmark.run {
                this.adapter = bookMarkAdapter
                bookMarkAdapter.clearListData()
                bookMarkAdapter.addAllData(bookmarkDeduplicationSet.toList())
                layoutManager = LinearLayoutManager(this.context)
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {

        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = SearchBookmarksPresenter(
            this,
            Injection.provideBookmarkRepository()
        )

        bookMarkAdapter.setItemClickListener(this)

        presenter.getBookmarksList()

    }

    override fun onResume() {
        super.onResume()
        presenter.getBookmarksList()
    }

    companion object {

        private const val TAG = "SearchLikeFragment"

        private const val SELECT_URL = 1
        private const val SELECT_DELETE = 2
        private const val RESULT_SUCCESS = "success"
        private const val RESULT_FAILURE = "error"

        const val BOOKMARK_DATA = "bookmark_data"
        const val BOOKMARK_TOGGLE = "bookmark_toggle"

    }


}