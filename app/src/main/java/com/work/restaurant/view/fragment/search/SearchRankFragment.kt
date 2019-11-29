package com.work.restaurant.view.fragment.search

import android.app.Activity
import android.content.Context
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
import com.work.restaurant.data.model.FitnessCenterItem
import com.work.restaurant.ext.FitnessCenterApi
import com.work.restaurant.view.adapter.FitnessRankAdapter
import com.work.restaurant.view.fragment.home.HomeAddressFragment
import kotlinx.android.synthetic.main.search_rank_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SearchRankFragment : Fragment(), View.OnClickListener {

    private lateinit var fitnessRankAdapter: FitnessRankAdapter


    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.iv_search_settings -> {


                val homeAddressFragment = HomeAddressFragment()
                homeAddressFragment.setTargetFragment(this, REQUEST_CODE)
                this.requireFragmentManager().beginTransaction()
                    .replace(R.id.search_main_container, homeAddressFragment)
                    .commit()
            }


        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val address = data?.extras?.getString("address")
                tv_search_locate.text = address
                Toast.makeText(this.context, "$address", Toast.LENGTH_LONG).show()
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
        val view = inflater.inflate(R.layout.search_rank_fragment, container, false)
        return view.also {
            fitnessRankAdapter = FitnessRankAdapter()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "onActivityCreated")
        super.onActivityCreated(savedInstanceState)

        iv_search_settings.setOnClickListener(this)

        load()

    }

    private fun load() {

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
                recyclerview_rank.run {

                    this.adapter = fitnessRankAdapter

                    response?.body()?.let {
                        fitnessRankAdapter.addData(it)
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
        private const val URL = "https://duksung12.cafe24.com"
        private const val REQUEST_CODE = 1
    }


}