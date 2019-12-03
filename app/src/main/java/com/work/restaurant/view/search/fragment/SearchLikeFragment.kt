package com.work.restaurant.view.search.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.work.restaurant.R
import com.work.restaurant.network.api.FitnessCenterApi
import com.work.restaurant.network.model.FitnessCenterItemModel
import com.work.restaurant.view.adapter.BookMarkAdapter
import kotlinx.android.synthetic.main.search_like_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchLikeFragment : Fragment(), View.OnClickListener {


    private lateinit var bookMarkAdapter: BookMarkAdapter


    override fun onClick(v: View?) {
        when (v?.id) {

        }
    }


    override fun onAttach(context: Context) {
        Log.d(TAG, "onAttach")
        super.onAttach(context)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_like_fragment, container, false).also {
            bookMarkAdapter = BookMarkAdapter()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "onActivityCreated")
        super.onActivityCreated(savedInstanceState)

        load()

    }

    private fun load() {

        val retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        val fitnessApi = retrofit.create(FitnessCenterApi::class.java)


        fitnessApi.fitnessCenterAllItem().enqueue(object : Callback<List<FitnessCenterItemModel>> {

            override fun onFailure(call: Call<List<FitnessCenterItemModel>>?, t: Throwable?) {
                Log.d("cccccccccccccccccccccccccccc", "$t")
            }

            override fun onResponse(
                call: Call<List<FitnessCenterItemModel>>?,
                response: Response<List<FitnessCenterItemModel>>?
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