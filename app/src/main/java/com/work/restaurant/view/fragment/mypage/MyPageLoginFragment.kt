package com.work.restaurant.view.fragment.mypage

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.gson.GsonBuilder
import com.work.restaurant.R
import com.work.restaurant.login.ResultModel
import com.work.restaurant.login.UserApi
import kotlinx.android.synthetic.main.mypage_login_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyPageLoginFragment : Fragment() {

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
        return inflater.inflate(R.layout.mypage_login_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(fragmentName, "onActivityCreated")
        super.onActivityCreated(savedInstanceState)


        val gson = GsonBuilder().setLenient().create()


        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        Log.d("1111","$retrofit")

        val userApi = retrofit.create(UserApi::class.java)

        Log.d("1111","$userApi")


        btn_login.setOnClickListener {
            userApi.login(
                et_email.text.toString(),
                et_pass.text.toString()
            )?.enqueue(object : Callback<ResultModel> {

                override fun onResponse(call: Call<ResultModel>, response: Response<ResultModel>) {

                    this@MyPageLoginFragment.requireFragmentManager().beginTransaction().replace(
                        R.id.mypage_main_container,
                        MyPageFragment()
                    ).commit()

                    Log.d("1111","$call")
                    Log.d("1111","$response")
                    Log.d("1111","연결 성공")

                    val result = response.body()

                    Log.d("1111","$result")

                    if(result!!.equals("ok")){
                        Log.d("1111","ok성공")
                    }else{
                        Log.d("1111","ok실패")
                    }
                }

                override fun onFailure(call: Call<ResultModel>, t: Throwable) {

                    Log.d("1111",""+et_email.text.toString())
                    Log.d("1111",""+et_pass.text.toString())
                    Log.d("1111",""+t)
                    Log.d("1111",""+call)
                    Log.d("1111","연결 실패")

                }


            })
        }

        ib_login_back.setOnClickListener {

            this.requireFragmentManager().beginTransaction().replace(
                R.id.mypage_main_container,
                MyPageFragment()
            ).commit()

        }


        tv_login_register.setOnClickListener {

            this.requireFragmentManager().beginTransaction().replace(
                R.id.mypage_main_container,
                MyPageRegisterFragment()
            ).commit()

        }


        tv_login_find.setOnClickListener {


        }


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
        private const val fragmentName = "MyPageLoginFragment"
        private const val url = "http://duksung12.cafe24.com/"
    }


}