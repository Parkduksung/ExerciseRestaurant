package com.work.restaurant.view.search.rank

import android.app.Activity
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
import com.work.restaurant.view.home.HomeAddressActivity
import com.work.restaurant.view.search.SearchLookForActivity
import com.work.restaurant.view.search.rank.adpater.FitnessRankAdapter
import com.work.restaurant.view.search.rank.presenter.SearchRankContract
import com.work.restaurant.view.search.rank.presenter.SearchRankPresenter
import kotlinx.android.synthetic.main.search_rank_fragment.*


class SearchRankFragment : Fragment(), View.OnClickListener, SearchRankContract.View,
    AdapterDataListener {

    private lateinit var presenter: SearchRankPresenter
    private lateinit var fitnessRankAdapter: FitnessRankAdapter


    interface ItemClickListener {
        fun clickItemData(data: String)
    }


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
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "onActivityCreated")
        super.onActivityCreated(savedInstanceState)

        presenter = SearchRankPresenter(this)
        iv_search_settings.setOnClickListener(this)
        fitnessRankAdapter.setItemClickListener(this)
        presenter.getFitnessList()

    }


    override fun showSettings() {
        val homeAddressActivity = Intent(this.context, HomeAddressActivity::class.java)
        startActivity(homeAddressActivity)
    }

    override fun showFitnessList(fitnessList: List<FitnessCenterItemResponse>) {
        recyclerview_rank.run {
            this.adapter = fitnessRankAdapter
            fitnessRankAdapter.addData(fitnessList)
            layoutManager = LinearLayoutManager(this.context)
        }
    }

    override fun getData(data: String) {

        val searchLookForActivity = SearchLookForActivity()
        searchLookForActivity.clickItemData(data)

        val intent = Intent(activity?.application, searchLookForActivity::class.java)
        startActivity(intent)
    }


    companion object {
        private const val TAG = "SearchRankFragment"
        private const val REQUEST_CODE = 1
    }


}