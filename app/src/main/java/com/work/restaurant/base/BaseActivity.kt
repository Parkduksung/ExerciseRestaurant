package com.work.restaurant.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.work.restaurant.R
import com.work.restaurant.ext.showToast


abstract class BaseActivity<T : ViewBinding>(
    private val inflate: (LayoutInflater) -> T,
    @LayoutRes val layoutId: Int
) :
    AppCompatActivity() {

    protected lateinit var binding: T
        private set

    var mBackWait: Long =
        INIT_TIME

    override fun onBackPressed() {

        var handled = false

        for (fragment in supportFragmentManager.fragments) {
            if (fragment is BaseFragment<*>) {
                handled = fragment.onBackPressed()
                if (handled) {
                    supportFragmentManager.popBackStack()
                    break
                }
            }
        }

        if (!handled) {
            if (layoutId == R.layout.activity_main) {
                if (System.currentTimeMillis() - mBackWait >= LIMIT_TIME) {
                    mBackWait = System.currentTimeMillis()
                    showToast(getString(R.string.baseActivity_backPressed))
                } else {
                    finish()
                }
            } else {
                super.onBackPressed()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(layoutInflater)
        setContentView(binding.root)
    }

    companion object {

        private const val INIT_TIME = 0L
        private const val LIMIT_TIME = 2000


    }

}
