package com.work.restaurant.view.search.bookmarks

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.work.restaurant.Injection
import com.work.restaurant.R
import com.work.restaurant.data.model.BookmarkModel
import com.work.restaurant.util.App
import com.work.restaurant.view.adapter.AdapterDataListener
import com.work.restaurant.view.adapter.RenewBookmarkAndRankListener
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
    private lateinit var renewBookmarkAndRankListener: RenewBookmarkAndRankListener


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as? RenewBookmarkAndRankListener)?.let {
            renewBookmarkAndRankListener = it
        }
    }


    override fun showBookmarkDeleteResult(msg: Boolean) {
        when (msg) {
            RESULT_SUCCESS -> {
                renewBookmarkAndRankListener.renewBookmarkAndRank()
                Toast.makeText(
                    this.context, getString(
                        R.string.bookmark_state_no
                    ), Toast.LENGTH_LONG
                ).show()
            }
            RESULT_FAILURE -> {
                Toast.makeText(
                    this.context,
                    getString(R.string.bookmark_delete_no_message),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    }

    override fun getBookmarkData(select: Int, data: BookmarkModel) {
        when (select) {
            SELECT_URL -> {
                val intent =
                    Intent(activity?.application, SearchLookForActivity()::class.java).apply {
                        putExtra(BOOKMARK_DATA, data.bookmarkUrl)
                        putExtra(BOOKMARK_TOGGLE, true)
                    }
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
        recyclerview_bookmark.run {
            bookMarkAdapter.clearListData()
            bookMarkAdapter.addAllData(bookmarkDeduplicationSet.toList())
        }
    }

    override fun showNotLoginBookmark() {
        recyclerview_bookmark.run {
            bookMarkAdapter.clearListData()
            layoutManager = LinearLayoutManager(this.context)
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
        recyclerview_bookmark.run {
            this.adapter = bookMarkAdapter
            layoutManager = LinearLayoutManager(this.context)
        }

        bookMarkAdapter.setItemClickListener(this)
        presenter.getBookmarksList(App.prefs.login_state_id)
    }


    fun renewBookmark() {
        presenter.getBookmarksList(App.prefs.login_state_id)
    }


    override fun onResume() {
        super.onResume()
        presenter.getBookmarksList(App.prefs.login_state_id)
    }

    companion object {

        private const val TAG = "SearchLikeFragment"

        private const val SELECT_URL = 1
        private const val SELECT_DELETE = 2
        private const val RESULT_SUCCESS = true
        private const val RESULT_FAILURE = false

        const val BOOKMARK_DATA = "bookmark_data"
        const val BOOKMARK_TOGGLE = "bookmark_toggle"

    }


}