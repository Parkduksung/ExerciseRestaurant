package com.work.restaurant.view.search.fragment

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.work.restaurant.R
import com.work.restaurant.network.model.FitnessCenterItemModel
import com.work.restaurant.network.api.FitnessCenterApi
import com.work.restaurant.view.adapter.SearchItemAdapter
import com.work.restaurant.view.adapter.SearchItemDataListener
import kotlinx.android.synthetic.main.search_look_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchLookFragment : Fragment(), View.OnClickListener, SearchItemDataListener {


    override fun getSearchItemData(data: String) {
        Toast.makeText(context, data, Toast.LENGTH_LONG).show()
        val searchOkItemFragment = SearchItemFragment()
        searchOkItemFragment.setSelectItem(data)
        this.requireFragmentManager().beginTransaction()
            .replace(R.id.search_look_sub_container, searchOkItemFragment).commit()
    }

    private lateinit var searchItemAdapter: SearchItemAdapter

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.ib_search_look_back -> {
                requireFragmentManager().beginTransaction().remove(this).commit()
            }
            R.id.ib_search_item_look -> {
                load()
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
        return inflater.inflate(R.layout.search_look_fragment, container, false).also {
            searchItemAdapter = SearchItemAdapter()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "onActivityCreated")
        super.onActivityCreated(savedInstanceState)

        ib_search_look_back.setOnClickListener(this)
        ib_search_item_look.setOnClickListener(this)
        searchItemAdapter.setItemClickListener(this)

    }


    private fun load() {


        val alertDialog =
            AlertDialog.Builder(
                ContextThemeWrapper(
                    activity,
                    R.style.Theme_AppCompat_Light_Dialog
                )
            )


        val retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        val fitnessApi = retrofit.create(FitnessCenterApi::class.java)


        fitnessApi.FitnessCenterAllItem().enqueue(object : Callback<List<FitnessCenterItemModel>> {


            override fun onFailure(call: Call<List<FitnessCenterItemModel>>?, t: Throwable?) {
                Log.d("cccccccccccccccccccccccccccc", "$t")
            }

            override fun onResponse(
                call: Call<List<FitnessCenterItemModel>>?,
                response: Response<List<FitnessCenterItemModel>>?
            ) {


                recyclerview_look.run {

                    this.adapter = searchItemAdapter

                    searchItemAdapter.clearListData()

                    response?.body()?.let {

                        if (et_search_item.text.toString().trim() == "") {
                            alertDialog.setTitle("검색 실패")
                            alertDialog.setMessage("다시 입력해 주세요.")
                            alertDialog.setPositiveButton(
                                "확인",
                                object : DialogInterface.OnClickListener {
                                    override fun onClick(dialog: DialogInterface?, which: Int) {
                                        this@SearchLookFragment.requireFragmentManager()
                                    }
                                })
                            alertDialog.show()
                        } else {

                            it.forEach { fitnessCenterItem ->
                                Log.d("ㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎ", fitnessCenterItem.fitnessCenterName)

                                if (fitnessCenterItem.fitnessCenterName.contains(et_search_item.text.toString())) {
                                    Log.d("ㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎ", et_search_item.text.toString())
                                    searchItemAdapter.addData(fitnessCenterItem)
                                    count++
                                }
                            }

                            if (count == 0) {
                                alertDialog.setTitle("검색 실패")
                                alertDialog.setMessage("헬스장이 없습니다. 다시 검색해 주세요.")
                                alertDialog.setPositiveButton(
                                    "확인",
                                    object : DialogInterface.OnClickListener {
                                        override fun onClick(dialog: DialogInterface?, which: Int) {
                                            this@SearchLookFragment.requireFragmentManager()
                                        }
                                    })
                                alertDialog.show()
                            } else {
                                layoutManager = LinearLayoutManager(this.context)
                            }

                        }
                        count = 0
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
        private var count = 0
        private const val URL = "https://duksung12.cafe24.com"
        private const val TAG = "SearchLookFragment"
    }


}