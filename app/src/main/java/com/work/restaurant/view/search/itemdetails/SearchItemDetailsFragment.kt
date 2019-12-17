package com.work.restaurant.view.search.itemdetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.work.restaurant.R
import com.work.restaurant.network.model.FitnessCenterItemResponse
import com.work.restaurant.util.GlideApp
import com.work.restaurant.view.search.itemdetails.presenter.SearchItemDetailsContract
import com.work.restaurant.view.search.itemdetails.presenter.SearchItemDetailsPresenter
import kotlinx.android.synthetic.main.search_item_details_fragment.*


class SearchItemDetailsFragment : Fragment(), View.OnClickListener, SearchItemDetailsContract.View {


    private lateinit var detailsPresenter: SearchItemDetailsPresenter
    private lateinit var intent: Intent


    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.review_ll -> {

//                this.requireFragmentManager().beginTransaction().remove(this)
//                    .remove(SearchLookForFragment()).commit()
//                this.requireFragmentManager().beginTransaction().remove(SearchLookFragment()).commit()

                Toast.makeText(context, "click1", Toast.LENGTH_LONG).show()
            }

            R.id.search_item_calling -> {
                detailsPresenter.call(callNumber)
            }

            R.id.search_item_book_mark_check_ib -> {
                search_item_book_mark_check_ib.setImageResource(R.drawable.ic_book_mark_check_ok)
            }

        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_item_details_fragment, container, false).also {
            intent = Intent(Intent.ACTION_DIAL)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "onActivityCreated")
        super.onActivityCreated(savedInstanceState)
        detailsPresenter =
            SearchItemDetailsPresenter(this)
        review_ll.setOnClickListener(this)
        search_item_calling.setOnClickListener(this)
        search_item_book_mark_check_ib.setOnClickListener(this)
    }


    fun setSelectItem(data: String) {
        detailsPresenter =
            SearchItemDetailsPresenter(this)
        detailsPresenter.itemInfoDetail(data)
    }


    override fun showItemInfoDetail(fitnessList: FitnessCenterItemResponse) {
        search_item_name_tv.text = fitnessList.fitnessCenterName
        search_item_time_tv.text = fitnessList.fitnessCenterTime
        search_item_best_part_tv.text = fitnessList.fitnessCenterBestPart
        search_item_like_count_tv.text = fitnessList.fitnessCenterLikeCount.toString()
        search_item_parking_tv.text = fitnessList.fitnessCenterParking
        search_item_pay_tv.text = fitnessList.fitnessCenterPrice
        search_item_parking_tv.text = fitnessList.fitnessCenterParking

        callNumber = fitnessList.fitnessCenterCalling

        GlideApp.with(context!!)
            .load(fitnessList.fitnessCenterImage)
            .into(search_item_image_iv)


    }

    override fun showCall(callNum: String) {
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.data = Uri.parse("tel:$callNumber")
        startActivity(intent)
    }


    companion object {
        private const val TAG = "SearchOkItemFragment"

        private var callNumber = ""
    }


}