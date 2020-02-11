package com.work.restaurant.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.work.restaurant.R

class AddressAdapter : RecyclerView.Adapter<AddressAdapter.ViewHolder>() {

    private lateinit var adapterListener: AdapterDataListener

    private val addressList = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.address_item,
                parent,
                false
            )
        )

    override fun getItemCount(): Int =
        addressList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(addressList[position])


    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val addressItem: Button = itemView.findViewById(R.id.btn_address_item)

        fun bind(item: String) {

            val address: String = item
            addressItem.text = address

            if (::adapterListener.isInitialized) {
                addressItem.setOnClickListener {
                    adapterListener.getData(address)
                }
            }
        }

    }

    fun removeData() {
        addressList.clear()
    }

    fun addData(item: String?) {
        if (item != null) {
            addressList.add(item)
        }
    }


    fun setItemClickListener(listener: AdapterDataListener) {
        adapterListener = listener
    }


}
