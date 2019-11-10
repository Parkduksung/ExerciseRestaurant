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
import com.work.restaurant.view.fragment.mypage.MyPageLoginFragment.Companion.userId
import kotlinx.android.synthetic.main.mypage_register_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyPageRegisterFragment : Fragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        val alertDialog =
            AlertDialog.Builder(ContextThemeWrapper(activity, R.style.Theme_AppCompat_Light_Dialog))



        when (v?.id) {
            R.id.ib_register_back -> {
                this.requireFragmentManager().beginTransaction().replace(
                    R.id.mypage_main_container,
                    MyPageLoginFragment()
                ).commit()
            }

            R.id.btn_register -> {

                var emailState = false
                var nicknameState = false
                var passState = false
                var passSameState = false

                if (et_register_nickname.text.toString() != "") {
                    nicknameState = true
                }
                if (et_register_email.text.toString() != "") {
                    emailState = true
                }
                if (et_register_pass.text.toString() != "") {
                    passState = true
                }
                if (et_register_pass == et_register_pass_ok) {
                    passSameState = true
                }


                val retrofit = Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()


                val userApi = retrofit.create(UserApi::class.java)


                if (nicknameState || emailState || passState || passSameState) {

                    userApi.register(
                        et_register_nickname.text.toString(),
                        et_register_email.text.toString(),
                        et_register_pass.text.toString()
                    ).enqueue(object : Callback<ResultModel> {
                        override fun onFailure(call: Call<ResultModel>?, t: Throwable?) {

                            alertDialog.setTitle("회원가입 실패")
                            alertDialog.setMessage("회원 탈퇴를 실패하였습니다.")
                            alertDialog.setPositiveButton("확인",
                                object : DialogInterface.OnClickListener {
                                    override fun onClick(dialog: DialogInterface?, which: Int) {

                                        Log.d("1111111", "실패")
                                    }

                                })
                            alertDialog.show()

                            Log.d("1111111", "네트워크 실패")
                        }

                        override fun onResponse(
                            call: Call<ResultModel>?,
                            response: Response<ResultModel>?
                        ) {
                            val result = response?.body()?.result

                            if (result.equals("ok")) {
                                Log.d("1111111", "성공")


                                this@MyPageRegisterFragment.requireFragmentManager()
                                    .beginTransaction()
                                    .replace(
                                        R.id.mypage_main_container,
                                        MyPageRegisterOkFragment()
                                    ).commit().also {
                                        MyPageFragment.loginState = true
                                        userId = et_register_email.text.toString()
                                    }

                            } else {
                                alertDialog.setTitle("회웝가입 실패")
                                alertDialog.setMessage("회원 가입을 실패하였습니다.")
                                alertDialog.setPositiveButton("확인",
                                    object : DialogInterface.OnClickListener {
                                        override fun onClick(dialog: DialogInterface?, which: Int) {

                                            Log.d("1111111", "실패")
                                        }

                                    })
                                alertDialog.show()

                            }

                        }

                    })

                } else {

                    alertDialog.setTitle("실패")
                    alertDialog.setMessage("정보 입력란을 확인하세요.")
                    alertDialog.setPositiveButton("확인",
                        object : DialogInterface.OnClickListener {
                            override fun onClick(dialog: DialogInterface?, which: Int) {

                            }
                        })
                    alertDialog.show()


                }


            }

        }
    }

    override fun onAttach(context: Context) {
        Log.d(fragmentName, "onAttach")
        super.onAttach(context)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(fragmentName, "onCreate")
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.mypage_register_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(fragmentName, "onActivityCreated")
        super.onActivityCreated(savedInstanceState)


        ib_register_back.setOnClickListener(this)
//        val retrofit = Retrofit.Builder()
//            .baseUrl(url)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//
//        val userApi = retrofit.create(UserApi::class.java)

        btn_register.setOnClickListener(this)


    }


    override fun onStart() {
        Log.d(fragmentName, "onStart")
        super.onStart()
    }

    override fun onResume() {
        Log.d(fragmentName, "onResume")
        super.onResume()
    }

    override fun onPause() {
        Log.d(fragmentName, "onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.d(fragmentName, "onStop")
        super.onStop()
    }

    override fun onDestroyView() {
        Log.d(fragmentName, "onDestroyView")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d(fragmentName, "onDestroy")
        super.onDestroy()
    }

    override fun onDetach() {
        Log.d(fragmentName, "onDetach")
        super.onDetach()
    }

    companion object {
        private const val fragmentName = "MyPageRegisterFragment"
        private const val url = "https://duksung12.cafe24.com"
    }


}