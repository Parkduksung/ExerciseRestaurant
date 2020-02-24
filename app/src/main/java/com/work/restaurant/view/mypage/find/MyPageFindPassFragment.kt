package com.work.restaurant.view.mypage.find

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.Toast
import com.work.restaurant.Injection
import com.work.restaurant.R
import com.work.restaurant.util.App
import com.work.restaurant.view.base.BaseFragment
import com.work.restaurant.view.mypage.find.presenter.MyPageFindPassContract
import com.work.restaurant.view.mypage.find.presenter.MyPageFindPassPresenter
import com.work.restaurant.view.mypage.find_ok.MyPageFindOkFragment
import kotlinx.android.synthetic.main.mypage_find_fragment.*

class MyPageFindPassFragment : BaseFragment(R.layout.mypage_find_fragment), View.OnClickListener,
    MyPageFindPassContract.View {

    private lateinit var presenter: MyPageFindPassPresenter

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ib_find_back -> {
                requireFragmentManager().popBackStack()
            }

            R.id.btn_request_change_pass -> {
                pb_find_email.bringToFront()
                pb_find_email.visibility = View.VISIBLE
                btn_request_change_pass.isClickable = false
                presenter.resetPass(et_change_pass.text.toString().trim())
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = MyPageFindPassPresenter(
            this,
            Injection.provideUserRepository()
        )
        ib_find_back.setOnClickListener(this)
        btn_request_change_pass.setOnClickListener(this)

    }


    override fun showResetOk() {

        if (pb_find_email != null) {
            pb_find_email.visibility = View.GONE
            btn_request_change_pass.isClickable = true
            Toast.makeText(
                App.instance.context(),
                "등록된 메일에서 비밀번호 초기화 할 수 있습니다.",
                Toast.LENGTH_SHORT
            ).show()

            requireFragmentManager().beginTransaction().replace(
                R.id.mypage_find_container,
                MyPageFindOkFragment()
            ).commit()

        }

    }

    override fun showResetNo(message: String) {

        if (pb_find_email != null) {
            pb_find_email.visibility = View.GONE
            btn_request_change_pass.isClickable = true

            val alertDialog =
                AlertDialog.Builder(
                    ContextThemeWrapper(
                        activity,
                        R.style.Theme_AppCompat_Light_Dialog
                    )
                )

            alertDialog.setTitle("비밀번호 초기화 실패")
            alertDialog.setMessage("등록된 이메일이 없습니다.")
            alertDialog.setPositiveButton(
                "확인",
                object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {

                    }
                })
            alertDialog.show()

        }
    }


    companion object {
        private const val TAG = "MyPageFindFragment"
    }

}
