package com.work.restaurant.view.home.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.work.restaurant.R
import com.work.restaurant.data.model.DisplayBookmarkKakaoModel
import com.work.restaurant.databinding.HomeFragmentBinding
import com.work.restaurant.ext.showToast
import com.work.restaurant.util.RelateLogin
import com.work.restaurant.view.adapter.RenewBookmarkAndRankListener
import com.work.restaurant.base.BaseFragment
import com.work.restaurant.view.home.MapInterface
import com.work.restaurant.view.home.address.HomeAddressActivity
import com.work.restaurant.view.home.address_select_all.HomeAddressSelectAllFragment
import com.work.restaurant.view.home.daum_maps.MapFragment
import com.work.restaurant.view.home.main.presenter.HomeContract
import com.work.restaurant.view.home.main.presenter.HomePresenter
import com.work.restaurant.view.search.lookfor.SearchLookForActivity
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf


class HomeFragment :
    BaseFragment<HomeFragmentBinding>(HomeFragmentBinding::bind, R.layout.home_fragment),
    HomeContract.View, View.OnClickListener,
    MapInterface.SelectMarkerListener, MapInterface.CurrentLocationClickListener,
    MapInterface.SearchLocationListener {

    private lateinit var presenter: HomeContract.Presenter

    private lateinit var renewBookmarkAndRankListener: RenewBookmarkAndRankListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as? RenewBookmarkAndRankListener)?.let {
            renewBookmarkAndRankListener = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = get { parametersOf(this) }

        binding.llMarkerDetails.setOnClickListener(this)
        binding.ibMarkerUrl.setOnClickListener(this)
        binding.llMarkerRefresh.setOnClickListener(this)
        binding.ibCurrentLocation.setOnClickListener(this)
        binding.btnAddressSearch.setOnClickListener(this)

        startMaps()

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_address_search -> {
                startHomeAddress()
            }

            R.id.ll_marker_details -> {
                if (getMarkerUrl.isNotEmpty()) {
                    val intent =
                        Intent(activity?.application, SearchLookForActivity()::class.java).apply {
                            putExtra(MARKER_CLICK_DATA, getMarkerUrl)
                            putExtra(MARKER_CLICK_TOGGLE, true)
                        }
                    startActivity(intent)
                } else {
                    showToast(getString(R.string.home_no_access))
                }
            }

            R.id.ll_marker_refresh -> {

                if (toggleClickEffect) {
                    requireFragmentManager().fragments.forEach { parentFragment ->
                        if (parentFragment is HomeFragment) {
                            parentFragment.childFragmentManager.fragments.forEach { childFragment ->
                                if (childFragment is MapFragment) {
                                    childFragment.searchByZoomLevel()
                                }
                            }
                        }
                    }
                } else {
                    showToast(getString(R.string.home_unRemain_result))
                }


            }

            R.id.ib_current_location -> {
                requireFragmentManager().fragments.forEach { parentFragment ->
                    if (parentFragment is HomeFragment) {
                        parentFragment.childFragmentManager.fragments.forEach { childFragment ->
                            if (childFragment is MapFragment) {
                                childFragment.showCurrentLocation()
                            }
                        }
                    }
                }

            }


            R.id.ib_marker_url -> {
                if (getMarkerUrl.isNotEmpty()) {
                    val intent =
                        Intent(context, SearchLookForActivity()::class.java).apply {
                            putExtra(MARKER_CLICK_DATA, getMarkerUrl)
                            putExtra(MARKER_CLICK_TOGGLE, true)
                        }
                    startActivity(intent)

                } else {
                    showToast(getString(R.string.home_no_access))
                }
            }
        }
    }

    private fun startMaps() {
        childFragmentManager.beginTransaction()
            .add(
                R.id.maps_fl,
                MapFragment()
            ).addToBackStack(null)
            .commit()
    }

    private fun startHomeAddress() {
        val homeAddressActivity =
            Intent(context, HomeAddressActivity::class.java)
        startActivityForResult(homeAddressActivity, ADDRESS_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            ADDRESS_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    val getAddressData =
                        data?.getStringExtra(HomeAddressSelectAllFragment.ADDRESS)

                    getAddressData?.let {
                        requireFragmentManager().fragments.forEach { ParentFragment ->
                            if (ParentFragment is HomeFragment) {
                                ParentFragment.childFragmentManager.fragments.forEach { ChildFragment ->
                                    if (ChildFragment is MapFragment) {
                                        MapFragment.toggleSelectLocation = true
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun showBookmarkResult(result: Int) {

        when (result) {
            HomePresenter.ADD_BOOKMARK -> {
                showToast(getString(R.string.bookmark_state_ok))

                renewBookmarkAndRankListener.renewBookmarkAndRank()
            }

            HomePresenter.DELETE_BOOKMARK -> {
                showToast(getString(R.string.bookmark_state_no))
                renewBookmarkAndRankListener.renewBookmarkAndRank()
            }


            HomePresenter.FAIL_ADD -> {
                showToast(getString(R.string.bookmark_add_no_message))
            }

            HomePresenter.FAIL_DELETE -> {
                showToast(getString(R.string.bookmark_delete_no_message))
            }

        }

    }

    override fun findFitnessResult(sort: Int) {

        when (sort) {
            MapFragment.NO_NEW_RESULT -> {
                binding.llMarkerRefresh.visibility = View.GONE
                showToast(getString(R.string.home_no_result))
            }

            MapFragment.REMAIN_RESULT -> {
                binding.tvRefresh.text =
                    getString(R.string.home_remain_result)
                binding.tvRefresh.setTextColor(
                    ContextCompat.getColor(
                        this.requireContext(),
                        R.color.colorMiddleBlue
                    )
                )
                binding.ivRefresh.setColorFilter(
                    ContextCompat.getColor(
                        this.requireContext(),
                        R.color.colorMiddleBlue
                    )
                )
                toggleClickEffect = false

            }
            MapFragment.FINAL_RESULT -> {
                binding.tvRefresh.text =
                    getString(R.string.home_remain_result)

                binding.tvRefresh.setTextColor(
                    ContextCompat.getColor(
                        this.requireContext(),
                        R.color.colorBlue
                    )
                )
                binding.ivRefresh.setColorFilter(
                    ContextCompat.getColor(
                        this.requireContext(),
                        R.color.colorBlue
                    )
                )
            }

            MapFragment.RENEW_SEARCHABLE_STATE -> {
                binding.tvRefresh.text =
                    getString(R.string.home_this_locate_research)
                binding.tvRefresh.setTextColor(
                    ContextCompat.getColor(
                        this.requireContext(),
                        R.color.colorBlue
                    )
                )
                binding.ivRefresh.setColorFilter(
                    ContextCompat.getColor(
                        this.requireContext(),
                        R.color.colorBlue
                    )
                )

                binding.llMarkerRefresh.visibility = View.VISIBLE
                toggleClickEffect = true
            }
        }

    }

    override fun getMarkerData(data: DisplayBookmarkKakaoModel) {


        binding.tvMarkerPlaceAddress.text = data.displayAddress
        binding.tvMarkerPlaceName.text = data.displayName
        getMarkerUrl = data.displayUrl


        binding.cbMarkerBookmark.isChecked = data.toggleBookmark
        binding.cbMarkerBookmark.setOnClickListener {

            if (RelateLogin.loginState()) {
                if (binding.cbMarkerBookmark.isChecked) {
                    val toBookmarkModel =
                        data.toBookmarkModel(RelateLogin.getLoginId())

                    presenter.addBookmark(toBookmarkModel)
                } else {
                    val toBookmarkModel =
                        data.toBookmarkModel(RelateLogin.getLoginId())

                    presenter.deleteBookmark(toBookmarkModel)
                }
            } else {
                showToast(getString(R.string.bookmark_state_no_login_message))
                binding.cbMarkerBookmark.isChecked = false
            }
        }
    }


    override fun clickMap(clickData: Boolean) {

        if (clickData) {

            binding.llMarkerContent.isVisible = true
            binding.llMarkerContent.startAnimation(
                AnimationUtils.loadAnimation(
                    this.context,
                    R.anim.slide_up
                )
            )
        } else {
            if (binding.llMarkerContent.isVisible) {
                binding.llMarkerContent.isVisible = false
                binding.llMarkerContent.startAnimation(
                    AnimationUtils.loadAnimation(
                        this.context,
                        R.anim.slide_down
                    )
                )
            } else {
                binding.llMarkerContent.isVisible = false
                binding.tvMarkerPlaceAddress.text = EMPTY_TEXT
                binding.tvMarkerPlaceName.text = EMPTY_TEXT
            }
        }
    }


    companion object {

        private const val TAG = "HomeFragment"
        private const val EMPTY_TEXT = ""

        private var toggleClickEffect = true
        private var getMarkerUrl = ""

        const val MARKER_CLICK_DATA = "marker_click_data"
        const val MARKER_CLICK_TOGGLE = "marker_click_toggle"
        const val ADDRESS_REQUEST_CODE = 1


    }

}