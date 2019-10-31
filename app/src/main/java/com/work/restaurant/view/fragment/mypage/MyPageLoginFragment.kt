package com.work.restaurant.view.fragment.mypage

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.work.restaurant.R
import kotlinx.android.synthetic.main.mypage_login_fragment.*

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
    }


}