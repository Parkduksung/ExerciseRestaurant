package com.work.restaurant.view.home.fragment

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.work.restaurant.R
import com.work.restaurant.view.adapter.AdapterDataListener
import com.work.restaurant.view.adapter.AddressAdapter
import com.work.restaurant.view.home.fragment.HomeAddressFragment.Companion.addressClick
import com.work.restaurant.view.home.fragment.HomeAddressFragment.Companion.selectAddress1
import kotlinx.android.synthetic.main.home_address_select1_fragment.*


class HomeAddressAdapterSelect1Fragment : Fragment(),
    AdapterDataListener {
    override fun getData(data: String) {
        selectAddress1 = data
        addressClick += data


//
//        val dataIntent = Intent()
//        dataIntent.putExtra("address1", data)
//        targetFragment?.onActivityResult(targetRequestCode, Activity.RESULT_OK, dataIntent)
//        this.requireFragmentManager().beginTransaction().commit()


    }


    private lateinit var addressAdapter: AddressAdapter

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
        return inflater.inflate(R.layout.home_address_select1_fragment, container, false).also {
            addressAdapter = AddressAdapter()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "onActivityCreated")
        super.onActivityCreated(savedInstanceState)

        addressAdapter.setItemClickListener(this)
        startView()

    }

    private fun startView() {

        val loadingTextArrayList = resources.getStringArray(R.array.select)


        recyclerview_address1.addItemDecoration(Decoration(20))

        recyclerview_address1.run {
            this.adapter = addressAdapter

            loadingTextArrayList.forEach {
                addressAdapter.addData(it)
            }
            layoutManager = GridLayoutManager(this.context, 3)

        }
    }


    companion object {
        private const val TAG = "HomeAddressSelect1Fragment"
    }


    inner class Decoration(var count: Int) : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            outRect.bottom = count
            outRect.left = count
            outRect.top = count
            outRect.right = count
        }
    }

}
