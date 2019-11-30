package com.work.restaurant.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.work.restaurant.R
import com.work.restaurant.network.model.FitnessCenterItemModel
import com.work.restaurant.view.GlideApp
import kotlinx.android.synthetic.main.fitness_rank_item.view.*

class FitnessRankAdapter : RecyclerView.Adapter<FitnessRankAdapter.ViewHolder>() {

    private val fitnessList = ArrayList<FitnessCenterItemModel>()


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


        val fitnessCenterItemModel: FitnessCenterItemModel = fitnessList[position]

        holder.run {
            fitnessNo.text = fitnessCenterItemModel.fitnessCenterNo.toString()
            fitnessName.text = fitnessCenterItemModel.fitnessCenterName

            GlideApp.with(holder.itemView.context)
                .load(fitnessCenterItemModel.fitnessCenterImage)
                .override(100, 100)
                .into(holder.fitnessImage)


        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val fitnessNo: TextView = itemView.fitness_rank_no_tv
        val fitnessName: TextView = itemView.fitness_name_tv
        val fitnessImage: ImageView = itemView.fitness_image_iv

    }

    fun addData(fitnessCenterItemModel: List<FitnessCenterItemModel>) =
        fitnessList.addAll(fitnessCenterItemModel)


    fun clearListData() {
        fitnessList.clear()
        notifyDataSetChanged()
    }

}