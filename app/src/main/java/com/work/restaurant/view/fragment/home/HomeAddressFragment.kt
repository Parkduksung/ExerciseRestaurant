package com.work.restaurant.view.fragment.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.work.restaurant.R
import com.work.restaurant.view.adapter.ViewPagerAdapter
import kotlinx.android.synthetic.main.home_address_fragment.*

class HomeAddressFragment : Fragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v?.id) {


            R.id.ib_home_address_back -> {

                this.requireFragmentManager().beginTransaction().remove(
                    this
                ).commit()
            }
        }
    }

    override fun onAttach(context: Context) {
        Log.d(TAG, "onAttach")
        super.onAttach(context)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_address_fragment, container, false).also {
            addressClick
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "onActivityCreated")
        super.onActivityCreated(savedInstanceState)



        ib_home_address_back.setOnClickListener(this)
        init()

    }


    private fun init() {
        val fragmentMap: Map<String, Fragment> = mapOf(
            "광역시/도" to HomeAddressSelect1Fragment(),
            "시/군/구" to HomeAddressSelect2Fragment(),
            "읍/면/동" to HomeAddressSelect3Fragment()
        )


        val adapter = ViewPagerAdapter(this.requireFragmentManager(), fragmentMap)

        vp_address.adapter = adapter
        tl_address.setupWithViewPager(vp_address)


//
//        selectPage(2)

    }


    fun selectPage(pageIndex: Int) {
        tl_address.setScrollPosition(pageIndex, 0f, true)
        vp_address.currentItem = pageIndex
    }


    override fun onStart() {
        Log.d(TAG, "onStart")
        super.onStart()
    }

    override fun onResume() {
        Log.d(TAG, "onResume")
        super.onResume()

    }

    override fun onPause() {
        Log.d(TAG, "onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.d(TAG, "onStop")
        super.onStop()
    }

    override fun onDestroyView() {
        Log.d(TAG, "onDestroyView")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")
        super.onDestroy()
    }

    override fun onDetach() {
        Log.d(TAG, "onDetach")
        super.onDetach()
    }

    companion object {

        var ok = false

        var addressClick = ""
        var selectAddress1 = ""
        var selectAddress2 = ""
        var selectAddress3 = ""

        private const val TAG = "HomeAddressFragment"
    }
}
