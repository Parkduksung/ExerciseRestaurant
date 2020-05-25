package com.work.restaurant.view.mypage.withdraw

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.work.restaurant.R
import com.work.restaurant.databinding.MypageWithdrawalFragmentBinding
import com.work.restaurant.ext.showToast
import com.work.restaurant.util.App
import com.work.restaurant.util.ShowAlertDialog
import com.work.restaurant.view.base.BaseFragment
import com.work.restaurant.view.mypage.withdraw.presenter.MyPageWithdrawalContract
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf

class MyPageWithdrawalFragment : BaseFragment<MypageWithdrawalFragmentBinding>(
    MypageWithdrawalFragmentBinding::bind,
    R.layout.mypage_withdrawal_fragment
),
    View.OnClickListener, MyPageWithdrawalContract.View {

    private lateinit var presenter: MyPageWithdrawalContract.Presenter

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
        presenter = get { parametersOf(this) }

        binding.btnWithdrawCancel.setOnClickListener(this)
        binding.btnWithdrawOk.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.btn_withdraw_cancel -> {
                fragmentManager?.popBackStack()
            }

            R.id.btn_withdraw_ok -> {
                showProgressState(true)

                val getUserId =
                    arguments?.getString(WITHDRAW_ID).orEmpty()
                val getUserNickname =
                    arguments?.getString(WITHDRAW_NICKNAME).orEmpty()

                if (getUserId.isNotEmpty() && getUserNickname.isNotEmpty()) {
                    presenter.run {
                        withdraw(getUserNickname, getUserId)
                        withdrawLogin(getUserNickname, getUserId)
                    }
                } else {
                    showProgressState(false)
                    showToast(getString(R.string.withdrawal_no))
                }

            }
        }
    }


    override fun showProgressState(state: Boolean) {
        binding.pbWithdrawal.apply {
            bringToFront()
            isVisible = state
        }
        binding.btnWithdrawOk.isClickable = !state
        binding.btnWithdrawCancel.isClickable = !state
    }


    private fun withdrawOk(userNickname: String) {

        if (toggleWithdraw && toggleWithdrawLogin) {
            showProgressState(false)
            toggleWithdraw = false
            toggleWithdrawLogin = false

            targetFragment?.onActivityResult(
                targetRequestCode,
                Activity.RESULT_OK,
                null
            )


            ShowAlertDialog(context).apply {
                titleAndMessage(
                    getString(R.string.common_success),
                    getString(R.string.withdrawal_ok_result, userNickname)
                )
                alertDialog.setPositiveButton(
                    getString(R.string.common_ok)
                ) { _, _ -> }
                showDialog()
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

    override fun showWithdrawNo() {
        showProgressState(false)


        ShowAlertDialog(context).apply {
            titleAndMessage(getString(R.string.common_fail), getString(R.string.withdrawal_no))
            alertDialog.setPositiveButton(
                getString(R.string.common_ok)
            ) { _, _ -> }
            showDialog()
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
