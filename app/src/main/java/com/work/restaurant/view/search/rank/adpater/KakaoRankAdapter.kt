package com.work.restaurant.view.search.rank.adpater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.work.restaurant.R
import com.work.restaurant.network.model.kakaoSearch.KakaoSearchDocuments
import com.work.restaurant.view.adapter.AdapterDataListener

class KakaoRankAdapter : RecyclerView.Adapter<KakaoRankAdapter.ViewHolder>() {

    private val kakaoList = ArrayList<KakaoSearchDocuments>()

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
        kakaoList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (::adapterListener.isInitialized) {
            holder.bind(kakaoList[position])
        }
    }


    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private val fitnessNo: TextView = itemView.findViewById(R.id.fitness_rank_no_tv)
        private val fitnessName: TextView = itemView.findViewById(R.id.fitness_name_tv)

        fun bind(item: KakaoSearchDocuments) {

            val fitnessCenterItemResponse: KakaoSearchDocuments = item

            if (::adapterListener.isInitialized) {
                itemView.setOnClickListener {
                    adapterListener.getData(item.placeName)
                }
            } else {
                adapterListener = object : AdapterDataListener {
                    override fun getData(data: String) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                }
                itemView.setOnClickListener {
                    adapterListener.getData(item.placeName)
                }
            }

            fitnessNo.text = fitnessCenterItemResponse.distance
            fitnessName.text = fitnessCenterItemResponse.placeName


        }


    }

    fun addData(documents: List<KakaoSearchDocuments>) =
        kakaoList.addAll(documents)


    fun clearListData() {
        kakaoList.clear()
        notifyDataSetChanged()
    }


    fun setItemClickListener(listenerAdapterAdapter: AdapterDataListener) {
        adapterListener = listenerAdapterAdapter
    }
}

