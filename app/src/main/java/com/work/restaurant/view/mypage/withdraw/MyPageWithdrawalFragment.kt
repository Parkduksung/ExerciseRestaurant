package com.work.restaurant.view.mypage.withdraw

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.work.restaurant.Injection
import com.work.restaurant.R
import com.work.restaurant.util.App
import com.work.restaurant.view.base.BaseFragment
import com.work.restaurant.view.mypage.main.MyPageFragment
import com.work.restaurant.view.mypage.main.MyPageFragment.Companion.userId
import com.work.restaurant.view.mypage.main.MyPageFragment.Companion.userNickname
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
                presenter.withdraw(userNickname, userId)
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
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = MyPageWithdrawalPresenter(
            this,
            Injection.provideUserRepository()
        )
        btn_withdraw_cancel.setOnClickListener(this)
        btn_withdraw_ok.setOnClickListener(this)


    }


    override fun showWithdrawNo() {
        val alertDialog =
            AlertDialog.Builder(ContextThemeWrapper(activity, R.style.Theme_AppCompat_Light_Dialog))

        alertDialog.setTitle("실패")
        alertDialog.setMessage("회원 탈퇴를 실패하였습니다.")
        alertDialog.setPositiveButton("확인",
            object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {

                }
            })
        alertDialog.show()

    }

    override fun showWithdrawCancel() {
        this.requireFragmentManager().beginTransaction().remove(
            this@MyPageWithdrawalFragment
        ).commit()
    }

    override fun showWithdrawOk(userNickname: String) {
        val alertDialog =
            AlertDialog.Builder(ContextThemeWrapper(activity, R.style.Theme_AppCompat_Light_Dialog))

        alertDialog.setTitle("성공")
        alertDialog.setMessage(userNickname + "님이 정상적으로 탈퇴 되었습니다.")
        alertDialog.setPositiveButton(
            "확인",
            object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {

                    this@MyPageWithdrawalFragment.requireFragmentManager()
                        .beginTransaction()
                        .remove(
                            this@MyPageWithdrawalFragment
                        ).replace(
                            R.id.mypage_main_container,
                            MyPageFragment()
                        ).commit().also {
                            val data = Intent()
                            data.putExtra("id", "")
                            data.putExtra("nickname", "")
                            targetFragment?.onActivityResult(
                                targetRequestCode,
                                Activity.RESULT_OK,
                                data
                            )
                        }
                }

            })

        alertDialog.show()


    }

    companion object {
        private const val TAG = "MyPageWithdrawalFragment"
    }

}
