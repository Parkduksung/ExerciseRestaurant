package com.work.restaurant.view.search.bookmarks

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.work.restaurant.R
import com.work.restaurant.data.model.BookmarkModel
import com.work.restaurant.ext.showToast
import com.work.restaurant.util.RelateLogin
import com.work.restaurant.view.adapter.AdapterDataListener
import com.work.restaurant.view.adapter.RenewBookmarkAndRankListener
import com.work.restaurant.view.base.BaseFragment
import com.work.restaurant.view.search.bookmarks.adapter.BookMarkAdapter
import com.work.restaurant.view.search.bookmarks.presenter.SearchBookmarksContract
import com.work.restaurant.view.search.bookmarks.presenter.SearchBookmarksPresenter
import com.work.restaurant.view.search.lookfor.SearchLookForActivity
import kotlinx.android.synthetic.main.search_bookmarks_fragment.*
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf

class SearchBookmarksFragment : BaseFragment(R.layout.search_bookmarks_fragment),
    AdapterDataListener.GetBookmarkData,
    SearchBookmarksContract.View {

    private val bookMarkAdapter: BookMarkAdapter by lazy { BookMarkAdapter() }

    private lateinit var presenter: SearchBookmarksContract.Presenter

    private lateinit var renewBookmarkAndRankListener: RenewBookmarkAndRankListener


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as? RenewBookmarkAndRankListener)?.let {
            renewBookmarkAndRankListener = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = get { parametersOf(this) }

        rv_bookmark.run {
            this.adapter = bookMarkAdapter
            layoutManager = LinearLayoutManager(this.context)
        }

        bookMarkAdapter.setItemClickListener(this)

        renewBookmark()

    }


    override fun showBookmarkDeleteResult(msg: Boolean) {
        when (msg) {
            SearchBookmarksPresenter.RESULT_SUCCESS -> {
                renewBookmarkAndRankListener.renewBookmarkAndRank()
                showToast(getString(R.string.bookmark_state_no))

            }
            SearchBookmarksPresenter.RESULT_FAILURE -> {
                showToast(getString(R.string.bookmark_delete_no_message))
            }
        }
    }

    override fun getBookmarkData(select: Int, data: BookmarkModel) {
        when (select) {
            BookMarkAdapter.SELECT_URL -> {
                val intent =
                    Intent(activity?.application, SearchLookForActivity()::class.java).apply {
                        putExtra(BOOKMARK_DATA, data.bookmarkUrl)
                        putExtra(BOOKMARK_TOGGLE, true)
                    }
                startActivity(intent)
            }
            BookMarkAdapter.DELETE_BOOKMARK -> {
                presenter.deleteBookmark(data)
            }
        }
    }

    override fun showBookmarksList(bookmarkModelList: List<BookmarkModel>) {

        val bookmarkDeduplicationSet =
            mutableSetOf<BookmarkModel>()

        bookmarkDeduplicationSet.addAll(bookmarkModelList)

        bookMarkAdapter.clearListData()
        bookMarkAdapter.addAllData(bookmarkDeduplicationSet.toList())

    }

    override fun showBookmarkPresenceOrAbsence(state: Boolean) {
        if (!state) {
            tv_bookmark_message.text =
                getString(R.string.bookmark_no_item)
        }
        rv_bookmark.isVisible = state
        tv_bookmark_message.isInvisible = state
    }

    override fun showLoginState(state: Boolean) {
        if (!state) {
            tv_bookmark_message.text =
                getString(R.string.bookmark_no_login)
        }
        rv_bookmark.isVisible = state
        tv_bookmark_message.isInvisible = state
    }

    fun renewBookmark() {
        presenter.getBookmarksList(RelateLogin.getLoginId())
    }

    override fun onResume() {
        super.onResume()
        renewBookmark()
    }

    companion object {

        private const val TAG = "SearchBookmarksFragment"

        const val BOOKMARK_DATA = "bookmark_data"
        const val BOOKMARK_TOGGLE = "bookmark_toggle"

    }


}