package com.work.restaurant.view.home.address

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.work.restaurant.R
import com.work.restaurant.util.Decoration
import com.work.restaurant.view.ExerciseRestaurantActivity
import com.work.restaurant.view.adapter.AdapterDataListener
import com.work.restaurant.view.adapter.AddressAdapter
import com.work.restaurant.view.base.BaseActivity
import com.work.restaurant.view.home.address.presenter.HomeAddressContract
import com.work.restaurant.view.home.address_select_all.HomeAddressSelectAllFragment
import kotlinx.android.synthetic.main.address_main.*
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf


class HomeAddressActivity : BaseActivity(R.layout.address_main),
    HomeAddressContract.View, View.OnClickListener,
    AdapterDataListener,
    HomeAddressSelectAllFragment.AddressAllDataListener {

    private lateinit var presenter: HomeAddressContract.Presenter
    private lateinit var addressAdapter: AddressAdapter

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.ib_home_address_back -> {
                this@HomeAddressActivity.finish()
            }

            R.id.tv_address1 -> {

                address2 = false
                address3 = false

                select(tv_address1, resources.getStringArray(R.array.select))
                unSelect(tv_address2)
                unSelect(tv_address3)

                tv_address1.text = getString(R.string.address_select1)
                tv_address2.text = getString(R.string.address_select2)
                tv_address3.text = getString(R.string.address_select3)

            }

            R.id.tv_address2 -> {

                address3 = false

                tv_address2.text = getString(R.string.address_select2)
                tv_address3.text = getString(R.string.address_select3)

                if (address2) {
                    presenter.getRoadItem(
                        tv_address2,
                        si,
                        si, GUN_GU
                    )
                    unSelect(tv_address1)
                    unSelect(tv_address3)
                }

            }

            R.id.tv_address3 -> {
                if (address3) {
                    presenter.getRoadItem(
                        tv_address2,
                        dong,
                        si, GUN_GU
                    )
                    unSelect(tv_address1)
                    unSelect(tv_address2)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = get { parametersOf(this) }
        addressAdapter = AddressAdapter()
        addressAdapter.setItemClickListener(this)
        ib_home_address_back.setOnClickListener(this)
        tv_address1.setOnClickListener(this)
        tv_address2.setOnClickListener(this)
        tv_address3.setOnClickListener(this)

        startView()

    }

    private fun startView() {

        tv_address1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, SELECT_FONT_SIZE)
        address1 = true
        address2 = false
        address3 = false

        unSelect(tv_address2)
        unSelect(tv_address3)

        val loadingTextArrayList =
            resources.getStringArray(R.array.select)
        val decoration =
            Decoration(DECORATION_LEFT, DECORATION_RIGHT, DECORATION_TOP, DECORATION_BOTTOM)

        rv_address.run {
            this.adapter = addressAdapter
            this.addItemDecoration(decoration)
            loadingTextArrayList.forEach {
                addressAdapter.addData(it)
            }
            layoutManager = GridLayoutManager(this.context, SPAN_COUNT)
        }

    }

    override fun sendData(data: String) {

        val addressAllIntent =
            Intent(this@HomeAddressActivity, ExerciseRestaurantActivity::class.java).apply {
                putExtra(ADDRESS, data)
            }
        setResult(RESULT_OK, addressAllIntent)
        finish()
    }

    override fun showRoadItem(address: TextView, list: List<String>) {
        select(address, list.toTypedArray())
    }

    override fun getData(data: String) {
        getAreaItem(data)
    }

    private fun getAreaItem(clickData: String) {
        if (address1 && !address2 && !address3) {
            si = clickData
            tv_address1.text = clickData
            address2 = true
            presenter.getRoadItem(
                tv_address2, clickData,
                si, GUN_GU
            )
            unSelect(tv_address1)
            unSelect(tv_address3)
        } else if (address1 && address2 && !address3) {
            gunGu = clickData
            tv_address2.text = clickData
            address3 = true
            presenter.getRoadItem(
                tv_address3, clickData,
                si, DONG
            )
            unSelect(tv_address1)
            unSelect(tv_address2)
        } else if (address1 && address2 && address3) {
            dong = clickData
            tv_address3.text = clickData

            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.address_main_container, HomeAddressSelectAllFragment.newInstance(
                        "$si $gunGu $dong"
                    )
                )
                .addToBackStack(null)
                .commit()
        }
    }

    private fun select(address: TextView, loadingTextArrayList: Array<String?>) {

        addressAdapter.removeData()
        loadingTextArrayList.forEach {
            addressAdapter.addData(it)
        }
        rv_address.adapter?.notifyDataSetChanged()

        address.run {
            this.setTextColor(
                ContextCompat.getColor(
                    this@HomeAddressActivity,
                    R.color.colorWhite
                )
            )
            this.setTextSize(TypedValue.COMPLEX_UNIT_DIP, SELECT_FONT_SIZE)
            this.setTypeface(null, Typeface.BOLD)
        }

    }

    private fun unSelect(address: TextView) {
        address.apply {
            setTextColor(
                ContextCompat.getColor(
                    this@HomeAddressActivity,
                    R.color.colorGrayBasic
                )
            )
            setTextSize(TypedValue.COMPLEX_UNIT_DIP, UN_SELECT_FONT_SIZE)
            setTypeface(null, Typeface.NORMAL)
        }
    }

    companion object {

        private const val GUN_GU = "gunGu"
        private const val DONG = "dong"
        private const val SPAN_COUNT = 3

        private const val SELECT_FONT_SIZE = 22F
        private const val UN_SELECT_FONT_SIZE = 20F

        private const val DECORATION_LEFT = 30
        private const val DECORATION_RIGHT = 30
        private const val DECORATION_TOP = 30
        private const val DECORATION_BOTTOM = 30

        var address1 = false
        var address2 = false
        var address3 = false

        var si = ""
        var gunGu = ""
        var dong = ""

        const val ADDRESS = "address"


    }
}