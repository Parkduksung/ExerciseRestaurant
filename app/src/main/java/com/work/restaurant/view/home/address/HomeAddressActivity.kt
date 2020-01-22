package com.work.restaurant.view.home.address

import android.graphics.Typeface
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.work.restaurant.R
import com.work.restaurant.data.repository.road.RoadRepositoryImpl
import com.work.restaurant.data.source.local.road.RoadLocalDataSourceImpl
import com.work.restaurant.network.room.database.AddressDatabase
import com.work.restaurant.util.App
import com.work.restaurant.util.AppExecutors
import com.work.restaurant.util.Decoration
import com.work.restaurant.view.adapter.AdapterDataListener
import com.work.restaurant.view.adapter.AddressAdapter
import com.work.restaurant.view.home.address.presenter.HomeAddressContract
import com.work.restaurant.view.home.address.presenter.HomeAddressPresenter
import com.work.restaurant.view.home.address_select_all.HomeAddressSelectAllFragment
import kotlinx.android.synthetic.main.address_main.*


class HomeAddressActivity : AppCompatActivity(),
    HomeAddressContract.View, View.OnClickListener,
    AdapterDataListener {
    override fun showRoadItem(address: TextView, list: List<String>) {

        select(address, list.toTypedArray())

    }

    override fun getData(data: String) {

        Toast.makeText(this, data, Toast.LENGTH_SHORT).show()

        if (address1 && !address2 && !address3) {
            si = data
            address2 = true
            presenter.getRoadItem(
                tv_address2, data,
                si, "gunGu"
            )
            unSelect(tv_address1)
            unSelect(tv_address3)
        } else if (address1 && address2 && !address3) {
            gunGu = data
            address3 = true
            presenter.getRoadItem(
                tv_address3, data,
                si, "dong"
            )
            unSelect(tv_address1)
            unSelect(tv_address2)
        } else if (address1 && address2 && address3) {
            dong = data
            selectAddress = "$si $gunGu $dong"
            val homeAddressSelectAllFragment = HomeAddressSelectAllFragment.newInstance(
                selectAddress
            )
            this.supportFragmentManager.beginTransaction()
                .replace(R.id.address_main_container, homeAddressSelectAllFragment).commit()
        }
    }

    private lateinit var presenter: HomeAddressContract.Presenter
    private lateinit var addressAdapter: AddressAdapter

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.ib_home_address_back -> {
                presenter.backPage()
            }

            R.id.tv_address1 -> {

                address2 = false
                address3 = false

                select(tv_address1, resources.getStringArray(R.array.select))
                unSelect(tv_address2)
                unSelect(tv_address3)

                Toast.makeText(this, "1", Toast.LENGTH_SHORT).show()

            }

            R.id.tv_address2 -> {

                address3 = false

                if (address2) {

                    //
                    //이 부분은 만약 <시>를 선택해서 <군구>로 넘어왔는데 사용자가 <군구>를 다시 눌렀을때 다른게 안뜨는 것 방지.
//                    select(tv_address2, resources.getStringArray(R.array.인천))
                    //
                    //
                    presenter.getRoadItem(
                        tv_address2,
                        si,
                        si, "gunGu"
                    )
                    unSelect(tv_address1)
                    unSelect(tv_address3)
                }

                Toast.makeText(this, "2", Toast.LENGTH_SHORT).show()

            }

            R.id.tv_address3 -> {

                if (address3) {
                    presenter.getRoadItem(
                        tv_address2,
                        dong,
                        si, "gunGu"
                    )
                    unSelect(tv_address1)
                    unSelect(tv_address2)
                }

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



        presenter = HomeAddressPresenter(
            this, RoadRepositoryImpl.getInstance(
                RoadLocalDataSourceImpl.getInstance(
                    AddressDatabase.getInstance(
                        App.instance.context()
                    ),
                    AppExecutors()
                )
            )
        )
        addressAdapter = AddressAdapter()
        addressAdapter.setItemClickListener(this)
        ib_home_address_back.setOnClickListener(this)
        tv_address1.setOnClickListener(this)
        tv_address2.setOnClickListener(this)
        tv_address3.setOnClickListener(this)

        initView()


    }

    private fun initView() {

        tv_address1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22F)
        address1 = true
        address2 = false
        address3 = false

        val loadingTextArrayList = resources.getStringArray(R.array.select)
        val decoration = Decoration(30, 30, 30, 30)

        recyclerview_address.run {
            this.adapter = addressAdapter
            this.addItemDecoration(decoration)
            loadingTextArrayList.forEach {
                addressAdapter.addData(it)
            }
            layoutManager = GridLayoutManager(this.context, 3)
        }

    }

    private fun select(address: TextView, loadingTextArrayList: Array<String?>) {

        addressAdapter.removeData()
        loadingTextArrayList.forEach {
            addressAdapter.addData(it)
        }
        recyclerview_address.adapter?.notifyDataSetChanged()

        address.run {
            this.setTextColor(
                ContextCompat.getColor(
                    this@HomeAddressActivity,
                    R.color.colorWhite
                )
            )
            this.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22F)
            this.setTypeface(null, Typeface.BOLD)
        }

    }

    private fun unSelect(address: TextView) {
        address.run {
            this.setTextColor(
                ContextCompat.getColor(
                    this@HomeAddressActivity,
                    R.color.colorGrayBasic
                )
            )
            this.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20F)
            this.setTypeface(null, Typeface.NORMAL)
        }
    }


    companion object {

        var address1 = false
        var address2 = false
        var address3 = false

        var si = ""
        var gunGu = ""
        var dong = ""
        var selectAddress = ""

    }
}