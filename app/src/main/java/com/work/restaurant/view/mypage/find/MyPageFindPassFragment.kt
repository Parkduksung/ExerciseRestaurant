package com.work.restaurant.view.mypage.find

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.work.restaurant.R
import com.work.restaurant.data.repository.user.UserRepositoryImpl
import com.work.restaurant.data.source.remote.user.UserRemoteDataSourceSourceImpl
import com.work.restaurant.network.RetrofitInstance
import com.work.restaurant.view.mypage.find.presenter.MyPageFindPassContract
import com.work.restaurant.view.mypage.find.presenter.MyPageFindPassPresenter
import com.work.restaurant.view.mypage.find_ok.MyPageFindOkFragment
import com.work.restaurant.view.mypage.main.MyPageFragment
import kotlinx.android.synthetic.main.mypage_find_fragment.*

class MyPageFindPassFragment : Fragment(), View.OnClickListener, MyPageFindPassContract.View {
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

    override fun showBackPage() {
        this.requireFragmentManager().beginTransaction().remove(
            this
        ).commit()
    }

    private lateinit var presenter: MyPageFindPassPresenter

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ib_find_back -> {
                presenter.backPage()
            }

            R.id.btn_request_change_pass -> {
                presenter.resetPass(et_change_pass.text.toString())
            }
        }
    }


    override fun onAttach(context: Context) {
        Log.d(TAG, "onAttach")
        super.onAttach(context)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.mypage_find_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "onActivityCreated")
        super.onActivityCreated(savedInstanceState)


        presenter = MyPageFindPassPresenter(
            this, UserRepositoryImpl.getInstance(
                UserRemoteDataSourceSourceImpl.getInstance(
                    RetrofitInstance.getInstance(
                        MyPageFragment.URL
                    )
                )
            )
        )
        ib_find_back.setOnClickListener(this)
        btn_request_change_pass.setOnClickListener(this)


    }


    companion object {
        private const val URL = "https://duksung12.cafe24.com"
        private const val TAG = "MyPageFindFragment"
    }

}
