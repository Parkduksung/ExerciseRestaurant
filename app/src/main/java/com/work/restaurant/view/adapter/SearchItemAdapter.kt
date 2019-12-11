package com.work.restaurant.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.work.restaurant.R
import com.work.restaurant.network.model.FitnessCenterItemResponse
import com.work.restaurant.view.GlideApp
import kotlinx.android.synthetic.main.search_look_item.view.*

class SearchItemAdapter : RecyclerView.Adapter<SearchItemAdapter.ViewHolder>() {

    private val searchLookList = ArrayList<FitnessCenterItemResponse>()

    private var adapterListener: AdapterDataListener? = null

    override fun onCreateViewHolder(holder: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(holder.context).inflate(
                R.layout.search_look_item,
                holder,
                false
            )
        )


    override fun getItemCount(): Int =
        searchLookList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.searchLookName.setOnClickListener {

            adapterListener?.getData(searchLookList[position].fitnessCenterName)

        }

        val fitnessCenterItemResponse: FitnessCenterItemResponse = searchLookList[position]

        holder.run {
            searchLookName.text = fitnessCenterItemResponse.fitnessCenterName

            GlideApp.with(holder.itemView.context)
                .load(fitnessCenterItemResponse.fitnessCenterImage)
                .override(100, 100)
                .into(holder.searchLookImage)

        }


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val searchLookName: TextView = itemView.tv_search_look_name
        val searchLookImage: ImageView = itemView.iv_search_look_image

    }

    fun addAllData(fitnessCenterItemResponse: List<FitnessCenterItemResponse>) =
        searchLookList.addAll(fitnessCenterItemResponse)


    fun addData(fitnessCenterItemResponse: FitnessCenterItemResponse) {
        searchLookList.add(fitnessCenterItemResponse)
    }

    fun clearListData() {
        searchLookList.clear()
        notifyDataSetChanged()
    }

    fun setItemClickListener(listenerAdapterAdapter: AdapterDataListener) {
        adapterListener = listenerAdapterAdapter
    }

}