package com.work.restaurant.view.search.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.work.restaurant.R
import com.work.restaurant.data.model.FitnessCenterItem
import com.work.restaurant.ext.FitnessCenterApi
import com.work.restaurant.view.GlideApp
import kotlinx.android.synthetic.main.search_item_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchItemFragment : Fragment(), View.OnClickListener {
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
        return inflater.inflate(R.layout.search_item_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "onActivityCreated")
        super.onActivityCreated(savedInstanceState)


    }


    fun setSelectItem(data: String) {


        val retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        val fitnessApi = retrofit.create(FitnessCenterApi::class.java)


        fitnessApi.FitnessCenterAllItem().enqueue(object : Callback<List<FitnessCenterItem>> {

            override fun onFailure(call: Call<List<FitnessCenterItem>>?, t: Throwable?) {
                Log.d("cccccccccccccccccccccccccccc", "$t")
            }

            override fun onResponse(
                call: Call<List<FitnessCenterItem>>?,
                response: Response<List<FitnessCenterItem>>?
            ) {


                response?.body()?.let {

                    it.forEach {

                        if (it.fitnessCenterName == data) {
                            search_item_name_tv.text = it.fitnessCenterName
                            search_item_best_part_tv.text = it.fitnessCenterBestPart
                            search_item_like_count_tv.text = it.fitnessCenterLikeCount.toString()
                            search_item_parking_tv.text = it.fitnessCenterParking
                            search_item_pay_tv.text = it.fitnessCenterPrice
                            search_item_parking_tv.text = it.fitnessCenterParking


                            GlideApp.with(context!!)
                                .load(it.fitnessCenterImage)
                                .into(search_item_image_iv)

                        }
                    }

                }


            }
        })


    }


    override fun onStart() {
        Log.d(TAG, "onStart")
        super.onStart()
    }

    override fun onResume() {
        Log.d(TAG, "onResume")
        super.onResume()
    }

    override fun onPause() {
        Log.d(TAG, "onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.d(TAG, "onStop")
        super.onStop()
    }

    override fun onDestroyView() {
        Log.d(TAG, "onDestroyView")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")
        super.onDestroy()
    }

    override fun onDetach() {
        Log.d(TAG, "onDetach")
        super.onDetach()
    }

    companion object {
        private const val TAG = "SearchOkItemFragment"
        private const val URL = "https://duksung12.cafe24.com"
    }


}