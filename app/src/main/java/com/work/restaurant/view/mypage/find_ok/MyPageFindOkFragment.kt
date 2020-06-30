package com.work.restaurant.view.mypage.find_ok

import android.os.Bundle
import android.view.View
import com.work.restaurant.R
import com.work.restaurant.databinding.MypageFindOkFragmentBinding
import com.work.restaurant.base.BaseFragment

class MyPageFindOkFragment : BaseFragment<MypageFindOkFragmentBinding>(
    MypageFindOkFragmentBinding::bind,
    R.layout.mypage_find_ok_fragment
), View.OnClickListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSendChangePass.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_send_change_pass -> {
                fragmentManager?.popBackStack()
            }
        }
    }

    companion object {
        private const val TAG = "MyPageFindOkFragment"
    }

}
