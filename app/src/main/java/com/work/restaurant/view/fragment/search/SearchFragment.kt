package com.work.restaurant.view.fragment.search

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.work.restaurant.R
import com.work.restaurant.view.adapter.ViewPagerAdapter
import kotlinx.android.synthetic.main.search_fragment.*

class SearchFragment : Fragment() {


    private lateinit var searchlook: ImageButton
    override fun onAttach(context: Context) {
        Log.d(fragmentName, "onAttach")
        super.onAttach(context)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(fragmentName, "onCreate")
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_fragment, container, false)

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(fragmentName, "onActivityCreated")
        super.onActivityCreated(savedInstanceState)

        init()

        searchlook = ib_search_look

        searchlook.setOnClickListener {

            this.requireFragmentManager().beginTransaction().replace(
                R.id.search_main_container,
                SearchLookFragment()
            ).commit()


        }


    }

    private fun init() {
        val fragmentMap: Map<String, Fragment> = mapOf(
            "운동맛집 랭킹" to SearchRankFragment(),
            "관심맛집" to SearchlikeFragment()

        )

        val adapter = ViewPagerAdapter(this.requireFragmentManager(), fragmentMap)
        vp_search.adapter = adapter
        tl_search.setupWithViewPager(vp_search)
        tl_search.getTabAt(0)?.setIcon(R.drawable.ic_cooking)
        tl_search.getTabAt(1)?.setIcon(R.drawable.ic_like)


//        main_taps.getTabAt(1)?.apply {
//            icon?.setColorFilter(Color.parseColor("#a8a8a8"), PorterDuff.Mode.SRC_IN);
//        }

    }


    override fun onStart() {
        Log.d(fragmentName, "onStart")
        super.onStart()
    }

    override fun onResume() {
        Log.d(fragmentName, "onResume")
        super.onResume()
    }

    override fun onPause() {
        Log.d(fragmentName, "onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.d(fragmentName, "onStop")
        super.onStop()
    }

    override fun onDestroyView() {
        Log.d(fragmentName, "onDestroyView")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d(fragmentName, "onDestroy")
        super.onDestroy()
    }

    override fun onDetach() {
        Log.d(fragmentName, "onDetach")
        super.onDetach()
    }

    companion object {
        private const val fragmentName = "SearchFragment"
    }
}