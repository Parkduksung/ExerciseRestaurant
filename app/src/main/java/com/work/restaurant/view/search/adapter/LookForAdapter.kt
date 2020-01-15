package com.work.restaurant.view.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.work.restaurant.R
import com.work.restaurant.network.model.FitnessCenterItemResponse
import com.work.restaurant.util.GlideApp
import com.work.restaurant.view.adapter.AdapterDataListener

class LookForAdapter : RecyclerView.Adapter<LookForAdapter.ViewHolder>() {

    private val searchLookList = ArrayList<FitnessCenterItemResponse>()

    private lateinit var adapterListener: AdapterDataListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.search_look_item,
                parent,
                false
            )
        )

    override fun getItemCount(): Int =
        searchLookList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (::adapterListener.isInitialized) {
            holder.bind(searchLookList[position])
        }
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

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val searchLookName: TextView = itemView.findViewById(R.id.tv_search_look_name)
        private val searchLookImage: ImageView = itemView.findViewById(R.id.iv_search_look_image)

        fun bind(item: FitnessCenterItemResponse) {

            if (::adapterListener.isInitialized) {
                searchLookName.setOnClickListener {
                    adapterListener.getData(item.fitnessCenterName)
                }
            } else {
                adapterListener = object : AdapterDataListener {
                    override fun getData(data: String) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                }
                searchLookName.setOnClickListener {
                    adapterListener.getData(item.fitnessCenterName)
                }
            }

            val fitnessCenterItemResponse: FitnessCenterItemResponse = item

            searchLookName.text = fitnessCenterItemResponse.fitnessCenterName

            GlideApp.with(itemView.context)
                .load(fitnessCenterItemResponse.fitnessCenterImage)
                .override(100, 100)
                .into(searchLookImage)

        }

    }

}