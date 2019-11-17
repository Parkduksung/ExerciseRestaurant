package com.work.restaurant.view.fragment.mypage

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.work.restaurant.R
import com.work.restaurant.login.ResultModel
import com.work.restaurant.login.UserApi
import kotlinx.android.synthetic.main.mypage_find_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyPageFindFragment : Fragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ib_find_back -> {
                this.requireFragmentManager().beginTransaction().remove(
                    this
                ).commit()
            }

            R.id.btn_request_change_pass -> {

                val retrofit = Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()


                val userApi = retrofit.create(UserApi::class.java)


                inputEmail = et_change_pass.text.toString()

                userApi.find(
                    inputEmail
                ).enqueue(object : Callback<ResultModel> {
                    override fun onFailure(call: Call<ResultModel>?, t: Throwable?) {
                        Log.d("ccccc", "네트워크 연결 실패")
                    }

                    override fun onResponse(
                        call: Call<ResultModel>?,
                        response: Response<ResultModel>?
                    ) {

                        val result = response?.body()?.result


                        if (result.equals("ok")) {
                            Log.d("ttt", "성공")


                            this@MyPageFindFragment.requireFragmentManager().beginTransaction()
                                .replace(
                                    R.id.loading_container,
                                    MyPageFindOkFragment()
                                ).commit()


                        } else {
                            Log.d("ttt", "실패")
                        }


                        Log.d("ccccc", "네트워크 연결 실패")

                    }
                })


                this.requireFragmentManager().beginTransaction().replace(
                    R.id.loading_container,
                    MyPageFindOkFragment()
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
        return inflater.inflate(R.layout.mypage_find_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "onActivityCreated")
        super.onActivityCreated(savedInstanceState)

//        val retrofit = Retrofit.Builder()
//            .baseUrl(url)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//
//        val userApi = retrofit.create(UserApi::class.java)
//

        ib_find_back.setOnClickListener(this)



        btn_request_change_pass.setOnClickListener(this)


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
        var inputEmail = ""
        private const val TAG = "MyPageFindFragment"
    }

}
