package com.work.restaurant.view.mypage.register_ok

import android.os.Bundle
import android.view.View
import com.work.restaurant.R
import com.work.restaurant.view.base.BaseFragment
import com.work.restaurant.view.mypage.main.MyPageFragment
import com.work.restaurant.view.mypage.register_ok.presenter.MyPageRegisterOkContract
import com.work.restaurant.view.mypage.register_ok.presenter.MyPageRegisterOkPresenter
import kotlinx.android.synthetic.main.mypage_registerok_fragment.*

class MyPageRegisterOkFragment : BaseFragment(R.layout.mypage_registerok_fragment),
    View.OnClickListener, MyPageRegisterOkContract.View {


    private lateinit var presenter: MyPageRegisterOkPresenter

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_register_ok -> {
                presenter.registerOk()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = MyPageRegisterOkPresenter(this)
        btn_register_ok.setOnClickListener(this)

    }


    override fun showRegisterOk() {
        this@MyPageRegisterOkFragment.requireFragmentManager().beginTransaction()
            .replace(
                R.id.mypage_main_container,
                MyPageFragment()
            ).addToBackStack(null).commit()
    }


    companion object {
        private const val TAG = "MyPageRegisterOkFragment"
    }

}
