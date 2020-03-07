package com.work.restaurant.view.search.rank

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.view.menu.MenuPopupHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.work.restaurant.Injection
import com.work.restaurant.R
import com.work.restaurant.data.model.DisplayBookmarkKakaoModel
import com.work.restaurant.util.App
import com.work.restaurant.util.AppExecutors
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
    AdapterDataListener.GetDisplayBookmarkKakaoModel {

    private lateinit var presenter: SearchRankPresenter
    private val searchRankAdapter: SearchRankAdapter by lazy { SearchRankAdapter() }

    override fun showCurrentLocation(addressName: String) {

        val splitAddressName = addressName.split(" ")
        val convertAddressName =
            "${compressCity(splitAddressName[0])} ${splitAddressName[1]} ${splitAddressName[2]}"
        tv_search_locate.text = convertAddressName
        presenter.getCurrentLocation(addressName, INIT_COUNT, SORT_ACCURANCY)
    }

    override fun showBookmarkResult(msg: Int) {
        when (msg) {
            SELECT_BOOKMARK -> {
                Toast.makeText(this.context, "즐겨찾기에 추가되었습니다.", Toast.LENGTH_LONG).show()
                renewRank()
            }
            DELETE_BOOKMARK -> {
                Toast.makeText(this.context, "즐겨찾기에 제거되었습니다.", Toast.LENGTH_LONG).show()
                renewRank()
            }
            RESULT_FAILURE -> {
                Toast.makeText(this.context, "즐겨찾기에 추가를 실패하였습니다.", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun renewRank() {
        initData()
        if (!toggleSort) {
            presenter.getCurrentLocation(
                tv_search_locate.text.toString(),
                searchRankAdapter.itemCount,
                SORT_ACCURANCY
            )
        } else {
            presenter.getCurrentLocation(
                tv_search_locate.text.toString(),
                searchRankAdapter.itemCount,
                SORT_DISTANCE
            )
        }
    }

    private fun compressCity(city: String): String {
        return if (city.contains("남") || city.contains("북")) {
            "${city[0]}${city[2]}"
        } else {
            city.slice(IntRange(0, 1))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {

                val getAddressData = data?.getStringExtra(HomeAddressSelectAllFragment.ADDRESS)
                getAddressData?.let {

                    tv_search_locate.text = getAddressData

                    initData()
                    toggleSort = false

                    presenter.getCurrentLocation(
                        tv_search_locate.text.toString(),
                        searchRankAdapter.itemCount,
                        SORT_ACCURANCY
                    )
                }
            }
        }
    }


    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.iv_search_settings -> {
                val homeAddressActivity = Intent(this.context, HomeAddressActivity::class.java)
                startActivityForResult(homeAddressActivity, REQUEST_CODE)
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
                                initData()
                                toggleSort = false

                                presenter.getCurrentLocation(
                                    tv_search_locate.text.toString(),
                                    searchRankAdapter.itemCount,
                                    SORT_ACCURANCY
                                )
                            }
                            R.id.exercise_list_distance_sort -> {
                                initData()
                                toggleSort = true

                                presenter.getCurrentLocation(
                                    tv_search_locate.text.toString(),
                                    searchRankAdapter.itemCount,
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

        setup()
    }

    override fun showKakaoList(kakaoList: List<DisplayBookmarkKakaoModel>) {
        AppExecutors().mainThread.execute {
            recyclerview_rank.run {
                searchRankAdapter.addAllData(kakaoList)
            }
        }
    }

    override fun getDisplayBookmarkKakaoData(select: Int, data: DisplayBookmarkKakaoModel) {
        when (select) {
            SELECT_URL -> {
                val intent =
                    Intent(activity?.application, SearchLookForActivity()::class.java).apply {
                        putExtra(RECYCLERVIEW_CLICK_DATA, data.displayUrl)
                        putExtra(RECYCLERVIEW_CLICK_TOGGLE, true)
                    }
                startActivity(intent)
            }
            SELECT_BOOKMARK -> {
                if (App.prefs.login_state_id.isNotEmpty()) {
                    val toBookmarkModel =
                        data.toBookmarkModel(App.prefs.login_state_id)
                    presenter.addBookmarkKakaoItem(toBookmarkModel)
                }
            }

            DELETE_BOOKMARK -> {
                if (App.prefs.login_state_id.isNotEmpty()) {
                    val toBookmarkModel =
                        data.toBookmarkModel(App.prefs.login_state_id)
                    presenter.deleteBookmarkKakaoItem(toBookmarkModel)
                }
            }

            NOT_LOGIN -> {
                Toast.makeText(this.context, "즐겨찾기 기능은 로그인이 필요합니다.", Toast.LENGTH_LONG).show()
            }

        }
    }

    override fun showLoad() {
        if (pb_search_rank != null) {
            pb_search_rank.bringToFront()
            pb_search_rank.visibility = View.VISIBLE
            pb_search_rank.isIndeterminate = true
        }
    }

    override fun showKakaoResult(sort: Int) {

        AppExecutors().mainThread.execute {
            when (sort) {
                0 -> {
                    Toast.makeText(context, "현재 위치를 가져올 수 없습니다.", Toast.LENGTH_SHORT)
                        .show()
                }
                1 -> {
//                    Toast.makeText(context, "데이터를 가져올 수 없습니다.", Toast.LENGTH_SHORT)
//                        .show()
                }
                2 -> {
                    Toast.makeText(context, "더이상 결과가 없습니다.", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        if (pb_search_rank != null) {
            pb_search_rank.visibility = View.GONE
            pb_search_rank.isIndeterminate = false
        }
    }


    private fun setup() {

        recyclerview_rank.run {
            this.adapter = searchRankAdapter
            layoutManager = LinearLayoutManager(this.context)

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val lastVisible =
                        (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()

                    val totalItemCount = searchRankAdapter.itemCount

                    if (lastVisible >= totalItemCount - 1) {
                        if (tv_search_locate.text.toString().isNotEmpty()) {
                            if (!toggleSort) {
                                presenter.getCurrentLocation(
                                    tv_search_locate.text.toString(),
                                    totalItemCount,
                                    SORT_ACCURANCY
                                )
                            } else {
                                presenter.getCurrentLocation(
                                    tv_search_locate.text.toString(),
                                    totalItemCount,
                                    SORT_DISTANCE
                                )
                            }
                        }
                    }
                }
            })
        }

        if (tv_search_locate.text.toString().isEmpty()) {
            presenter.getCurrentAddress(
                App.prefs.current_location_long.toDouble(),
                App.prefs.current_location_lat.toDouble()
            )
        }
    }

    private fun initData() {
        presenter.resetData()
        AppExecutors().mainThread.execute {
            searchRankAdapter.clearListData()
        }
    }


    companion object {
        private const val TAG = "SearchRankFragment"
        private const val REQUEST_CODE = 2

        private const val SELECT_URL = 1
        private const val SELECT_BOOKMARK = 2
        private const val DELETE_BOOKMARK = 3
        private const val NOT_LOGIN = 4


        private const val RESULT_FAILURE = 0

        private const val INIT_COUNT = 15

        private var toggleSort = false
        private const val SORT_DISTANCE = "distance"
        private const val SORT_ACCURANCY = "accuracy"

        const val RECYCLERVIEW_CLICK_DATA = "recyclerview_click_data"
        const val RECYCLERVIEW_CLICK_TOGGLE = "recyclerview_click_toggle"


    }


}