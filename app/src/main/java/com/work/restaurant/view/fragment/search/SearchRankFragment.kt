package com.work.restaurant.view.fragment.search

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.work.restaurant.R
import com.work.restaurant.view.fragment.home.HomeAddressFragment
import com.work.restaurant.view.fragment.home.HomeAddressFragment.Companion.selectAddress1
import com.work.restaurant.view.fragment.home.HomeAddressFragment.Companion.selectAddress2
import com.work.restaurant.view.fragment.home.HomeAddressFragment.Companion.selectAddress3
import com.work.restaurant.view.fragment.home.HomeAddressSelect3Fragment.Companion.toggleInput
import kotlinx.android.synthetic.main.search_rank_fragment.*

class SearchRankFragment : Fragment(), View.OnClickListener {
    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.iv_search_settings -> {

                this.requireFragmentManager().beginTransaction().replace(
                    R.id.loading_container,
                    HomeAddressFragment()
                ).commit()


            }


        }
    }

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
        return inflater.inflate(R.layout.search_rank_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(fragmentName, "onActivityCreated")
        super.onActivityCreated(savedInstanceState)


        iv_search_settings.setOnClickListener(this)

        if (toggleInput) {
            tv_search_locate.text = "$selectAddress1 $selectAddress2 $selectAddress3"
        }

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
        private const val fragmentName = "SearchRankFragment"
    }


}