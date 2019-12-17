package com.work.restaurant.view.search.bookmarks

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.work.restaurant.R
import com.work.restaurant.network.model.FitnessCenterItemResponse
import com.work.restaurant.view.adapter.AdapterDataListener
import com.work.restaurant.view.search.SearchLookForActivity
import com.work.restaurant.view.search.bookmarks.adapter.BookMarkAdapter
import com.work.restaurant.view.search.bookmarks.presenter.SearchBookmarksContract
import com.work.restaurant.view.search.bookmarks.presenter.SearchBookmarksPresenter
import kotlinx.android.synthetic.main.search_bookmarks_fragment.*

class SearchBookmarksFragment : Fragment(), View.OnClickListener, AdapterDataListener,
    SearchBookmarksContract.View {


    interface ItemClickListener {
        fun clickItemData(data: String)
    }


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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "onActivityCreated")
        super.onActivityCreated(savedInstanceState)

        presenter = SearchBookmarksPresenter(this)
        bookMarkAdapter.setItemClickListener(this)

        presenter.getBookmarksList()
//        load()

    }
//
//    private fun load() {
//
//        val retrofit = Retrofit.Builder()
//            .baseUrl(URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//
//        val fitnessApi = retrofit.create(FitnessCenterApi::class.java)
//
//
//        fitnessApi.fitnessCenterAllItem()
//            .enqueue(object : Callback<List<FitnessCenterItemResponse>> {
//
//                override fun onFailure(
//                    call: Call<List<FitnessCenterItemResponse>>?,
//                    t: Throwable?
//                ) {
//                    Log.d("cccccccccccccccccccccccccccc", "$t")
//                }
//
//                override fun onResponse(
//                    call: Call<List<FitnessCenterItemResponse>>?,
//                    response: Response<List<FitnessCenterItemResponse>>?
//                ) {
//                    recyclerview_bookmark.run {
//
//                        this.adapter = bookMarkAdapter
//
//
//                        response?.body()?.let {
//                            it.forEach { fitnessCenterItem ->
//                                if (fitnessCenterItem.fitnessCenterLikeCount >= 90) {
//                                    bookMarkAdapter.addData(fitnessCenterItem)
//                                }
//                            }
//                        }
//
//                        layoutManager = LinearLayoutManager(this.context)
//
//                    }
//                }
//            })
//
//
//    }

    override fun getData(data: String) {

        val searchLookForActivity = SearchLookForActivity()
        searchLookForActivity.clickItemData(data)

        val intent = Intent(activity?.application, searchLookForActivity::class.java)
        startActivity(intent)
        Toast.makeText(this.context, data, Toast.LENGTH_SHORT).show()
    }


    companion object {
        private const val TAG = "SearchLikeFragment"
        private const val URL = "https://duksung12.cafe24.com"
    }


}