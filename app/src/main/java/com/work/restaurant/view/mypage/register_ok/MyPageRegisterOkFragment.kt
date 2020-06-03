package com.work.restaurant.view.mypage.register_ok

import android.os.Bundle
import android.view.View
import com.work.restaurant.R
import com.work.restaurant.databinding.MypageRegisterokFragmentBinding
import com.work.restaurant.view.base.BaseFragment


class MyPageRegisterOkFragment : BaseFragment<MypageRegisterokFragmentBinding>(
    MypageRegisterokFragmentBinding::bind,
    R.layout.mypage_registerok_fragment
),
    View.OnClickListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnRegisterOk.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_register_ok -> {
                fragmentManager?.popBackStack()
            }
        }
    }
    companion object {
        const val TAG = "MyPageRegisterOkFragment"
    }

}
