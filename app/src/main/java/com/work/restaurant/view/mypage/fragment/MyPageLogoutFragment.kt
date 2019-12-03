package com.work.restaurant.view.mypage.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.work.restaurant.R
import com.work.restaurant.view.mypage.contract.MyPageLogoutContract
import com.work.restaurant.view.mypage.presenter.MyPageLogoutPresenter
import kotlinx.android.synthetic.main.mypage_logout_fragment.*

class MyPageLogoutFragment : Fragment(), View.OnClickListener, MyPageLogoutContract.View {

    private lateinit var presenter: MyPageLogoutContract.Presenter


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_logout_cancel -> {
                presenter.logoutCancel()
            }

            R.id.btn_logout_ok -> {
                presenter.logoutOk()
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
        return inflater.inflate(R.layout.mypage_logout_fragment, container, false).also {
            presenter = MyPageLogoutPresenter(this)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "onActivityCreated")
        super.onActivityCreated(savedInstanceState)

        btn_logout_cancel.setOnClickListener(this)
        btn_logout_ok.setOnClickListener(this)


    }

    override fun showLogoutCancel() {
        this.requireFragmentManager().beginTransaction().remove(
            this@MyPageLogoutFragment
        ).commit()
    }

    override fun showLogoutOk() {

        this.requireFragmentManager().beginTransaction().remove(
            this@MyPageLogoutFragment
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


    override fun onStart() {
        Log.d(TAG, "onStart")
        super.onStart()
    }

    override fun onResume() {
        Log.d(TAG, "onResume")
        super.onResume()
    }

    override fun onPause() {
        Log.d(TAG, "onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.d(TAG, "onStop")
        super.onStop()
    }

    override fun onDestroyView() {
        Log.d(TAG, "onDestroyView")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")
        super.onDestroy()
    }

    override fun onDetach() {
        Log.d(TAG, "onDetach")
        super.onDetach()
    }

    companion object {
        private const val TAG = "MyPageLogoutFragment"
    }

}
