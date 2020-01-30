package com.work.restaurant.view.search.rank

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.work.restaurant.Injection
import com.work.restaurant.R
import com.work.restaurant.data.repository.fitness.FitnessItemRepositoryImpl
import com.work.restaurant.data.source.remote.fitness.FitnessCenterRemoteDataSourceImpl
import com.work.restaurant.network.RetrofitInstance
import com.work.restaurant.network.model.FitnessCenterItemResponse
import com.work.restaurant.network.model.kakaoSearch.KakaoSearchDocuments
import com.work.restaurant.view.adapter.AdapterDataListener
import com.work.restaurant.view.base.BaseFragment
import com.work.restaurant.view.home.address.HomeAddressActivity
import com.work.restaurant.view.home.address.HomeAddressActivity.Companion.dong
import com.work.restaurant.view.home.address.HomeAddressActivity.Companion.gunGu
import com.work.restaurant.view.home.address.HomeAddressActivity.Companion.si
import com.work.restaurant.view.home.address_select_all.HomeAddressSelectAllFragment
import com.work.restaurant.view.search.lookfor.SearchLookForActivity
import com.work.restaurant.view.search.main.SearchFragment
import com.work.restaurant.view.search.rank.adpater.FitnessRankAdapter
import com.work.restaurant.view.search.rank.adpater.KakaoRankAdapter
import com.work.restaurant.view.search.rank.presenter.SearchRankContract
import com.work.restaurant.view.search.rank.presenter.SearchRankPresenter
import kotlinx.android.synthetic.main.search_rank_fragment.*


class SearchRankFragment : BaseFragment(R.layout.search_rank_fragment), View.OnClickListener,
    SearchRankContract.View,
    AdapterDataListener {

    private lateinit var presenter: SearchRankPresenter
    private lateinit var fitnessRankAdapter: FitnessRankAdapter
    private lateinit var kakaoAdapter: KakaoRankAdapter

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.iv_search_settings -> {
                presenter.settings()
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

        if (requestCode == 12345) {
            if (resultCode == Activity.RESULT_OK) {
                val address = data?.extras?.getString("address")
                tv_search_locate.text = address
                Toast.makeText(this.context, "$address", Toast.LENGTH_LONG).show()
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.search_rank_fragment, container, false)
        return view.also {
            fitnessRankAdapter = FitnessRankAdapter()
            kakaoAdapter = KakaoRankAdapter()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = SearchRankPresenter(
            this, FitnessItemRepositoryImpl.getInstance(
                FitnessCenterRemoteDataSourceImpl.getInstance(
                    RetrofitInstance.getInstance(
                        SearchFragment.URL
                    )
                )
            ),
            Injection.provideKakaoRepository()
        )





        iv_search_settings.setOnClickListener(this)
        fitnessRankAdapter.setItemClickListener(this)
        kakaoAdapter.setItemClickListener(this)
//        presenter.getFitnessList()

        presenter.getKakaoList()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "onAttach")
    }

    override fun onResume() {
        super.onResume()

        tv_search_locate.text = "$si $gunGu $dong"
        Log.d(TAG, "onResume")
    }

    override fun showKakaoList(kakaoList: List<KakaoSearchDocuments>) {
        recyclerview_rank.run {
            this.adapter = kakaoAdapter
            kakaoAdapter.clearListData()
            kakaoAdapter.addData(kakaoList)
            layoutManager = LinearLayoutManager(this.context)
        }
    }

    override fun showSettings() {

        val homeAddressSelectAllFragment = HomeAddressSelectAllFragment()
        homeAddressSelectAllFragment.setTargetFragment(this, REQUEST_CODE)

        val homeAddressActivity = Intent(this.context, HomeAddressActivity::class.java)
        startActivity(homeAddressActivity)
    }

    override fun showFitnessList(fitnessList: List<FitnessCenterItemResponse>) {
        recyclerview_rank.run {
            this.adapter = fitnessRankAdapter
            fitnessRankAdapter.clearListData()
//            fitnessRankAdapter.addData(fitnessList)
            layoutManager = LinearLayoutManager(this.context)
        }
    }

    override fun getData(data: String) {


        val intent = Intent(activity?.application, SearchLookForActivity()::class.java)
        intent.putExtra("data", data)
        intent.putExtra("toggle", true)
        startActivity(intent)
    }


    companion object {
        private const val TAG = "SearchRankFragment"
        private const val REQUEST_CODE = 1
    }


}