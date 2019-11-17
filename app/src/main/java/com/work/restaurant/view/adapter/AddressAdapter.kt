package com.work.restaurant.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.work.restaurant.R
import com.work.restaurant.view.fragment.home.HomeAddressFragment.Companion.addressClick
import kotlinx.android.synthetic.main.address_item.view.*

class AddressAdapter : RecyclerView.Adapter<AddressAdapter.ViewHolder>() {

    private val addressArr = ArrayList<String>()

    override fun onCreateViewHolder(holder: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(holder.context).inflate(
                R.layout.address_item,
                holder,
                false
            )
        )

    override fun getItemCount(): Int =
        addressArr.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.addressItem.setOnClickListener {
            val context = it.context
            Toast.makeText(context, addressArr[position], Toast.LENGTH_LONG).show()
            addressClick = addressArr[position]
        }

        holder.run {
            addressItem.text = addressArr[position]
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val addressItem: Button = itemView.btn_address_item

    }

    fun addData(item: String) {
        addressArr.add(item)
    }


}