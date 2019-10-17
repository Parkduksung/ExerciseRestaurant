package com.work.restaurant.view.fragment

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.postDelayed
import androidx.fragment.app.Fragment
import com.work.restaurant.R
import kotlinx.android.synthetic.main.loading_fragment.*
import java.util.*

class LoadingFragment : Fragment() {


    private val fragmentName = "LoadingFragment"

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
        return inflater.inflate(R.layout.loading_fragment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(fragmentName, "onActivityCreated")
        super.onActivityCreated(savedInstanceState)


        val random = Random().nextInt(resources.getStringArray(R.array.load_string).size)
        val loadingTextArrayList = resources.getStringArray(R.array.load_string)

        Log.d(fragmentName, loadingTextArrayList[random])

        loading_tv.text = loadingTextArrayList[random]


        Handler().postDelayed(3000L) {
            requireFragmentManager().beginTransaction().remove(this).commit()
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


}

