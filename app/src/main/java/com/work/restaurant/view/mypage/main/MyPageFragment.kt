package com.work.restaurant.view.mypage.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.work.restaurant.R
import com.work.restaurant.databinding.MypageFragmentBinding
import com.work.restaurant.ext.showToast
import com.work.restaurant.util.App
import com.work.restaurant.util.ShowAlertDialog
import com.work.restaurant.view.adapter.RenewBookmarkAndRankListener
import com.work.restaurant.base.BaseFragment
import com.work.restaurant.view.mypage.find.MyPageFindPassFragment
import com.work.restaurant.view.mypage.logout.MyPageLogoutFragment
import com.work.restaurant.view.mypage.main.presenter.MyPageContract
import com.work.restaurant.view.mypage.main.presenter.MyPagePresenter
import com.work.restaurant.view.mypage.notification.MyPageNotificationFragment
import com.work.restaurant.view.mypage.question.MyPageQuestionFragment
import com.work.restaurant.view.mypage.register.MyPageRegisterFragment
import com.work.restaurant.view.mypage.withdraw.MyPageWithdrawalFragment
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf

class MyPageFragment :
    BaseFragment<MypageFragmentBinding>(MypageFragmentBinding::bind, R.layout.mypage_fragment),
    MyPageContract.View,
    View.OnClickListener {

    private lateinit var presenter: MyPageContract.Presenter

    private lateinit var renewBookmarkAndRankListener: RenewBookmarkAndRankListener


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as? RenewBookmarkAndRankListener)?.let {
            renewBookmarkAndRankListener = it
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = get { parametersOf(this) }
        presenter.getLoginState()

        binding.ivLogin.setOnClickListener(this)
        binding.btnLogout.setOnClickListener(this)
        binding.tvWithdrawal.setOnClickListener(this)
        binding.btnIdentity.setOnClickListener(this)
        binding.btnNotification.setOnClickListener(this)
        binding.btnQuestion.setOnClickListener(this)
        binding.tvMainRegister.setOnClickListener(this)
        binding.tvMainFind.setOnClickListener(this)
        binding.btnLogin.setOnClickListener(this)
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

                presenter.loginCheck(
                    binding.etEmail.text.toString(),
                    binding.etPass.text.toString()
                )

            }


            R.id.btn_logout -> {

                val myPageLogoutFragment =
                    MyPageLogoutFragment.newInstance(binding.tvLoginId.text.toString())

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
                        binding.tvLoginId.text.toString(),
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
                    binding.etEmail.text.clear()
                    binding.etPass.text.clear()
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
        binding.tvLoginNickname.text = EMPTY_TEXT
        binding.tvLoginId.text = EMPTY_TEXT
        App.prefs.loginStateId = EMPTY_TEXT
        App.prefs.loginState = false
        renewBookmarkAndRankListener.renewBookmarkAndRank()
    }

    override fun showProgressState(state: Boolean) {
        binding.pbLogin.apply {
            bringToFront()
            isVisible = state
        }
        binding.tvMainFind.isClickable = !state
        binding.tvMainRegister.isClickable = !state
        binding.btnLogin.isClickable = !state
    }

    override fun showLoginOk(email: String, nickname: String) {
        loginState(true)
        binding.tvLoginNickname.text =
            getString(
                R.string.myPage_login_state_nickname,
                nickname
            )
        binding.tvLoginId.text = email
        userNickname = nickname
        App.prefs.loginState = true
        App.prefs.loginStateId = email
        renewBookmarkAndRankListener.renewBookmarkAndRank()
        showProgressState(false)
    }

    override fun showLoginNo() {
        showToast(getString(R.string.myPage_login_fail))
        showProgressState(false)
    }

    override fun showMaintainLogin(email: String, nickname: String) {
        loginState(true)
        binding.tvLoginNickname.text =
            getString(
                R.string.myPage_login_state_nickname,
                nickname
            )
        binding.tvLoginId.text = email
        userNickname = nickname
    }

    override fun showLoginCheck(kind: Int) {

        when (kind) {
            MyPagePresenter.LOGIN_OK -> {
                showProgressState(true)
                presenter.login(
                    binding.etEmail.text.toString(),
                    binding.etPass.text.toString()
                )
            }

            MyPagePresenter.NOT_VALID_EMAIL -> {
                showToast(getString(R.string.myPage_not_valid_email))
            }

            MyPagePresenter.HAVE_TRIM -> {
                showToast(getString(R.string.common_have_trim))
            }

            MyPagePresenter.NOT_INPUT_PASS -> {
                showToast(getString(R.string.myPage_not_input_pass))
            }

            MyPagePresenter.NOT_INPUT_EMAIL -> {
                showToast(getString(R.string.myPage_not_input_email))
            }

            MyPagePresenter.NOT_INPUT_ALL -> {
                showToast(getString(R.string.myPage_not_input_all))
            }
        }
    }

    private fun loginState(state: Boolean) {

        binding.llMyPageInit.isInvisible = state

        binding.llMyPageLogin.isVisible = state

    }

    private fun clearInputText() {
        binding.etEmail.text.clear()
        binding.etPass.text.clear()
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