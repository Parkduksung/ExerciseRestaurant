package com.work.restaurant.view.search.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.work.restaurant.R
import com.work.restaurant.view.adapter.ViewPagerAdapter
import com.work.restaurant.view.search.contract.SearchContract
import com.work.restaurant.view.search.presenter.SearchPresenter
import kotlinx.android.synthetic.main.search_fragment.*

class SearchFragment : Fragment(), View.OnClickListener, SearchContract.View {
    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
    }

    private lateinit var presenter: SearchPresenter

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.et_search_look -> {
                presenter.search()
            }
        }
    }

    fun logged() {

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_fragment, container, false).also {
            presenter = SearchPresenter(this)
        }

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "onActivityCreated")
        super.onActivityCreated(savedInstanceState)

        start()

        et_search_look.setOnClickListener(this)

    }

    private fun start() {
        presenter.init()
    }


    override fun showInit() {
        val searchRankFragment = SearchRankFragment()
        searchRankFragment.setTargetFragment(this, 12343)
        val fragmentMap: Map<String, Fragment> = mapOf(
            resources.getStringArray(R.array.tab_search)[0] to searchRankFragment,
            resources.getStringArray(R.array.tab_search)[1] to SearchLikeFragment()
        )

        val adapter = ViewPagerAdapter(this.requireFragmentManager(), fragmentMap)
        vp_search.adapter = adapter
        tl_search.setupWithViewPager(vp_search)
        tl_search.getTabAt(0)?.setIcon(R.drawable.ic_cooking)
        tl_search.getTabAt(1)?.setIcon(R.drawable.ic_like)
    }

    override fun showSearch() {
        this.requireFragmentManager().beginTransaction().replace(
            R.id.main_container,
            SearchLookFragment()
        ).commit()
    }


    companion object {
        private const val TAG = "SearchFragment"
        const val URL = "https://duksung12.cafe24.com"
    }
}