package com.work.restaurant.view.search.itemdetails

import android.content.Intent
import android.net.Uri
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
import com.work.restaurant.network.model.kakaoImage.KakaoImageDocuments
import com.work.restaurant.network.model.kakaoSearch.KakaoSearchDocuments
import com.work.restaurant.view.base.BaseFragment
import com.work.restaurant.view.search.itemdetails.adapter.KakaoImageAdapter
import com.work.restaurant.view.search.itemdetails.presenter.SearchItemDetailsContract
import com.work.restaurant.view.search.itemdetails.presenter.SearchItemDetailsPresenter
import com.work.restaurant.view.search.main.SearchFragment
import kotlinx.android.synthetic.main.search_item_details_fragment.*


class SearchItemDetailsFragment : BaseFragment(R.layout.search_item_details_fragment),
    View.OnClickListener, SearchItemDetailsContract.View {
    override fun showKakaoImage(imageList: List<KakaoImageDocuments>) {


        if (imageList.isNotEmpty()) {
            imageList.forEach {
                Log.d("이미지결과", it.imageUrl)
            }

            recyclerview_item_image.run {
                this.adapter = kakaoImageAdapter
                kakaoImageAdapter.clearListData()
                kakaoImageAdapter.addData(imageList)
                layoutManager = LinearLayoutManager(this.context).also {
                    it.orientation = LinearLayoutManager.HORIZONTAL
                }
            }
        }
    }


    override fun showKakaoItemInfoDetail(searchList: KakaoSearchDocuments) {

        search_item_name_tv.text = searchList.placeName
        search_item_time_tv.text = searchList.distance
        search_item_best_part_tv.text = searchList.roadAddressName
        search_item_like_count_tv.text = searchList.distance
        search_item_parking_tv.text = searchList.distance
        search_item_pay_tv.text = searchList.distance
        search_item_parking_tv.text = searchList.distance

        Log.d("카카오결과", searchList.placeUrl)

        if (searchList.phone != "") {
            callNumber = searchList.phone
        }

    }


    private lateinit var detailsPresenter: SearchItemDetailsPresenter
    private lateinit var intent: Intent
    private lateinit var kakaoImageAdapter: KakaoImageAdapter


    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.review_ll -> {

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
            kakaoImageAdapter = KakaoImageAdapter()
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailsPresenter =
            SearchItemDetailsPresenter(
                this, FitnessItemRepositoryImpl.getInstance(
                    FitnessCenterRemoteDataSourceImpl.getInstance(
                        RetrofitInstance.getInstance(
                            SearchFragment.URL
                        )
                    )
                ),
                Injection.provideKakaoRepository()
            )
        review_ll.setOnClickListener(this)
        search_item_calling.setOnClickListener(this)
        search_item_book_mark_check_ib.setOnClickListener(this)
    }

    fun setSelectItem(data: String) {
        detailsPresenter =
            SearchItemDetailsPresenter(
                this, FitnessItemRepositoryImpl.getInstance(
                    FitnessCenterRemoteDataSourceImpl.getInstance(
                        RetrofitInstance.getInstance(
                            SearchFragment.URL
                        )
                    )
                ),
                Injection.provideKakaoRepository()
            )

        detailsPresenter.kakaoItemImage(data)
        detailsPresenter.kakaoItemInfoDetail(data)
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

//        GlideApp.with(context!!)
//            .load(fitnessList.fitnessCenterImage)
//            .into(search_item_image_iv)


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