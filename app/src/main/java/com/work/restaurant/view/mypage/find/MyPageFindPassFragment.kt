package com.work.restaurant.view.mypage.find

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.View
import com.work.restaurant.Injection
import com.work.restaurant.R
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
                activity?.onBackPressed()
            }

            R.id.btn_request_change_pass -> {
                presenter.resetPass(et_change_pass.text.toString())
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


    override fun showResetOk(nickName: String) {

        val alertDialog =
            AlertDialog.Builder(
                ContextThemeWrapper(
                    activity,
                    R.style.Theme_AppCompat_Light_Dialog
                )
            )

        alertDialog.setTitle("비밀번호 초기화 성공")
        alertDialog.setMessage(nickName + "님, 등록된 메일에서 비밀번호 초기화 할 수 있습니다.")
        alertDialog.setPositiveButton(
            "확인",
            object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    this@MyPageFindPassFragment.requireFragmentManager().beginTransaction().replace(
                        R.id.main_container,
                        MyPageFindOkFragment()
                    ).commit()
                }
            })
        alertDialog.show()


    }

    override fun showResetNo(message: String) {

        val alertDialog =
            AlertDialog.Builder(
                ContextThemeWrapper(
                    activity,
                    R.style.Theme_AppCompat_Light_Dialog
                )
            )

        alertDialog.setTitle("비밀번호 초기화 실패")
        alertDialog.setMessage(message)
        alertDialog.setPositiveButton(
            "확인",
            object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    Log.d("1111", "실패")
                }
            })
        alertDialog.show()
    }


    companion object {
        private const val TAG = "MyPageFindFragment"
    }

}
