package com.work.restaurant.view.fragment.search

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.work.restaurant.R
import com.work.restaurant.data.model.ExerciseRank
import com.work.restaurant.ext.ExerciseApi
import com.work.restaurant.view.adapter.ExerciseRankAdapter
import com.work.restaurant.view.fragment.home.HomeAddressFragment
import com.work.restaurant.view.fragment.home.HomeAddressFragment.Companion.selectAddress1
import com.work.restaurant.view.fragment.home.HomeAddressFragment.Companion.selectAddress2
import com.work.restaurant.view.fragment.home.HomeAddressFragment.Companion.selectAddress3
import com.work.restaurant.view.fragment.home.HomeAddressSelect3Fragment.Companion.toggleInput
import kotlinx.android.synthetic.main.search_rank_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchRankFragment : Fragment(), View.OnClickListener {

    private lateinit var exerciseRankAdapter: ExerciseRankAdapter

    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.iv_search_settings -> {

                this.requireFragmentManager().beginTransaction().replace(
                    R.id.loading_container,
                    HomeAddressFragment()
                ).commit()


            }


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
        return inflater.inflate(R.layout.search_rank_fragment, container, false).also {
            exerciseRankAdapter = ExerciseRankAdapter()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "onActivityCreated")
        super.onActivityCreated(savedInstanceState)


        iv_search_settings.setOnClickListener(this)


        if (toggleInput) {
            tv_search_locate.text = "$selectAddress1 $selectAddress2 $selectAddress3"
        }

        load()


    }


    private fun load() {

        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        val exerciseApi = retrofit.create(ExerciseApi::class.java)


        exerciseApi.exerciseAllItem().enqueue(object : Callback<List<ExerciseRank>> {
            override fun onFailure(call: Call<List<ExerciseRank>>?, t: Throwable?) {
                Log.d("cccccccccccccccccccccccccccc", "$t")
            }

            override fun onResponse(
                call: Call<List<ExerciseRank>>?,
                response: Response<List<ExerciseRank>>?
            ) {
                recyclerview_rank.run {

                    this.adapter = exerciseRankAdapter

                    val result = response!!.body()

                    Log.d("cccccccccccccccccccccccccccc", "$result")

                    response!!.body().let {
                        exerciseRankAdapter.addData(it)
                    }

                    layoutManager = LinearLayoutManager(this.context)

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
        private const val TAG = "SearchRankFragment"
        private const val url = "https://duksung12.cafe24.com"
    }


}