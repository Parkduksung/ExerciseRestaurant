package com.work.restaurant.view.search.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.work.restaurant.R
import com.work.restaurant.databinding.SearchFragmentBinding
import com.work.restaurant.view.adapter.ViewPagerAdapter
import com.work.restaurant.base.BaseFragment
import com.work.restaurant.view.search.bookmarks.SearchBookmarksFragment
import com.work.restaurant.view.search.lookfor.SearchLookForActivity
import com.work.restaurant.view.search.rank.SearchRankFragment


class SearchFragment :
    BaseFragment<SearchFragmentBinding>(SearchFragmentBinding::bind, R.layout.search_fragment),
    View.OnClickListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSearchLook.setOnClickListener(this)
        startView()
    }

    private fun startView() {

        binding.vpSearch.adapter = object : ViewPagerAdapter(
            requireFragmentManager(),
            resources.getStringArray(R.array.tab_search).toList()
        ) {
            override fun getItem(position: Int): Fragment =
                when (position) {
                    0 -> {
                        SearchRankFragment()
                    }
                    1 -> {
                        SearchBookmarksFragment()
                    }
                    else -> throw RuntimeException()
                }
        }

        binding.tlSearch.run {
            setupWithViewPager(binding.vpSearch)
            getTabAt(RANK)?.setIcon(R.drawable.ic_cooking)
            getTabAt(BOOKMARK)?.setIcon(R.drawable.ic_favorite2)
        }
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_search_look -> {
                startSearchLook()
            }
        }
    }


    private fun startSearchLook() {
        val searchLookForActivity =
            Intent(this.context, SearchLookForActivity::class.java)
        startActivityForResult(searchLookForActivity, RENEW_BOOKMARK_AND_RANK)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {

            RENEW_BOOKMARK_AND_RANK -> {

                if (resultCode == Activity.RESULT_OK) {

                    val getBoolean =
                        data?.getBooleanExtra(SearchLookForActivity.RENEW, false)

                    getBoolean?.let {
                        requireFragmentManager().fragments.forEach {
                            when (it) {
                                is SearchRankFragment -> {
                                    it.renewRank()
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    companion object {
        private const val TAG = "SearchFragment"

        private const val RANK = 0
        private const val BOOKMARK = 1

        private const val RENEW_BOOKMARK_AND_RANK = 1

    }
}