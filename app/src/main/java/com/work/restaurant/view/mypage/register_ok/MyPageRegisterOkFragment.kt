package com.work.restaurant.view.mypage.register_ok

import android.os.Bundle
import android.view.View
import com.work.restaurant.R
import com.work.restaurant.view.base.BaseFragment
import kotlinx.android.synthetic.main.mypage_registerok_fragment.*

class MyPageRegisterOkFragment : BaseFragment(R.layout.mypage_registerok_fragment),
    View.OnClickListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_register_ok.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_register_ok -> {
                fragmentManager?.popBackStack()
            }
        }
    }


    companion object {
        private const val TAG = "MyPageRegisterOkFragment"
    }

}
