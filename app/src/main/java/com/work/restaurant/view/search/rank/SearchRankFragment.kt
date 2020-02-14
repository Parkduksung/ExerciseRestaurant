package com.work.restaurant.view.search.rank

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.view.menu.MenuPopupHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.work.restaurant.Injection
import com.work.restaurant.R
import com.work.restaurant.data.model.KakaoSearchModel
import com.work.restaurant.util.App
import com.work.restaurant.view.ExerciseRestaurantActivity.Companion.selectAll
import com.work.restaurant.view.adapter.AdapterDataListener
import com.work.restaurant.view.base.BaseFragment
import com.work.restaurant.view.home.address.HomeAddressActivity
import com.work.restaurant.view.home.address_select_all.HomeAddressSelectAllFragment
import com.work.restaurant.view.search.lookfor.SearchLookForActivity
import com.work.restaurant.view.search.rank.adpater.SearchRankAdapter
import com.work.restaurant.view.search.rank.presenter.SearchRankContract
import com.work.restaurant.view.search.rank.presenter.SearchRankPresenter
import kotlinx.android.synthetic.main.search_rank_fragment.*


class SearchRankFragment : BaseFragment(R.layout.search_rank_fragment), View.OnClickListener,
    SearchRankContract.View,
    AdapterDataListener.GetKakaoData {

    private lateinit var presenter: SearchRankPresenter
    private val searchRankAdapter: SearchRankAdapter by lazy { SearchRankAdapter() }


    override fun showCurrentLocation(addressName: String) {

        val splitAddressName = addressName.split(" ")

        val convertAddressName =
            "${compressCity(splitAddressName[0])} ${splitAddressName[1]} ${splitAddressName[2]}"

        tv_search_locate.text = convertAddressName
        presenter.getCurrentLocation(addressName)
    }


    override fun showBookmarkResult(msg: Boolean) {
        when (msg) {
            RESULT_SUCCESS -> {
                Toast.makeText(this.context, "즐겨찾기에 추가되었습니다.", Toast.LENGTH_LONG).show()
            }
            RESULT_FAILURE -> {
                Toast.makeText(this.context, "즐겨찾기에 추가를 실패하였습니다.", Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun compressCity(city: String): String {
        return if (city.contains("남") || city.contains("북")) {
            "${city[0]}${city[2]}"
        } else {
            city.slice(IntRange(0, 1))
        }
    }


    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.iv_search_settings -> {
                val homeAddressSelectAllFragment = HomeAddressSelectAllFragment()
                homeAddressSelectAllFragment.setTargetFragment(this, REQUEST_CODE)

                val homeAddressActivity = Intent(this.context, HomeAddressActivity::class.java)
                startActivity(homeAddressActivity)
            }

            R.id.iv_search_filter -> {
                val menuBuilder = MenuBuilder(this.context)
                val inflater = MenuInflater(this.context)
                inflater.inflate(R.menu.exercise_list_sort_menu, menuBuilder)

                val optionMenu =
                    MenuPopupHelper(this.context!!, menuBuilder, iv_search_filter)
                optionMenu.setForceShowIcon(true)
                optionMenu.gravity = Gravity.CENTER
                menuBuilder.setCallback(object : MenuBuilder.Callback {
                    override fun onMenuModeChange(menu: MenuBuilder?) {
                    }

                    override fun onMenuItemSelected(
                        menu: MenuBuilder?,
                        item: MenuItem?
                    ): Boolean {
                        when (item?.itemId) {
                            R.id.exercise_list_accuracy_sort -> {

                                presenter.getSortKakaoList(
                                    tv_search_locate.text.toString(),
                                    SORT_ACCURANCY
                                )
                            }
                            R.id.exercise_list_distance_sort -> {
                                presenter.getSortKakaoList(
                                    tv_search_locate.text.toString(),
                                    SORT_DISTANCE
                                )
                            }
                        }
                        return true
                    }
                })

                optionMenu.show()

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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = SearchRankPresenter(
            this,
            Injection.provideKakaoRepository(),
            Injection.provideBookmarkRepository()
        )

        iv_search_settings.setOnClickListener(this)
        iv_search_filter.setOnClickListener(this)
        searchRankAdapter.setItemClickListener(this)

        if (selectAll == "") {

            presenter.getCurrentAddress(
                App.prefs.current_location_long.toDouble(),
                App.prefs.current_location_lat.toDouble()
            )

        }

    }


    override fun onResume() {
        super.onResume()

        if (selectAll.trim() != "") {
            tv_search_locate.text = selectAll
            presenter.getCurrentLocation(tv_search_locate.text.toString())
        }
        Log.d(TAG, "onResume")
    }

    override fun showKakaoList(kakaoList: List<KakaoSearchModel>) {
        recyclerview_rank.run {
            this.adapter = searchRankAdapter
            searchRankAdapter.clearListData()
            searchRankAdapter.addData(kakaoList)
            layoutManager = LinearLayoutManager(this.context)
        }
    }


    override fun getKakaoData(select: Int, data: KakaoSearchModel) {
        when (select) {
            SELECT_URL -> {
                val intent = Intent(activity?.application, SearchLookForActivity()::class.java)
                intent.putExtra(RECYCLERVIEW_CLICK_DATA, data.placeUrl)
                intent.putExtra(RECYCLERVIEW_CLICK_TOGGLE, true)
                startActivity(intent)
            }

            SELECT_BOOKMARK -> {
                val toBookmarkModel = data.toBookmarkModel()
                presenter.addBookmarkKakaoItem(toBookmarkModel)
            }
        }
    }


    companion object {
        private const val TAG = "SearchRankFragment"
        private const val REQUEST_CODE = 1

        private const val SELECT_URL = 1
        private const val SELECT_BOOKMARK = 2

        private const val RESULT_SUCCESS = true
        private const val RESULT_FAILURE = false

        private const val SORT_DISTANCE = "distance"

        private const val SORT_ACCURANCY = "accuracy"

        const val RECYCLERVIEW_CLICK_DATA = "recyclerview_click_data"
        const val RECYCLERVIEW_CLICK_TOGGLE = "recyclerview_click_toggle"

    }


}