package com.work.restaurant.view.home.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.work.restaurant.Injection
import com.work.restaurant.R
import com.work.restaurant.data.model.DisplayBookmarkKakaoModel
import com.work.restaurant.util.App
import com.work.restaurant.util.RelateLogin
import com.work.restaurant.view.adapter.RenewBookmarkAndRankListener
import com.work.restaurant.view.base.BaseFragment
import com.work.restaurant.view.home.MapInterface
import com.work.restaurant.view.home.address.HomeAddressActivity
import com.work.restaurant.view.home.address_select_all.HomeAddressSelectAllFragment
import com.work.restaurant.view.home.daum_maps.MapFragment
import com.work.restaurant.view.home.main.presenter.HomeContract
import com.work.restaurant.view.home.main.presenter.HomePresenter
import com.work.restaurant.view.search.lookfor.SearchLookForActivity
import kotlinx.android.synthetic.main.home_fragment.*


class HomeFragment : BaseFragment(R.layout.home_fragment),
    HomeContract.View, View.OnClickListener,
    MapInterface.SelectMarkerListener, MapInterface.CurrentLocationClickListener,
    MapInterface.SearchLocationListener {

    private lateinit var presenter: HomePresenter

    private lateinit var renewBookmarkAndRankListener: RenewBookmarkAndRankListener


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as? RenewBookmarkAndRankListener)?.let {
            renewBookmarkAndRankListener = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter =
            HomePresenter(this, Injection.provideBookmarkRepository())

        ll_marker_details.setOnClickListener(this)
        ib_marker_url.setOnClickListener(this)
        ll_marker_refresh.setOnClickListener(this)
        ib_current_location.setOnClickListener(this)
        btn_address_search.setOnClickListener(this)

        startMaps()

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
            Intent(this.context, HomeAddressActivity::class.java)
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
                    Toast.makeText(
                        context,
                        getString(R.string.home_no_access),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            R.id.ll_marker_refresh -> {

                if (toggleClickEffect) {
                    requireFragmentManager().fragments.forEach { ParentFragment ->
                        if (ParentFragment is HomeFragment) {
                            ParentFragment.childFragmentManager.fragments.forEach { ChildFragment ->
                                if (ChildFragment is MapFragment) {
                                    ChildFragment.searchByZoomLevel()
                                }
                            }
                        }
                    }
                } else {
                    Toast.makeText(
                        App.instance.context(),
                        getString(R.string.home_unRemain_result),
                        Toast.LENGTH_SHORT
                    )
                        .show()

                }


            }

            R.id.ib_current_location -> {
                requireFragmentManager().fragments.forEach { ParentFragment ->
                    if (ParentFragment is HomeFragment) {
                        ParentFragment.childFragmentManager.fragments.forEach { ChildFragment ->
                            if (ChildFragment is MapFragment) {
                                ChildFragment.showCurrentLocation()
                            }
                        }
                    }
                }

            }


            R.id.ib_marker_url -> {
                if (getMarkerUrl.isNotEmpty()) {
                    val intent =
                        Intent(activity?.application, SearchLookForActivity()::class.java).apply {
                            putExtra(MARKER_CLICK_DATA, getMarkerUrl)
                            putExtra(MARKER_CLICK_TOGGLE, true)
                        }
                    startActivity(intent)

                } else {
                    Toast.makeText(
                        context,
                        getString(R.string.home_no_access),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun showBookmarkResult(result: Int) {

        when (result) {
            HomePresenter.ADD_BOOKMARK -> {
                Toast.makeText(
                    context,
                    getString(R.string.bookmark_state_ok),
                    Toast.LENGTH_SHORT
                ).show()

                renewBookmarkAndRankListener.renewBookmarkAndRank()
            }

            HomePresenter.DELETE_BOOKMARK -> {
                Toast.makeText(
                    context,
                    getString(R.string.bookmark_state_no),
                    Toast.LENGTH_SHORT
                ).show()
                renewBookmarkAndRankListener.renewBookmarkAndRank()
            }


            HomePresenter.FAIL_ADD -> {
                Toast.makeText(
                    context,
                    getString(R.string.bookmark_add_no_message),
                    Toast.LENGTH_SHORT
                ).show()
            }

            HomePresenter.FAIL_DELETE -> {
                Toast.makeText(
                    context,
                    getString(R.string.bookmark_delete_no_message),
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

    }

    override fun findFitnessResult(sort: Int) {

        when (sort) {

            MapFragment.NO_NEW_RESULT -> {
                ll_marker_refresh.visibility = View.GONE
                Toast.makeText(
                    context,
                    getString(R.string.home_no_result),
                    Toast.LENGTH_SHORT
                ).show()
            }

            MapFragment.REMAIN_RESULT -> {
                tv_refresh.text =
                    getString(R.string.home_remain_result)
                tv_refresh.setTextColor(
                    ContextCompat.getColor(
                        this.requireContext(),
                        R.color.colorMiddleBlue
                    )
                )
                iv_refresh.setColorFilter(
                    ContextCompat.getColor(
                        this.requireContext(),
                        R.color.colorMiddleBlue
                    )
                )
                toggleClickEffect = false

            }
            MapFragment.FINAL_RESULT -> {
                tv_refresh.text =
                    getString(R.string.home_remain_result)

                tv_refresh.setTextColor(
                    ContextCompat.getColor(
                        this.requireContext(),
                        R.color.colorBlue
                    )
                )
                iv_refresh.setColorFilter(
                    ContextCompat.getColor(
                        this.requireContext(),
                        R.color.colorBlue
                    )
                )
            }

            MapFragment.RENEW_SEARCHABLE_STATE -> {
                tv_refresh.text =
                    getString(R.string.home_this_locate_research)
                tv_refresh.setTextColor(
                    ContextCompat.getColor(
                        this.requireContext(),
                        R.color.colorBlue
                    )
                )
                iv_refresh.setColorFilter(
                    ContextCompat.getColor(
                        this.requireContext(),
                        R.color.colorBlue
                    )
                )

                ll_marker_refresh.visibility = View.VISIBLE
                toggleClickEffect = true
            }
        }

    }

    override fun getMarkerData(data: DisplayBookmarkKakaoModel) {
        tv_marker_place_address.text = data.displayAddress
        tv_marker_place_name.text = data.displayName
        getMarkerUrl = data.displayUrl

        cb_marker_bookmark.isChecked = data.toggleBookmark
        cb_marker_bookmark.setOnClickListener {

            if (RelateLogin.loginState()) {
                if (cb_marker_bookmark.isChecked) {
                    val toBookmarkModel =
                        data.toBookmarkModel(App.prefs.login_state_id)

                    presenter.addBookmark(toBookmarkModel)
                } else {
                    val toBookmarkModel =
                        data.toBookmarkModel(App.prefs.login_state_id)

                    presenter.deleteBookmark(toBookmarkModel)
                }
            } else {
                Toast.makeText(
                   context,
                    getString(R.string.bookmark_state_no_login_message),
                    Toast.LENGTH_SHORT
                ).show()

                cb_marker_bookmark.isChecked = false
            }
        }
    }

    override fun clickMap(clickData: Boolean) {

        if (clickData) {
            ll_marker_content.isVisible = true
            ll_marker_content.startAnimation(
                AnimationUtils.loadAnimation(
                    this.context,
                    R.anim.slide_up
                )
            )
        } else {
            if (ll_marker_content.isVisible) {
                ll_marker_content.isVisible = false
                ll_marker_content.startAnimation(
                    AnimationUtils.loadAnimation(
                        this.context,
                        R.anim.slide_down
                    )
                )
            } else {
                ll_marker_content.isVisible = false
                tv_marker_place_address.text = EMPTY_TEXT
                tv_marker_place_name.text = EMPTY_TEXT
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