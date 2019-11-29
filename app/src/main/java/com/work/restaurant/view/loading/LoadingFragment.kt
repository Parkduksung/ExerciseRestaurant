package com.work.restaurant.view.loading

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.work.restaurant.R
import kotlinx.android.synthetic.main.loading_fragment.*

class LoadingFragment : Fragment(), LoadingContract.View {


    private lateinit var presenter: LoadingContract.Presenter

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
        return inflater.inflate(R.layout.loading_fragment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "onActivityCreated")
        super.onActivityCreated(savedInstanceState)

        presenter = LoadingPresenter(this)

        start()

    }

    private fun start() {

        presenter.randomText(resources.getStringArray(R.array.load_string))
        presenter.delayTime()

    }


    override fun showStartText(text: String) {
        loading_tv.text = text
    }

    override fun showDelay() {
        Handler().postDelayed({
            requireFragmentManager().beginTransaction().remove(this).commit()
        }, 3000L)
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
        private const val TAG = "LoadingFragment"

    }
}

