package com.work.restaurant.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.work.restaurant.R
import kotlinx.android.synthetic.main.address_item.view.*

class AddressAdapter : RecyclerView.Adapter<AddressAdapter.ViewHolder>() {

    private var addressAdapterDataListener: AddressAdapterDataListener? = null

    private val addressList = ArrayList<String>()

    override fun onCreateViewHolder(holder: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(holder.context).inflate(
                R.layout.address_item,
                holder,
                false
            )
        )

    override fun getItemCount(): Int =
        addressList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.addressItem.setOnClickListener {
            val context = it.context
            Toast.makeText(context, addressList[position], Toast.LENGTH_LONG).show()
            addressAdapterDataListener?.getAddressData(addressList[position])

        }

        holder.run {
            addressItem.text = addressList[position]
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val addressItem: Button = itemView.btn_address_item

    }

    fun addData(item: String) {
        addressList.add(item)
    }


    fun setItemClickListener(listenerAdapter: AddressAdapterDataListener) {
        addressAdapterDataListener = listenerAdapter
    }

}