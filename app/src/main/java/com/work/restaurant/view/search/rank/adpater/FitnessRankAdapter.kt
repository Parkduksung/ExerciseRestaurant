package com.work.restaurant.view.search.rank.adpater

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

class FitnessRankAdapter : RecyclerView.Adapter<FitnessRankAdapter.ViewHolder>() {

    private val fitnessList = ArrayList<FitnessCenterItemResponse>()

    private lateinit var adapterListener: AdapterDataListener


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.fitness_rank_item,
                parent,
                false
            )
        )


    override fun getItemCount(): Int =
        fitnessList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (::adapterListener.isInitialized) {
            holder.bind(fitnessList[position])
        }
    }


    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private val fitnessNo: TextView = itemView.findViewById(R.id.fitness_rank_no_tv)
        private val fitnessName: TextView = itemView.findViewById(R.id.fitness_name_tv)
        private val fitnessImage: ImageView = itemView.findViewById(R.id.fitness_image_iv)

        fun bind(item: FitnessCenterItemResponse) {

            val fitnessCenterItemResponse: FitnessCenterItemResponse = item

            if (::adapterListener.isInitialized) {
                itemView.setOnClickListener {
                    adapterListener.getData(item.fitnessCenterName)
                }
            } else {
                adapterListener = object : AdapterDataListener {
                    override fun getData(data: String) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                }
                itemView.setOnClickListener {
                    adapterListener.getData(item.fitnessCenterName)
                }
            }


            fitnessNo.text = fitnessCenterItemResponse.fitnessCenterNo.toString()
            fitnessName.text = fitnessCenterItemResponse.fitnessCenterName

            GlideApp.with(itemView.context)
                .load(fitnessCenterItemResponse.fitnessCenterImage)
                .override(100, 100)
                .into(fitnessImage)

        }


    }

    fun addData(fitnessCenterItemResponse: List<FitnessCenterItemResponse>) =
        fitnessList.addAll(fitnessCenterItemResponse)


    fun clearListData() {
        fitnessList.clear()
        notifyDataSetChanged()
    }


    fun setItemClickListener(listenerAdapterAdapter: AdapterDataListener) {
        adapterListener = listenerAdapterAdapter
    }
}

