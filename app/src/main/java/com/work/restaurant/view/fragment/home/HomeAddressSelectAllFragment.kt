package com.work.restaurant.view.fragment.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.work.restaurant.R
import com.work.restaurant.view.fragment.home.HomeAddressFragment.Companion.selectAddress1
import com.work.restaurant.view.fragment.home.HomeAddressFragment.Companion.selectAddress2
import com.work.restaurant.view.fragment.home.HomeAddressFragment.Companion.selectAddress3
import kotlinx.android.synthetic.main.home_address_selcet_all_fragment.*

class HomeAddressSelectAllFragment : Fragment(), View.OnClickListener {
    override fun onClick(v: View?) {
//        val alertDialog =
//            AlertDialog.Builder(ContextThemeWrapper(activity, R.style.Theme_AppCompat_Light_Dialog))


        when (v?.id) {
            R.id.btn_address_change_no -> {
                this.requireFragmentManager().beginTransaction().remove(this).commit()
            }

            R.id.btn_address_change_ok -> {


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
        return inflater.inflate(R.layout.home_address_selcet_all_fragment, container, false).also {

        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "onActivityCreated")
        super.onActivityCreated(savedInstanceState)

//
//        val alertDialog =
//            AlertDialog.Builder(ContextThemeWrapper(activity, R.style.Theme_AppCompat_Light_Dialog))
//
//
//        val retrofit = Retrofit.Builder()
//            .baseUrl(URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//
//        val userApi = retrofit.create(UserApi::class.java)


        btn_address_change_no.setOnClickListener(this)

        btn_address_change_ok.setOnClickListener(this)

        tv_address_select.text = "$selectAddress1 $selectAddress2 $selectAddress3"


//        selectAddress1
//        selectAddress2
//        selectAddress3

//
//
//            this.requireFragmentManager().beginTransaction().remove(
//                this@MyPageWithdrawalFragment
//            ).replace(
//                R.id.mypage_main_container,
//                MyPageFragment()
//            ).commit().also {
//                MyPageFragment.loginState = false
//            }

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
        private const val URL = "https://duksung12.cafe24.com"
        private const val TAG = "MyPageWithdrawalFragment"
    }

}
