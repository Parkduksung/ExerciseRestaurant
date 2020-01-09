package com.work.restaurant.view.mypage.find_ok

import android.os.Bundle
import android.util.Log
import android.view.View
import com.work.restaurant.R
import com.work.restaurant.view.base.BaseFragment
import com.work.restaurant.view.mypage.find_ok.presenter.MyPageFindOkContract
import com.work.restaurant.view.mypage.find_ok.presenter.MyPageFindOkPresenter
import kotlinx.android.synthetic.main.mypage_find_ok_fragment.*

class MyPageFindOkFragment : BaseFragment(R.layout.mypage_find_ok_fragment), View.OnClickListener,
    MyPageFindOkContract.View {

    private lateinit var presenter: MyPageFindOkPresenter

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_send_change_pass -> {
                presenter.ok()
            }
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "onActivityCreated")
        super.onActivityCreated(savedInstanceState)

        presenter = MyPageFindOkPresenter(this)
        btn_send_change_pass.setOnClickListener(this)

    }


    override fun showOk() {
        requireFragmentManager().beginTransaction()
            .remove(this)
            .commit()
    }

    companion object {
        private const val TAG = "MyPageFindOkFragment"
    }

}
