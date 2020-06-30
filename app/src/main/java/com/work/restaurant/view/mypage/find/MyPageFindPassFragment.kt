package com.work.restaurant.view.mypage.find

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.work.restaurant.R
import com.work.restaurant.databinding.MypageFindFragmentBinding
import com.work.restaurant.ext.showToast
import com.work.restaurant.util.ShowAlertDialog
import com.work.restaurant.base.BaseFragment
import com.work.restaurant.view.mypage.find.presenter.MyPageFindPassContract
import com.work.restaurant.view.mypage.find_ok.MyPageFindOkFragment
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf

class MyPageFindPassFragment : BaseFragment<MypageFindFragmentBinding>(
    MypageFindFragmentBinding::bind,
    R.layout.mypage_find_fragment
), View.OnClickListener,
    MyPageFindPassContract.View {

    private lateinit var presenter: MyPageFindPassContract.Presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = get { parametersOf(this) }
        binding.ibFindBack.setOnClickListener(this)
        binding.btnRequestChangePass.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ib_find_back -> {
                fragmentManager?.popBackStack()
            }

            R.id.btn_request_change_pass -> {
                showProgressState(true)
                presenter.resetPass(binding.etChangePass.text.toString().trim())
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
        binding.pbFind.apply {
            bringToFront()
            isVisible = state
        }
        binding.btnRequestChangePass.isClickable = !state
    }

    companion object {
        private const val TAG = "MyPageFindFragment"
    }

}
