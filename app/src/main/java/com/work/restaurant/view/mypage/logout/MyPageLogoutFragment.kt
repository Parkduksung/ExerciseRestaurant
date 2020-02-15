package com.work.restaurant.view.mypage.logout

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.work.restaurant.R
import com.work.restaurant.util.App
import com.work.restaurant.view.base.BaseFragment
import com.work.restaurant.view.mypage.logout.presenter.MyPageLogoutContract
import com.work.restaurant.view.mypage.logout.presenter.MyPageLogoutPresenter
import kotlinx.android.synthetic.main.mypage_logout_fragment.*

class MyPageLogoutFragment : BaseFragment(R.layout.mypage_logout_fragment), View.OnClickListener,
    MyPageLogoutContract.View {

    private lateinit var presenter: MyPageLogoutContract.Presenter


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_logout_cancel -> {
                activity?.onBackPressed()
            }


            R.id.btn_logout_ok -> {
                presenter.logoutOk()
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
        presenter = MyPageLogoutPresenter(this)
        btn_logout_cancel.setOnClickListener(this)
        btn_logout_ok.setOnClickListener(this)


    }


    override fun showLogoutOk() {

        val data = Intent()
        data.putExtra("id", "")
        data.putExtra("nickname", "")
        targetFragment?.onActivityResult(
            targetRequestCode,
            Activity.RESULT_OK,
            data
        )

        activity?.onBackPressed()

//        fragmentManager?.beginTransaction()
//            ?.replace(
//                R.id.main_container,
//                MyPageFragment()
//            )
//            ?.commit()


//        val data = Intent()
//        data.putExtra("id", "")
//        data.putExtra("nickname", "")
//        targetFragment?.onActivityResult(
//            targetRequestCode,
//            Activity.RESULT_OK,
//            data
//        )
//
//        activity?.onBackPressed()


//        fragmentManager?.beginTransaction()
//            ?.remove(this@MyPageLogoutFragment)
//            ?.commit()
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
