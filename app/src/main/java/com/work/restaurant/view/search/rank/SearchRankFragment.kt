package com.work.restaurant.view.search.rank

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.view.menu.MenuPopupHelper
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.work.restaurant.R
import com.work.restaurant.data.model.DisplayBookmarkKakaoModel
import com.work.restaurant.databinding.SearchRankFragmentBinding
import com.work.restaurant.ext.showToast
import com.work.restaurant.util.App
import com.work.restaurant.util.AppExecutors
import com.work.restaurant.util.RelateLogin
import com.work.restaurant.view.adapter.AdapterDataListener
import com.work.restaurant.view.base.BaseFragment
import com.work.restaurant.view.home.address.HomeAddressActivity
import com.work.restaurant.view.home.address_select_all.HomeAddressSelectAllFragment
import com.work.restaurant.view.search.lookfor.SearchLookForActivity
import com.work.restaurant.view.search.rank.adpater.SearchRankAdapter
import com.work.restaurant.view.search.rank.presenter.SearchRankContract
import com.work.restaurant.view.search.rank.presenter.SearchRankPresenter
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf


class SearchRankFragment : BaseFragment<SearchRankFragmentBinding>(
    SearchRankFragmentBinding::bind,
    R.layout.search_rank_fragment
), View.OnClickListener,
    SearchRankContract.View,
    AdapterDataListener.GetDisplayBookmarkKakaoModel {

    private lateinit var presenter: SearchRankContract.Presenter
    private val searchRankAdapter: SearchRankAdapter by lazy { SearchRankAdapter() }

    private lateinit var loginListener: LoginListener

    interface LoginListener {
        fun loginCallbackListener()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as? LoginListener)?.let {
            loginListener = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = get { parametersOf(this) }

        binding.ivSearchSettings.setOnClickListener(this)
        binding.ivSearchFilter.setOnClickListener(this)
        searchRankAdapter.setItemClickListener(this)

        startRankView()
    }

    private fun startRankView() {

        val itemAnimator: DefaultItemAnimator = object : DefaultItemAnimator() {
            override fun canReuseUpdatedViewHolder(viewHolder: RecyclerView.ViewHolder): Boolean {
                return true
            }
        }

        binding.rvRank.run {
            this.itemAnimator = itemAnimator
            this.adapter = searchRankAdapter
            layoutManager = LinearLayoutManager(this.context)

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val lastVisible =
                        (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()

                    val totalItemCount = searchRankAdapter.itemCount

                    if (lastVisible >= totalItemCount - 1) {
                        if (binding.tvSearchLocate.text.toString().isNotEmpty()) {
                            if (!toggleSort) {
                                presenter.getCurrentLocation(
                                    binding.tvSearchLocate.text.toString(),
                                    totalItemCount,
                                    SORT_ACCURACY
                                )
                            } else {
                                presenter.getCurrentLocation(
                                    binding.tvSearchLocate.text.toString(),
                                    totalItemCount,
                                    SORT_DISTANCE
                                )
                            }
                        }
                    }
                }
            })
        }

        if (binding.tvSearchLocate.text.toString().isEmpty()) {
            presenter.getCurrentAddress(
                App.prefs.currentLocationLong.toDouble(),
                App.prefs.currentLocationLat.toDouble()
            )
        }
    }

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.iv_search_settings -> {

                val homeAddressActivity =
                    Intent(context, HomeAddressActivity::class.java)
                startActivityForResult(homeAddressActivity, REQUEST_CODE)
            }

            R.id.iv_search_filter -> {

                context?.let { showSearchFilter(it) }

            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {

            REQUEST_CODE -> {

                val getAddressData =
                    data?.getStringExtra(HomeAddressSelectAllFragment.ADDRESS)

                getAddressData?.let {

                    binding.tvSearchLocate.text = getAddressData

                    initData()

                    toggleSort = false

                    presenter.getCurrentLocation(
                        binding.tvSearchLocate.text.toString(),
                        searchRankAdapter.itemCount,
                        SORT_ACCURACY
                    )
                }
            }


        }


    }

    @SuppressLint("RestrictedApi")
    private fun showSearchFilter(context: Context) {

        val menuBuilder = MenuBuilder(context)
        val inflater = MenuInflater(context)
        inflater.inflate(R.menu.exercise_list_sort_menu, menuBuilder)


        val optionMenu =
            MenuPopupHelper(context, menuBuilder, binding.ivSearchFilter)
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
                            binding.tvSearchLocate.text.toString(),
                            searchRankAdapter.itemCount,
                            SORT_ACCURACY
                        )
                    }
                    R.id.exercise_list_distance_sort -> {
                        initData()
                        toggleSort = true

                        presenter.getCurrentLocation(
                            binding.tvSearchLocate.text.toString(),
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

    override fun showCurrentLocation(addressName: String) {
        binding.tvSearchLocate.text = convertAddressName(addressName)
        presenter.getCurrentLocation(addressName, INIT_COUNT, SORT_ACCURACY)
    }

    private fun convertAddressName(addressName: String): String {
        val splitAddressName =
            addressName.split(" ")
        return "${compressCity(splitAddressName[0])} ${splitAddressName[1]} ${splitAddressName[2]}"
    }

    private fun compressCity(city: String): String {
        return if (city.contains("남") || city.contains("북")) {
            "${city[0]}${city[2]}"
        } else {
            city.slice(IntRange(0, 1))
        }
    }

    override fun showBookmarkResult(msg: Int, selectPosition: Int) {

        when (msg) {

            SearchRankPresenter.ADD_BOOKMARK -> {
                showToast(getString(R.string.bookmark_state_ok))
                searchRankAdapter.stateChange(selectPosition)

            }
            SearchRankPresenter.DELETE_BOOKMARK -> {
                showToast(getString(R.string.bookmark_state_no))
                searchRankAdapter.stateChange(selectPosition)

            }
            SearchRankPresenter.FAIL_ADD -> {
                showToast(getString(R.string.bookmark_add_no_message))
            }

            SearchRankPresenter.FAIL_DELETE -> {
                showToast(getString(R.string.bookmark_delete_no_message))
            }
        }
    }

    override fun showKakaoResult(sort: Int) {

        showLoadingState(false)

        AppExecutors().mainThread.execute {
            when (sort) {

                SearchRankPresenter.LOAD_LOCATION_ERROR -> {
                    showToast(getString(R.string.rank_error_location))
                }
                SearchRankPresenter.LOAD_DATA_ERROR -> {
                    if (RelateLogin.loginState()) {
                        showToast(getString(R.string.rank_error_load_data))
                    }
                }
                SearchRankPresenter.NOT_REMAIN_DATA -> {
                    showToast(getString(R.string.rank_unRemain_result))
                }
            }
        }

    }

    override fun showKakaoList(kakaoList: List<DisplayBookmarkKakaoModel>) {
        showLoadingState(false)
        searchRankAdapter.addAllData(kakaoList)
    }

    override fun getDisplayBookmarkKakaoData(
        select: Int,
        data: DisplayBookmarkKakaoModel,
        selectPosition: Int
    ) {
        when (select) {
            SearchRankAdapter.SELECT_URL -> {
                val intent =
                    Intent(activity?.application, SearchLookForActivity()::class.java).apply {
                        putExtra(CLICK_ITEM_DATA, data.displayUrl)
                        putExtra(CLICK_ITEM_TOGGLE, true)
                    }
                startActivity(intent)
            }
            SearchRankAdapter.ADD_BOOKMARK -> {
                if (RelateLogin.loginState()) {
                    val toBookmarkModel =
                        data.toBookmarkModel(RelateLogin.getLoginId())
                    presenter.addBookmarkKakaoItem(toBookmarkModel, selectPosition)
                }
            }
            SearchRankAdapter.DELETE_BOOKMARK -> {
                if (RelateLogin.loginState()) {
                    val toBookmarkModel =
                        data.toBookmarkModel(RelateLogin.getLoginId())
                    presenter.deleteBookmarkKakaoItem(toBookmarkModel, selectPosition)
                }
            }
            SearchRankAdapter.NOT_LOGIN_STATE -> {
                showToast(getString(R.string.bookmark_state_no_login_message))
            }

        }
    }

    override fun showLoadingState(state: Boolean) {
        binding.pbSearchRank.apply {
            bringToFront()
            isVisible = state
            isIndeterminate = state
        }
    }

    fun renewRank() {
        initData()
        if (!toggleSort) {
            presenter.getCurrentLocation(
                binding.tvSearchLocate.text.toString(),
                searchRankAdapter.itemCount,
                SORT_ACCURACY
            )
        } else {
            presenter.getCurrentLocation(
                binding.tvSearchLocate.text.toString(),
                searchRankAdapter.itemCount,
                SORT_DISTANCE
            )
        }
    }

    private fun initData() {
        presenter.resetData()
        searchRankAdapter.clearListData()
    }

    companion object {
        private const val TAG = "SearchRankFragment"

        const val INIT_COUNT = 15

        private var toggleSort = false

        private const val REQUEST_CODE = 2

        private const val SORT_DISTANCE = "distance"
        private const val SORT_ACCURACY = "accuracy"

        const val CLICK_ITEM_DATA = "click_item_data"
        const val CLICK_ITEM_TOGGLE = "click_item_toggle"


    }


}