package com.work.restaurant.view.search.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.work.restaurant.R
import com.work.restaurant.network.model.FitnessCenterItemModel
import com.work.restaurant.view.GlideApp
import com.work.restaurant.view.search.contract.SearchItemContract
import com.work.restaurant.view.search.presenter.SearchItemPresenter
import kotlinx.android.synthetic.main.search_item_fragment.*

class SearchItemFragment : Fragment(), View.OnClickListener, SearchItemContract.View {

    private lateinit var presenter: SearchItemPresenter

    override fun onClick(v: View?) {

        when (v?.id) {

        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_item_fragment, container, false).also {
            presenter = SearchItemPresenter(this)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "onActivityCreated")
        super.onActivityCreated(savedInstanceState)

    }


    fun setSelectItem(data: String) {
        presenter = SearchItemPresenter(this)
        presenter.itemInfoDetail(data)
    }


    override fun showItemInfoDetail(fitnessList: FitnessCenterItemModel) {
        search_item_name_tv.text = fitnessList.fitnessCenterName
        search_item_best_part_tv.text = fitnessList.fitnessCenterBestPart
        search_item_like_count_tv.text = fitnessList.fitnessCenterLikeCount.toString()
        search_item_parking_tv.text = fitnessList.fitnessCenterParking
        search_item_pay_tv.text = fitnessList.fitnessCenterPrice
        search_item_parking_tv.text = fitnessList.fitnessCenterParking

        GlideApp.with(context!!)
            .load(fitnessList.fitnessCenterImage)
            .into(search_item_image_iv)
    }


    companion object {
        private const val TAG = "SearchOkItemFragment"

    }


}