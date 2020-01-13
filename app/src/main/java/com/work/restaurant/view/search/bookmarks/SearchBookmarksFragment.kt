package com.work.restaurant.view.search.bookmarks

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.work.restaurant.R
import com.work.restaurant.data.repository.fitness.FitnessItemRepositoryImpl
import com.work.restaurant.data.source.remote.fitness.FitnessCenterRemoteDataSourceImpl
import com.work.restaurant.network.RetrofitInstance
import com.work.restaurant.network.model.FitnessCenterItemResponse
import com.work.restaurant.view.adapter.AdapterDataListener
import com.work.restaurant.view.base.BaseFragment
import com.work.restaurant.view.search.bookmarks.adapter.BookMarkAdapter
import com.work.restaurant.view.search.bookmarks.presenter.SearchBookmarksContract
import com.work.restaurant.view.search.bookmarks.presenter.SearchBookmarksPresenter
import com.work.restaurant.view.search.lookfor.SearchLookForActivity
import com.work.restaurant.view.search.main.SearchFragment
import kotlinx.android.synthetic.main.search_bookmarks_fragment.*

class SearchBookmarksFragment : BaseFragment(R.layout.search_bookmarks_fragment),
    View.OnClickListener, AdapterDataListener,
    SearchBookmarksContract.View {


    override fun showBookmarksList(fitnessList: List<FitnessCenterItemResponse>) {
        recyclerview_bookmark.run {

            this.adapter = bookMarkAdapter

            fitnessList.forEach { fitnessCenterItem ->
                if (fitnessCenterItem.fitnessCenterLikeCount >= 90) {
                    bookMarkAdapter.addData(fitnessCenterItem)
                }
            }

            layoutManager = LinearLayoutManager(this.context)

        }
    }


    private lateinit var bookMarkAdapter: BookMarkAdapter
    private lateinit var presenter: SearchBookmarksPresenter

    override fun onClick(v: View?) {
        when (v?.id) {

        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_bookmarks_fragment, container, false).also {
            bookMarkAdapter = BookMarkAdapter()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = SearchBookmarksPresenter(
            this, FitnessItemRepositoryImpl.getInstance(
                FitnessCenterRemoteDataSourceImpl.getInstance(
                    RetrofitInstance.getInstance(
                        SearchFragment.URL
                    )
                )
            )
        )
        bookMarkAdapter.setItemClickListener(this)

        presenter.getBookmarksList()

    }


    override fun getData(data: String) {

        val intent = Intent(activity?.application, SearchLookForActivity()::class.java)
        intent.putExtra("data", data)
        intent.putExtra("toggle", true)
        startActivity(intent)
        Toast.makeText(this.context, data, Toast.LENGTH_SHORT).show()
    }


    companion object {
        private const val TAG = "SearchLikeFragment"
    }


}