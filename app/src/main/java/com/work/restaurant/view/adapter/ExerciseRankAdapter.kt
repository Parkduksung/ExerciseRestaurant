package com.work.restaurant.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.work.restaurant.R
import com.work.restaurant.data.model.ExerciseRank
import kotlinx.android.synthetic.main.exercise_item.view.*

class ExerciseRankAdapter : RecyclerView.Adapter<ExerciseRankAdapter.ViewHolder>() {

    private val exerciseArr = ArrayList<ExerciseRank>()


    override fun onCreateViewHolder(holder: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(holder.context).inflate(
                R.layout.exercise_item,
                holder,
                false
            )
        )


    override fun getItemCount(): Int =
        exerciseArr.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val exerciseRank: ExerciseRank = exerciseArr[position]

        holder.run {
            exerciseName.text = exerciseRank.exercise_name
            exercisePrice.text = exerciseRank.exercise_price
            exerciseTime.text = exerciseRank.exercise_time
            exerciseBest.text = exerciseRank.exercise_bestpart
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val exerciseName: TextView = itemView.exercise_name_tv
        val exercisePrice: TextView = itemView.exercise_price_tv
        val exerciseTime: TextView = itemView.exercise_time_tv
        val exerciseBest: TextView = itemView.exercise_best_part_tv


    }

    fun addData(exerciseRank: List<ExerciseRank>) =
        exerciseArr.addAll(exerciseRank)
}