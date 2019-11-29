package com.work.restaurant.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.work.restaurant.R
import com.work.restaurant.data.model.FitnessCenterItem
import com.work.restaurant.view.GlideApp
import kotlinx.android.synthetic.main.fitness_rank_item.view.*

class FitnessRankAdapter : RecyclerView.Adapter<FitnessRankAdapter.ViewHolder>() {

    private val fitnessList = ArrayList<FitnessCenterItem>()


    override fun onCreateViewHolder(holder: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(holder.context).inflate(
                R.layout.fitness_rank_item,
                holder,
                false
            )
        )


    override fun getItemCount(): Int =
        fitnessList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        val fitnessCenterItem: FitnessCenterItem = fitnessList[position]

        holder.run {
            fitnessNo.text = fitnessCenterItem.fitnessCenterNo.toString()
            fitnessName.text = fitnessCenterItem.fitnessCenterName

            GlideApp.with(holder.itemView.context)
                .load(fitnessCenterItem.fitnessCenterImage)
                .override(100, 100)
                .into(holder.fitnessImage)

        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val fitnessNo: TextView = itemView.fitness_rank_no_tv
        val fitnessName: TextView = itemView.fitness_name_tv
        val fitnessImage: ImageView = itemView.fitness_image_iv

    }

    fun addData(fitnessCenterItem: List<FitnessCenterItem>) =
        fitnessList.addAll(fitnessCenterItem)


    fun clearListData() {
        fitnessList.clear()
        notifyDataSetChanged()
    }

}