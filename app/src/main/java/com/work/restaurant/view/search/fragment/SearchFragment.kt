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

    private lateinit var presenter: SearchPresenter

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.et_search_look -> {
                presenter.search()
            }
        }
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
        val fragmentMap: Map<String, Fragment> = mapOf(
            resources.getStringArray(R.array.tab_search)[0] to SearchRankFragment(),
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
            R.id.loading_container,
            SearchLookFragment()
        ).commit()
    }


    companion object {
        private const val TAG = "SearchFragment"
    }
}