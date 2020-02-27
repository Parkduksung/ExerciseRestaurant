package com.work.restaurant.view.home.address_select_all

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.work.restaurant.R
import com.work.restaurant.util.App
import com.work.restaurant.view.ExerciseRestaurantActivity.Companion.selectAll
import com.work.restaurant.view.base.BaseFragment
import kotlinx.android.synthetic.main.home_address_selcet_all_fragment.*


class HomeAddressSelectAllFragment : BaseFragment(R.layout.home_address_selcet_all_fragment),
    View.OnClickListener {


    private lateinit var addressAllDataListener: AddressAllDataListener


    interface AddressAllDataListener {
        fun sendData(data: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (activity != null && activity is AddressAllDataListener) {
            addressAllDataListener = activity as AddressAllDataListener
        }
    }


    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.btn_address_change_no -> {
                requireFragmentManager().beginTransaction().remove(this).commit()
            }

            R.id.btn_address_change_ok -> {

                if (::addressAllDataListener.isInitialized) {
                    val bundle = arguments
                    bundle?.let {
                        it.getString(ADDRESS)?.let { addressAll ->
                            addressAllDataListener.sendData(addressAll)
                            requireFragmentManager().popBackStack()
                            selectAll = tv_address_select.text.toString()
                        }
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_address_selcet_all_fragment, container, false).also {
            it?.setBackgroundColor(
                ContextCompat.getColor(
                    App.instance.context(),
                    R.color.transparent
                )
            )
            it?.setOnTouchListener { _, _ ->
                true
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_address_change_no.setOnClickListener(this)
        btn_address_change_ok.setOnClickListener(this)
        showSelectAddress()
    }

    private fun showSelectAddress() {
        val bundle = arguments
        if (bundle != null) {
            tv_address_select.text = bundle.getString(ADDRESS)
        }
    }


    companion object {
        private const val TAG = "HomeAddressSelectAllFragment"

        const val ADDRESS = "Address"

        fun newInstance(selectAddress: String) =
            HomeAddressSelectAllFragment().apply {
                arguments = Bundle().apply {
                    putString(ADDRESS, selectAddress)
                }
            }

    }

}
