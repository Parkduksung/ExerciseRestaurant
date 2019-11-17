package com.work.restaurant.view.fragment.mypage

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
import com.work.restaurant.login.ResultModel
import com.work.restaurant.login.UserApi
import kotlinx.android.synthetic.main.mypage_withdrawal_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyPageWithdrawalFragment : Fragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        val alertDialog =
            AlertDialog.Builder(ContextThemeWrapper(activity, R.style.Theme_AppCompat_Light_Dialog))


        when (v?.id) {
            R.id.btn_withdraw_cancel -> {
                this.requireFragmentManager().beginTransaction().remove(
                    this@MyPageWithdrawalFragment
                ).commit()

            }

            R.id.btn_withdraw_ok -> {

                val retrofit = Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()


                val userApi = retrofit.create(UserApi::class.java)


                Log.d("ttt", "${MyPageLoginFragment.userId}")
                userApi.delete(
                    MyPageLoginFragment.userId
                ).enqueue(object : Callback<ResultModel> {
                    override fun onFailure(call: Call<ResultModel>?, t: Throwable?) {
                        Log.d("ttt", "네트워크연결실패")

                        alertDialog.setTitle("실패")
                        alertDialog.setMessage("네트워크 상태를 확인바랍니다.")
                        alertDialog.setPositiveButton(
                            "확인",
                            object : DialogInterface.OnClickListener {
                                override fun onClick(dialog: DialogInterface?, which: Int) {

                                    this@MyPageWithdrawalFragment.requireFragmentManager()
                                        .beginTransaction()
                                        .remove(
                                            this@MyPageWithdrawalFragment
                                        ).commit()
                                }
                            })
                        alertDialog.show()

                    }

                    override fun onResponse(
                        call: Call<ResultModel>?,
                        response: Response<ResultModel>?
                    ) {
                        val result = response?.body()?.result


                        if (result.equals("ok")) {
                            Log.d("ttt", "성공")


                            alertDialog.setTitle("성공")
                            alertDialog.setMessage("정상적으로 탈퇴 되었습니다.")
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
                                                MyPageFragment.loginState = false
                                                MyPageLoginFragment.userId = ""
                                            }
                                    }

                                })

                            alertDialog.show()


                        } else {

                            val alertDialog = AlertDialog.Builder(
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

                                        this@MyPageWithdrawalFragment.requireFragmentManager()
                                            .beginTransaction()
                                            .remove(
                                                this@MyPageWithdrawalFragment
                                            ).commit()

                                    }

                                })
                            alertDialog.show()

                            Log.d("ttt", "실패")
                        }
                    }

                })


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
        return inflater.inflate(R.layout.mypage_withdrawal_fragment, container, false).also {


        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "onActivityCreated")
        super.onActivityCreated(savedInstanceState)

//
//        val alertDialog =
//            AlertDialog.Builder(ContextThemeWrapper(activity, R.style.Theme_AppCompat_Light_Dialog))
//
//
//        val retrofit = Retrofit.Builder()
//            .baseUrl(url)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//
//        val userApi = retrofit.create(UserApi::class.java)


        btn_withdraw_cancel.setOnClickListener(this)

        btn_withdraw_ok.setOnClickListener(this)

//
//
//            this.requireFragmentManager().beginTransaction().remove(
//                this@MyPageWithdrawalFragment
//            ).replace(
//                R.id.mypage_main_container,
//                MyPageFragment()
//            ).commit().also {
//                MyPageFragment.loginState = false
//            }

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
        private const val url = "https://duksung12.cafe24.com"
        private const val TAG = "MyPageWithdrawalFragment"
    }

}
