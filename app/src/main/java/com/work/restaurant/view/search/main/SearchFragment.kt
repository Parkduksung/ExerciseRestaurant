package com.work.restaurant.view.search.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.work.restaurant.R
import com.work.restaurant.view.adapter.ViewPagerAdapter
import com.work.restaurant.view.base.BaseFragment
import com.work.restaurant.view.search.bookmarks.SearchBookmarksFragment
import com.work.restaurant.view.search.lookfor.SearchLookForActivity
import com.work.restaurant.view.search.main.presenter.SearchContract
import com.work.restaurant.view.search.main.presenter.SearchPresenter
import com.work.restaurant.view.search.rank.SearchRankFragment
import kotlinx.android.synthetic.main.search_fragment.*

class SearchFragment : BaseFragment(R.layout.search_fragment), View.OnClickListener,
    SearchContract.View {

    private lateinit var presenter: SearchPresenter

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.et_search_look -> {
                startSearchLook()
            }
            R.id.ll_search_look -> {
                startSearchLook()
            }
            R.id.iv_search_look -> {
                startSearchLook()
            }
        }
    }


    private fun startSearchLook() {
        val searchLookForActivity = Intent(this.context, SearchLookForActivity::class.java)
        startActivityForResult(searchLookForActivity, RENEW_BOOKMARK_AND_RANK)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RENEW_BOOKMARK_AND_RANK) {
            if (resultCode == Activity.RESULT_OK) {

                val getBoolean = data?.getBooleanExtra(SearchLookForActivity.RENEW, false)

                getBoolean?.let {
                    requireFragmentManager().fragments.forEach {
                        if (it is SearchRankFragment) {
                            it.renewRank()
                        }
                    }
                }

            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = SearchPresenter(this)
        presenter.init()
        et_search_look.setOnClickListener(this)
        ll_search_look.setOnClickListener(this)
        iv_search_look.setOnClickListener(this)
    }


    override fun showInit() {

        val fragmentMap: Map<String, Fragment> = mapOf(
            resources.getStringArray(R.array.tab_search)[0] to SearchRankFragment(),
            resources.getStringArray(R.array.tab_search)[1] to SearchBookmarksFragment()
        )

        val adapter = ViewPagerAdapter(this.requireFragmentManager(), fragmentMap)
        vp_search.adapter = adapter
        tl_search.setupWithViewPager(vp_search)
        tl_search.getTabAt(0)?.setIcon(R.drawable.ic_cooking)
        tl_search.getTabAt(1)?.setIcon(R.drawable.ic_favorite2)
    }

    companion object {
        private const val TAG = "SearchFragment"

        private const val RENEW_BOOKMARK_AND_RANK = 1

    }
}