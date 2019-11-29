package com.work.restaurant.view.fragment.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.work.restaurant.R
import com.work.restaurant.view.adapter.ViewPagerAdapter
import kotlinx.android.synthetic.main.home_address_fragment.*

class HomeAddressFragment : Fragment(), View.OnClickListener {


    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.ib_home_address_back -> {


                val address = "$selectAddress1 $selectAddress2 $selectAddress3"


                if (selectAddress1 == "" || selectAddress2 == "" || selectAddress3 == "") {
                    this.requireFragmentManager().beginTransaction().remove(this).commit()
                } else {
                    Log.d("mmmmmmmmmmmmmmmmmmmm", "$address")
                    val data = Intent()
                    data.putExtra("address", address)
                    targetFragment?.onActivityResult(targetRequestCode, Activity.RESULT_OK, data)
                    this.requireFragmentManager().beginTransaction().remove(this).commit()

                }

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
        return inflater.inflate(R.layout.home_address_fragment, container, false)
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

        adapter.getItem(1).setTargetFragment(this, REQUEST_CODE1)


        vp_address.adapter = adapter
        tl_address.setupWithViewPager(vp_address)

        selectPage(2)

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {

                val address = data?.extras?.getString("address")

                Log.d("vvvvvvvvvvvvvvvvvvvvvvv", "$address")
//                selectPage(address.toInt())
                Toast.makeText(this.context, "$address", Toast.LENGTH_LONG).show()

                if (address != null) {
                    selectPage(address.toInt())
                }
            }
        }

        if (requestCode == REQUEST_CODE1) {
            if (resultCode == Activity.RESULT_OK) {

                val address1 = data?.extras?.getString("address1")

                Log.d("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm", "$address1")
//                selectPage(address.toInt())
                Toast.makeText(this.context, "$address1", Toast.LENGTH_LONG).show()

            }
        }

    }


    private fun selectPage(pageIndex: Int) {
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

        var addressClick = ""
        var selectAddress1 = ""
        var selectAddress2 = ""
        var selectAddress3 = ""
        private const val REQUEST_CODE = 2
        private const val REQUEST_CODE1 = 2
        private const val TAG = "HomeAddressFragment"
    }
}
