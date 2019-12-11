package com.work.restaurant.view.home

import android.graphics.Typeface
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.work.restaurant.R
import com.work.restaurant.view.home.fragment.HomeAddressAdapterSelect1Fragment
import com.work.restaurant.view.home.fragment.HomeAddressAdapterSelect2Fragment
import com.work.restaurant.view.home.fragment.HomeAddressAdapterSelect3Fragment
import kotlinx.android.synthetic.main.address_main.*


class HomeAddressActivity : AppCompatActivity(), HomeAddressContract.View, View.OnClickListener {

    private lateinit var presenter: HomeAddressContract.Presenter


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ib_home_address_back -> {
                presenter.backPage()
            }

            R.id.tv_address1 -> {

                replaceFragment(HomeAddressAdapterSelect1Fragment())

                tv_address1.setTextColor(
                    ContextCompat.getColor(
                        this@HomeAddressActivity,
                        R.color.colorWhite
                    )
                )
                tv_address2.setTextColor(
                    ContextCompat.getColor(
                        this@HomeAddressActivity,
                        R.color.colorGrayBasic
                    )
                )
                tv_address3.setTextColor(
                    ContextCompat.getColor(
                        this@HomeAddressActivity,
                        R.color.colorGrayBasic
                    )
                )
                tv_address1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22F)
                tv_address2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20F)
                tv_address3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20F)
                tv_address1.setTypeface(null, Typeface.BOLD)
                tv_address2.setTypeface(null, Typeface.NORMAL)
                tv_address3.setTypeface(null, Typeface.NORMAL)
                Toast.makeText(this, "1", Toast.LENGTH_SHORT).show()


            }

            R.id.tv_address2 -> {


                replaceFragment(HomeAddressAdapterSelect2Fragment())

                tv_address1.setTextColor(
                    ContextCompat.getColor(
                        this@HomeAddressActivity,
                        R.color.colorGrayBasic
                    )
                )
                tv_address2.setTextColor(
                    ContextCompat.getColor(
                        this@HomeAddressActivity,
                        R.color.colorWhite
                    )
                )
                tv_address3.setTextColor(
                    ContextCompat.getColor(
                        this@HomeAddressActivity,
                        R.color.colorGrayBasic
                    )
                )
                tv_address1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20F)
                tv_address2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22F)
                tv_address3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20F)
                tv_address1.setTypeface(null, Typeface.NORMAL)
                tv_address2.setTypeface(null, Typeface.BOLD)
                tv_address3.setTypeface(null, Typeface.NORMAL)


                Toast.makeText(this, "2", Toast.LENGTH_SHORT).show()


            }

            R.id.tv_address3 -> {

                replaceFragment(HomeAddressAdapterSelect3Fragment())

                tv_address1.setTextColor(
                    ContextCompat.getColor(
                        this@HomeAddressActivity,
                        R.color.colorGrayBasic
                    )
                )
                tv_address2.setTextColor(
                    ContextCompat.getColor(
                        this@HomeAddressActivity,
                        R.color.colorGrayBasic
                    )
                )
                tv_address3.setTextColor(
                    ContextCompat.getColor(
                        this@HomeAddressActivity,
                        R.color.colorWhite
                    )
                )
                tv_address1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20F)
                tv_address2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20F)
                tv_address3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22F)
                tv_address1.setTypeface(null, Typeface.NORMAL)
                tv_address2.setTypeface(null, Typeface.NORMAL)
                tv_address3.setTypeface(null, Typeface.BOLD)
                tv_address3.typeface.isBold


                Toast.makeText(this, "3", Toast.LENGTH_SHORT).show()


            }
        }
    }

    override fun showBackPage() {
        this@HomeAddressActivity.finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.address_main)

        presenter = HomeAddressPresenter(this)
        ib_home_address_back.setOnClickListener(this)


        tv_address1.setOnClickListener(this)
        tv_address2.setOnClickListener(this)
        tv_address3.setOnClickListener(this)



        Toast.makeText(this, "들어왓음", Toast.LENGTH_SHORT).show()

    }


    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(
            R.id.address_container,
            fragment
        ).commit()
    }


    companion object {

        private var select1 = true
        private var select2 = false
        private var select3 = false


    }
}