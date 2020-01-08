package com.work.restaurant.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.work.restaurant.R

class AddressAdapter : RecyclerView.Adapter<AddressAdapter.ViewHolder>() {

    private var adapterListener: AdapterDataListener? = null

    private val addressList = ArrayList<String>()

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
        holder.bind(addressList[position], adapterListener)


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val addressItem: Button = itemView.findViewById(R.id.btn_address_item)


        fun bind(item: String, adapterListener: AdapterDataListener?) {

            val address: String = item

//            itemView.setOnClickListener {
//                adapterListener?.getData(address)
//
//            }

            addressItem.text = address



            addressItem.setOnClickListener {
                adapterListener?.getData(address)
            }

        }

    }

    fun removeData(){
        addressList.clear()
    }

    fun addData(item: String?) {
        if (item != null) {
            addressList.add(item)
        }
    }


    fun setItemClickListener(listenerAdapterAdapter: AdapterDataListener) {
        adapterListener = listenerAdapterAdapter
    }

}
