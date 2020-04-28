package com.work.restaurant.view.mypage.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.work.restaurant.Injection
import com.work.restaurant.R
import com.work.restaurant.util.App
import com.work.restaurant.util.ShowAlertDialog
import com.work.restaurant.view.adapter.RenewBookmarkAndRankListener
import com.work.restaurant.view.base.BaseFragment
import com.work.restaurant.view.mypage.find.MyPageFindPassFragment
import com.work.restaurant.view.mypage.logout.MyPageLogoutFragment
import com.work.restaurant.view.mypage.main.presenter.MyPageContract
import com.work.restaurant.view.mypage.main.presenter.MyPagePresenter
import com.work.restaurant.view.mypage.notification.MyPageNotificationFragment
import com.work.restaurant.view.mypage.question.MyPageQuestionFragment
import com.work.restaurant.view.mypage.register.MyPageRegisterFragment
import com.work.restaurant.view.mypage.withdraw.MyPageWithdrawalFragment
import kotlinx.android.synthetic.main.mypage_fragment.*

class MyPageFragment : BaseFragment(R.layout.mypage_fragment), MyPageContract.View,
    View.OnClickListener {


    private lateinit var presenter: MyPageContract.Presenter

    private lateinit var renewBookmarkAndRankListener: RenewBookmarkAndRankListener


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as? RenewBookmarkAndRankListener)?.let {
            renewBookmarkAndRankListener = it
        }
    }

    override fun onStart() {
        presenter.getLoginState()
        super.onStart()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter =
            MyPagePresenter(
                this,
                Injection.provideLoginRepository(),
                Injection.provideUserRepository()
            )
        iv_login.setOnClickListener(this)
        btn_logout.setOnClickListener(this)
        tv_withdrawal.setOnClickListener(this)
        btn_identity.setOnClickListener(this)
        btn_notification.setOnClickListener(this)
        btn_question.setOnClickListener(this)
        tv_main_register.setOnClickListener(this)
        tv_main_find.setOnClickListener(this)
        btn_login.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_main_find -> {
                requireFragmentManager().beginTransaction().replace(
                    R.id.main_container,
                    MyPageFindPassFragment()
                ).addToBackStack(null).commit()
            }

            R.id.tv_main_register -> {

                val myPageRegisterFragment = MyPageRegisterFragment()

                myPageRegisterFragment.setTargetFragment(
                    this,
                    REGISTER
                )
                requireFragmentManager().beginTransaction().replace(
                    R.id.myPage_main_container,
                    myPageRegisterFragment
                ).addToBackStack(null).commit()
            }

            R.id.btn_login -> {

                presenter.loginCheck(et_email.text.toString(), et_pass.text.toString())

            }


            R.id.btn_logout -> {
                val myPageLogoutFragment =
                    MyPageLogoutFragment.newInstance(tv_login_id.text.toString())

                myPageLogoutFragment.setTargetFragment(
                    this,
                    LOGOUT
                )
                requireFragmentManager()
                    .beginTransaction()
                    .replace(
                        R.id.main_container,
                        myPageLogoutFragment
                    )
                    .addToBackStack(null)
                    .commit()
            }

            R.id.tv_withdrawal -> {
                val myPageWithdrawalFragment =
                    MyPageWithdrawalFragment.newInstance(
                        tv_login_id.text.toString(),
                        userNickname
                    )
                myPageWithdrawalFragment.setTargetFragment(
                    this,
                    WITHDRAW
                )

                requireFragmentManager()
                    .beginTransaction()
                    .replace(
                        R.id.main_container,
                        myPageWithdrawalFragment
                    )
                    .addToBackStack(null)
                    .commit()
            }

            R.id.btn_identity -> {


                ShowAlertDialog(context).apply {
                    alertDialog.setView(
                        LayoutInflater.from(context).inflate(
                            R.layout.identify_item,
                            null
                        )
                    )
                    titleAndMessage(getString(R.string.myPage_identity), null)
                    alertDialog.setPositiveButton(
                        getString(R.string.common_ok)
                    ) { _, _ -> }
                    showDialog()

                }
            }
            R.id.btn_notification -> {
                requireFragmentManager()
                    .beginTransaction()
                    .replace(
                        R.id.myPage_main_container,
                        MyPageNotificationFragment()
                    )
                    .addToBackStack(null)
                    .commit()
            }
            R.id.btn_question -> {
                requireFragmentManager()
                    .beginTransaction()
                    .replace(
                        R.id.myPage_main_container,
                        MyPageQuestionFragment()
                    )
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {

            LOGOUT -> {
                if (resultCode == Activity.RESULT_OK) {
                    et_email.text.clear()
                    et_pass.text.clear()
                    showInit()
                }
            }

            WITHDRAW -> {
                if (resultCode == Activity.RESULT_OK) {
                    clearInputText()
                    showInit()
                }
            }

            REGISTER -> {
                if (resultCode == Activity.RESULT_OK) {
                    val loginEmail =
                        data?.extras?.getString(MyPageRegisterFragment.REGISTER_ID).orEmpty()
                    val loginNickname =
                        data?.extras?.getString(MyPageRegisterFragment.REGISTER_NICKNAME).orEmpty()
                    showLoginOk(loginEmail, loginNickname)
                }
            }

        }
    }

    override fun showInit() {
        loginState(false)
        userNickname = EMPTY_TEXT
        tv_login_nickname.text = EMPTY_TEXT
        tv_login_id.text = EMPTY_TEXT
        App.prefs.login_state_id = EMPTY_TEXT
        App.prefs.login_state = false
        renewBookmarkAndRankListener.renewBookmarkAndRank()
    }

    override fun showProgressState(state: Boolean) {
        pb_login.bringToFront()
        pb_login.isVisible = state
        btn_login.isClickable = !state
        tv_main_register.isClickable = !state
        tv_main_find.isClickable = !state
    }

    override fun showLoginOk(email: String, nickname: String) {
        loginState(true)
        tv_login_nickname.text =
            getString(
                R.string.myPage_login_state_nickname,
                nickname
            )
        tv_login_id.text = email
        userNickname = nickname
        App.prefs.login_state = true
        App.prefs.login_state_id = email
        renewBookmarkAndRankListener.renewBookmarkAndRank()
        showProgressState(false)
    }

    override fun showLoginNo() {
        Toast.makeText(context, getString(R.string.myPage_login_fail), Toast.LENGTH_SHORT)
            .show()
        showProgressState(false)
    }

    override fun showMaintainLogin(email: String, nickname: String) {
        loginState(true)
        tv_login_nickname.text =
            getString(
                R.string.myPage_login_state_nickname,
                nickname
            )
        tv_login_id.text = email
        userNickname = nickname
    }

    override fun showLoginCheck(kind: Int) {

        when (kind) {
            MyPagePresenter.LOGIN_OK -> {
                showProgressState(true)
                presenter.login(
                    et_email.text.toString(),
                    et_pass.text.toString()
                )
            }

            MyPagePresenter.NOT_VALID_EMAIL -> {
                Toast.makeText(
                    context,
                    getString(R.string.myPage_not_valid_email),
                    Toast.LENGTH_SHORT
                )
                    .show()
            }

            MyPagePresenter.HAVE_TRIM -> {
                Toast.makeText(
                    context,
                    getString(R.string.common_have_trim),
                    Toast.LENGTH_SHORT
                )
                    .show()
            }

            MyPagePresenter.NOT_INPUT_PASS -> {
                Toast.makeText(
                    context,
                    getString(R.string.myPage_not_input_pass),
                    Toast.LENGTH_SHORT
                ).show()
            }

            MyPagePresenter.NOT_INPUT_EMAIL -> {
                Toast.makeText(
                    context,
                    getString(R.string.myPage_not_input_email),
                    Toast.LENGTH_SHORT
                ).show()
            }

            MyPagePresenter.NOT_INPUT_ALL -> {
                Toast.makeText(
                    context,
                    getString(R.string.myPage_not_input_all),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun loginState(state: Boolean) {
        ll_myPage_login.isVisible = state
        ll_myPage_init.isInvisible = state
    }

    private fun clearInputText() {
        et_email.text.clear()
        et_pass.text.clear()
    }

    override fun onResume() {
        clearInputText()
        super.onResume()
    }

    override fun onDetach() {
        super.onDetach()
        loginState(false)
    }


    companion object {

        private var userNickname = ""

        private const val TAG = "MyPageFragment"

        private const val REGISTER = 1
        private const val LOGOUT = 2
        private const val WITHDRAW = 3

        private const val EMPTY_TEXT = ""

    }

}