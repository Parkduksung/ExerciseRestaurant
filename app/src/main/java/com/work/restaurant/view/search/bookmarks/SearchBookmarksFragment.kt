package com.work.restaurant.view.search.bookmarks

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.work.restaurant.R
import com.work.restaurant.network.api.FitnessCenterApi
import com.work.restaurant.network.model.FitnessCenterItemResponse
import com.work.restaurant.view.adapter.AdapterDataListener
import com.work.restaurant.view.search.bookmarks.adapter.BookMarkAdapter
import com.work.restaurant.view.search.itemdetails.SearchItemDetailsFragment
import com.work.restaurant.view.search.look_for.SearchLookForFragment
import kotlinx.android.synthetic.main.search_bookmarks_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchBookmarksFragment : Fragment(), View.OnClickListener, AdapterDataListener {
    override fun getData(data: String) {
        val searchFragment = SearchLookForFragment()

//        fragment.ttt(data)
        val searchItemFragment =
            SearchItemDetailsFragment()
//        fragment.ttt(data)

        searchItemFragment.setSelectItem(data)

        //add를 해야할지 replace를 해야할지 정답을 알려죠~

        this.requireFragmentManager().beginTransaction()
            .replace(R.id.main_container, searchFragment)
            .add(R.id.search_look_sub_container, searchItemFragment)
            .commit()


//        fragment.requireFragmentManager().beginTransaction()
//            .replace(R.id.loading_container, fragment).commit()

    }


    private lateinit var bookMarkAdapter: BookMarkAdapter


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

        bookMarkAdapter.setItemClickListener(this)

        load()

    }

    private fun load() {

        val retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        val fitnessApi = retrofit.create(FitnessCenterApi::class.java)


        fitnessApi.fitnessCenterAllItem().enqueue(object : Callback<List<FitnessCenterItemResponse>> {

            override fun onFailure(call: Call<List<FitnessCenterItemResponse>>?, t: Throwable?) {
                Log.d("cccccccccccccccccccccccccccc", "$t")
            }

            override fun onResponse(
                call: Call<List<FitnessCenterItemResponse>>?,
                response: Response<List<FitnessCenterItemResponse>>?
            ) {
                recyclerview_bookmark.run {

                    this.adapter = bookMarkAdapter


                    response?.body()?.let {
                        it.forEach { fitnessCenterItem ->
                            if (fitnessCenterItem.fitnessCenterLikeCount >= 90) {
                                bookMarkAdapter.addData(fitnessCenterItem)
                            }
                        }
                    }

                    layoutManager = LinearLayoutManager(this.context)

                }
            }
        })


    }


    companion object {
        private const val TAG = "SearchLikeFragment"
        private const val URL = "https://duksung12.cafe24.com"
    }


}