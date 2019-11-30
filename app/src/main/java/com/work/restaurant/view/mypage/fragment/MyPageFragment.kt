package com.work.restaurant.view.mypage.fragment

import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.work.restaurant.R
import com.work.restaurant.view.mypage.contract.MyPageContract
import com.work.restaurant.view.mypage.fragment.MyPageLoginFragment.Companion.userNickname
import com.work.restaurant.view.mypage.presenter.MyPagePresenter
import kotlinx.android.synthetic.main.mypage_fragment.*

class MyPageFragment : Fragment(), MyPageContract.View, View.OnClickListener {

    private lateinit var presenter: MyPageContract.Presenter


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.login_ll -> {
                presenter.logIn()
            }

            R.id.tv_page_late_view -> {
                presenter.lateView()
            }

            R.id.tv_page_logout -> {
                presenter.logOut()
            }

            R.id.tv_page_withdrawal -> {
                presenter.withDraw()
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.mypage_fragment, container, false).also {
            presenter = MyPagePresenter(this)
        }

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "onActivityCreated")
        super.onActivityCreated(savedInstanceState)


        if (loginState) {
            iv_login.visibility = View.INVISIBLE
            login_ok_ll.visibility = View.VISIBLE
            tv_login_id.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25F)
//            tv_login_id.text = userId + " 님 환영합니다"
            tv_login_id.text = userNickname + " 님\n 환영합니다."
        } else {
            login_ok_ll.visibility = View.INVISIBLE
        }

        setListener()


    }


    private fun setListener() {
        login_ll.setOnClickListener(this)
        tv_page_logout.setOnClickListener(this)
        tv_page_withdrawal.setOnClickListener(this)
        tv_page_late_view.setOnClickListener(this)
    }


    override fun showLogIn() {
        this.requireFragmentManager().beginTransaction().replace(
            R.id.mypage_main_container,
            MyPageLoginFragment()
        ).commit()
    }

    override fun showLogOut() {
        this.requireFragmentManager().beginTransaction().replace(
            R.id.loading_container,
            MyPageLogoutFragment()
        ).commit()
    }

    override fun showWithDraw() {
        this.requireFragmentManager().beginTransaction().replace(
            R.id.loading_container,
            MyPageWithdrawalFragment()
        ).commit()
    }

    override fun showLateView() {
        this.requireFragmentManager().beginTransaction().replace(
            R.id.loading_container,
            MyPageWithdrawalFragment()
        ).commit()
    }


    companion object {
        var loginState = false
        private const val TAG = "MyPageFragment"
    }

}