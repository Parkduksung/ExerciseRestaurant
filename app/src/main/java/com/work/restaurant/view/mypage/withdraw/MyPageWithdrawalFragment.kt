package com.work.restaurant.view.mypage.withdraw

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.work.restaurant.Injection
import com.work.restaurant.R
import com.work.restaurant.util.App
import com.work.restaurant.view.base.BaseFragment
import com.work.restaurant.view.mypage.withdraw.presenter.MyPageWithdrawalContract
import com.work.restaurant.view.mypage.withdraw.presenter.MyPageWithdrawalPresenter
import kotlinx.android.synthetic.main.mypage_withdrawal_fragment.*

class MyPageWithdrawalFragment : BaseFragment(R.layout.mypage_withdrawal_fragment),
    View.OnClickListener, MyPageWithdrawalContract.View {

    private lateinit var presenter: MyPageWithdrawalContract.Presenter

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.btn_withdraw_cancel -> {
                presenter.withdrawCancel()
            }

            R.id.btn_withdraw_ok -> {
                pb_withdrawal.bringToFront()
                pb_withdrawal.visibility = View.VISIBLE
                btn_withdraw_cancel.isClickable = false
                btn_withdraw_ok.isClickable = false


                val bundle = arguments
                val getUserId = bundle?.getString(WITHDRAW_ID).orEmpty()
                val getUserNickname = bundle?.getString(WITHDRAW_NICKNAME).orEmpty()


                if (getUserId.isNotEmpty() && getUserNickname.isNotEmpty()) {
                    presenter.run {
                        withdraw(getUserNickname, getUserId)
                        withdrawLogin(getUserNickname, getUserId)
                    }
                } else {
                    pb_withdrawal.visibility = View.GONE
                    btn_withdraw_cancel.isClickable = true
                    btn_withdraw_ok.isClickable = true
                    Toast.makeText(App.instance.context(), "회원탈퇴를 실패하였습니다.", Toast.LENGTH_SHORT)
                        .show()

                }

            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return super.onCreateView(inflater, container, savedInstanceState).also {
            it?.setBackgroundColor(
                ContextCompat.getColor(
                    App.instance.context(),
                    R.color.transparent
                )
            )
            it?.setOnTouchListener { _, _ ->
                true
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = MyPageWithdrawalPresenter(
            this,
            Injection.provideUserRepository(),
            Injection.provideLoginRepository()
        )
        btn_withdraw_cancel.setOnClickListener(this)
        btn_withdraw_ok.setOnClickListener(this)


    }


    override fun showWithdrawCancel() {
        fragmentManager?.popBackStack()
    }

    private fun withdrawOk(userNickname: String) {

        if (toggleWithdraw && toggleWithdrawLogin) {
            if (pb_withdrawal != null) {


                pb_withdrawal.visibility = View.GONE
                btn_withdraw_cancel.isClickable = true
                btn_withdraw_ok.isClickable = true


                targetFragment?.onActivityResult(
                    targetRequestCode,
                    Activity.RESULT_OK,
                    null
                )


                val alertDialog =
                    AlertDialog.Builder(
                        ContextThemeWrapper(
                            activity,
                            R.style.Theme_AppCompat_Light_Dialog
                        )
                    )

                alertDialog.setTitle("성공")
                alertDialog.setMessage(userNickname + "님이 정상적으로 탈퇴 되었습니다.")
                alertDialog.setPositiveButton(
                    "확인",
                    object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                        }
                    })
                alertDialog.show()
            }

            toggleWithdraw = false
            toggleWithdrawLogin = false

            fragmentManager?.popBackStack()

        }

    }

    override fun showWithdrawLoginOk(userNickname: String) {
        toggleWithdrawLogin = true
        withdrawOk(userNickname)
    }

    override fun showWithdrawOk(userNickname: String) {
        toggleWithdraw = true
        withdrawOk(userNickname)
    }

    override fun showWithdrawNo(sort: Int) {
        if (sort == 0) {
            if (pb_withdrawal != null) {
                pb_withdrawal.visibility = View.GONE
                btn_withdraw_cancel.isClickable = true
                btn_withdraw_ok.isClickable = true

                val alertDialog =
                    AlertDialog.Builder(
                        ContextThemeWrapper(
                            activity,
                            R.style.Theme_AppCompat_Light_Dialog
                        )
                    )

                alertDialog.setTitle("실패")
                alertDialog.setMessage("회원 탈퇴를 실패하였습니다.")
                alertDialog.setPositiveButton("확인",
                    object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {

                        }
                    })
                alertDialog.show()

            }
        } else {
            if (pb_withdrawal != null) {
                pb_withdrawal.visibility = View.GONE
                btn_withdraw_cancel.isClickable = true
                btn_withdraw_ok.isClickable = true

                val alertDialog =
                    AlertDialog.Builder(
                        ContextThemeWrapper(
                            activity,
                            R.style.Theme_AppCompat_Light_Dialog
                        )
                    )

                alertDialog.setTitle("실패")
                alertDialog.setMessage("회원 탈퇴를 실패하였습니다.")
                alertDialog.setPositiveButton("확인",
                    object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {

                        }
                    })
                alertDialog.show()

            }
        }
    }


    companion object {
        private const val TAG = "MyPageWithdrawalFragment"

        private var toggleWithdraw = false
        private var toggleWithdrawLogin = false


        const val WITHDRAW_ID = "id"
        const val WITHDRAW_NICKNAME = "nickname"

        fun newInstance(
            userId: String,
            userNickname: String
        ) = MyPageWithdrawalFragment().apply {
            arguments = Bundle().apply {
                putString(WITHDRAW_ID, userId)
                putString(WITHDRAW_NICKNAME, userNickname)
            }
        }

    }

}
