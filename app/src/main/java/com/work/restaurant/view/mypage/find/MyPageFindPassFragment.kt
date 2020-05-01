package com.work.restaurant.view.mypage.find

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.work.restaurant.Injection
import com.work.restaurant.R
import com.work.restaurant.ext.showToast
import com.work.restaurant.util.ShowAlertDialog
import com.work.restaurant.view.base.BaseFragment
import com.work.restaurant.view.mypage.find.presenter.MyPageFindPassContract
import com.work.restaurant.view.mypage.find.presenter.MyPageFindPassPresenter
import com.work.restaurant.view.mypage.find_ok.MyPageFindOkFragment
import kotlinx.android.synthetic.main.mypage_find_fragment.*

class MyPageFindPassFragment : BaseFragment(R.layout.mypage_find_fragment), View.OnClickListener,
    MyPageFindPassContract.View {

    private lateinit var presenter: MyPageFindPassPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter =
            MyPageFindPassPresenter(
                this,
                Injection.provideUserRepository()
            )
        ib_find_back.setOnClickListener(this)
        btn_request_change_pass.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ib_find_back -> {
                fragmentManager?.popBackStack()
            }

            R.id.btn_request_change_pass -> {
                showProgressState(true)
                presenter.resetPass(et_change_pass.text.toString().trim())
            }
        }
    }

    override fun showResetOk() {

        showProgressState(false)

        showToast(getString(R.string.find_ok_message))

        requireFragmentManager().beginTransaction().replace(
            R.id.myPage_find_container,
            MyPageFindOkFragment()
        ).commit()

    }

    override fun showResetNo() {

        showProgressState(false)


        ShowAlertDialog(context).apply {
            titleAndMessage(
                getString(R.string.find_not_init_pass),
                getString(R.string.find_not_consist_email)
            )
            alertDialog.setPositiveButton(
                getString(R.string.common_ok)
            ) { _, _ -> }
            showDialog()
        }

    }

    override fun showProgressState(state: Boolean) {
        pb_find?.let {
            pb_find.bringToFront()
            pb_find.isVisible = state
        }

        btn_request_change_pass?.let {
            btn_request_change_pass.isClickable = !state
        }
    }

    companion object {
        private const val TAG = "MyPageFindFragment"
    }

}
