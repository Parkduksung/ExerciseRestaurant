package com.work.restaurant.view.mypage.fragment

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
import com.work.restaurant.data.model.ResultModel
import com.work.restaurant.login.UserApi
import com.work.restaurant.view.mypage.fragment.MyPageFragment.Companion.loginState
import kotlinx.android.synthetic.main.mypage_login_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyPageLoginFragment : Fragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_login -> {

                val alertDialog =
                    AlertDialog.Builder(
                        ContextThemeWrapper(
                            activity,
                            R.style.Theme_AppCompat_Light_Dialog
                        )
                    )


                val retrofit = Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                Log.d("1111", "$retrofit")

                val userApi = retrofit.create(UserApi::class.java)

                Log.d("1111", "$userApi")


                userApi.login(
                    et_email.text.toString(),
                    et_pass.text.toString()
                )?.enqueue(object : Callback<ResultModel> {

                    override fun onResponse(
                        call: Call<ResultModel>?,
                        response: Response<ResultModel>?
                    ) {

                        val resultModel = response?.body()
                        Log.d("1111", "${resultModel?.result}")
                        Log.d("1111", "${resultModel?.resultNickname}")

                        if (resultModel?.result.equals("ok")) {


                            alertDialog.setTitle("로그인 성공")
                            alertDialog.setMessage(et_email.text.toString() + "님 환영합니다!")
                            alertDialog.setPositiveButton(
                                "확인",
                                object : DialogInterface.OnClickListener {
                                    override fun onClick(dialog: DialogInterface?, which: Int) {
                                        this@MyPageLoginFragment.requireFragmentManager()
                                            .beginTransaction()
                                            .replace(
                                                R.id.mypage_main_container,
                                                MyPageFragment()
                                            ).commit().also {
                                                loginState = true
                                                userId = et_email.text.toString()
                                                userNickname =
                                                    resultModel?.resultNickname.toString()

                                            }
                                    }
                                })
                            alertDialog.show()


//                            this@MyPageLoginFragment.requireFragmentManager().beginTransaction()
//                                .replace(
//                                    R.id.mypage_main_container,
//                                    MyPageFragment()
//                                ).commit().also {
//                                    loginState = true
//                                    userId = et_email.text.toString()
//                                }

                        } else {


                            alertDialog.setTitle("로그인 실패")
                            alertDialog.setMessage("입력한 정보를 확인바랍니다.")
                            alertDialog.setPositiveButton(
                                "확인",
                                object : DialogInterface.OnClickListener {
                                    override fun onClick(dialog: DialogInterface?, which: Int) {
                                        Log.d("1111", "실패")
                                    }
                                })
                            alertDialog.show()

                        }

                    }

                    override fun onFailure(call: Call<ResultModel>?, t: Throwable?) {

                        alertDialog.setTitle("로그인 실패")
                        alertDialog.setMessage("네트워크를 확인바랍니다.")
                        alertDialog.setPositiveButton(
                            "확인",
                            object : DialogInterface.OnClickListener {
                                override fun onClick(dialog: DialogInterface?, which: Int) {
                                    Log.d("1111", "네트워크문제!!")
                                    Log.d("1111", "$call")
                                    Log.d("1111", "$t")
                                }
                            })
                        alertDialog.show()

                    }


                })


            }

            R.id.ib_login_back -> {
                this.requireFragmentManager().beginTransaction().replace(
                    R.id.mypage_main_container,
                    MyPageFragment()
                ).commit()
            }

            R.id.tv_login_register -> {
                this.requireFragmentManager().beginTransaction().replace(
                    R.id.mypage_main_container,
                    MyPageRegisterFragment()
                ).commit()
            }

            R.id.tv_login_find -> {
                this.requireFragmentManager().beginTransaction().replace(
                    R.id.loading_container,
                    MyPageFindFragment()
                ).commit()
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
        return inflater.inflate(R.layout.mypage_login_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "onActivityCreated")
        super.onActivityCreated(savedInstanceState)

//        val retrofit = Retrofit.Builder()
//            .baseUrl(URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        Log.d("1111", "$retrofit")
//
//        val userApi = retrofit.create(UserApi::class.java)
//
//        Log.d("1111", "$userApi")


        btn_login.setOnClickListener(this)
        ib_login_back.setOnClickListener(this)
        tv_login_register.setOnClickListener(this)
        tv_login_find.setOnClickListener(this)


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
        var userId = ""
        var userNickname = ""
        private const val TAG = "MyPageLoginFragment"
        private const val URL = "https://duksung12.cafe24.com"
    }


}

