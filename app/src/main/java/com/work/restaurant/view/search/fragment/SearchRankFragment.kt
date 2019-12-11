package com.work.restaurant.view.search.fragment

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
import com.work.restaurant.view.adapter.FitnessRankAdapter
import com.work.restaurant.view.home.fragment.HomeAddressFragment
import com.work.restaurant.view.search.contract.SearchRankContract
import com.work.restaurant.view.search.presenter.SearchRankPresenter
import kotlinx.android.synthetic.main.search_rank_fragment.*


class SearchRankFragment : Fragment(), View.OnClickListener, SearchRankContract.View,
    AdapterDataListener {
    override fun getData(data: String) {


        val searchLookFragment = SearchLookFragment()
        val searchItemFragment = SearchItemFragment()
        searchItemFragment.setSelectItem(data)


        this.requireFragmentManager().beginTransaction()
            .replace(
                R.id.main_container,
                searchLookFragment
            )
            .replace(
                R.id.search_look_sub_container,
                searchItemFragment
            )
            .commit()

    }


    private lateinit var presenter: SearchRankPresenter
    private lateinit var fitnessRankAdapter: FitnessRankAdapter


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
            presenter = SearchRankPresenter(this)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "onActivityCreated")
        super.onActivityCreated(savedInstanceState)

        iv_search_settings.setOnClickListener(this)
        fitnessRankAdapter.setItemClickListener(this)
        presenter.getFitnessList()

    }

    override fun showSettings() {
        val homeAddressFragment =
            HomeAddressFragment()
        homeAddressFragment.setTargetFragment(
            targetFragment,
            REQUEST_CODE
        )
        this.requireFragmentManager().beginTransaction()
            .replace(R.id.search_main_container, homeAddressFragment)
            .commit()
    }

    override fun showFitnessList(fitnessList: List<FitnessCenterItemResponse>) {
        recyclerview_rank.run {
            this.adapter = fitnessRankAdapter
            fitnessRankAdapter.addData(fitnessList)
            layoutManager = LinearLayoutManager(this.context)
        }
    }

    companion object {
        private const val TAG = "SearchRankFragment"
        private const val REQUEST_CODE = 1
    }


}