package com.work.restaurant.view.diary.add_exercise.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.work.restaurant.R
import com.work.restaurant.data.model.ExerciseSet
import com.work.restaurant.view.adapter.AdapterDataListener

class ExerciseAdapter : RecyclerView.Adapter<ExerciseAdapter.ViewHolder>() {

    private lateinit var adapterListener: AdapterDataListener
    private val addExerciseSet = ArrayList<ExerciseSet>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.add_exercise_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (::adapterListener.isInitialized) {
            holder.bind(addExerciseSet[position])
        }
    }

    override fun getItemCount(): Int =
        addExerciseSet.size

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        //        private val type = listOf("식사", "간식")
//
//        private val addExerciseName: EditText = itemView.findViewById(R.id.te_add_exercise_name)
//        private val addExerciseKg: EditText = itemView.findViewById(R.id.te_add_exercise_kg)
//        private val addExerciseCount: EditText = itemView.findViewById(R.id.te_add_exercise_count)

        fun bind(item: ExerciseSet) {

            if (::adapterListener.isInitialized) {
                itemView.setOnClickListener {

                }
            } else {
                adapterListener = object : AdapterDataListener {
                    override fun getData(data: String) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                }
                itemView.setOnClickListener {

                }
            }

//            addExerciseName.addTextChangedListener(object : TextWatcher {
//                override fun afterTextChanged(s: Editable?) {
//                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//                }
//
//                override fun beforeTextChanged(
//                    s: CharSequence?,
//                    start: Int,
//                    count: Int,
//                    after: Int
//                ) {
//
//                }
//
//                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//                }
//
//            })
//
//            addExerciseKg.addTextChangedListener(object : TextWatcher {
//                override fun afterTextChanged(s: Editable?) {
//                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//                }
//
//                override fun beforeTextChanged(
//                    s: CharSequence?,
//                    start: Int,
//                    count: Int,
//                    after: Int
//                ) {
//                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//                }
//
//                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//                }
//
//            })
//
//            addExerciseCount.addTextChangedListener(object : TextWatcher {
//                override fun afterTextChanged(s: Editable?) {
//                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//                }
//
//                override fun beforeTextChanged(
//                    s: CharSequence?,
//                    start: Int,
//                    count: Int,
//                    after: Int
//                ) {
//                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//                }
//
//                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//                }
//
//            })
//            addExerciseName.text.= item.exerciseSetName
//            addExerciseKg.text = item.time
//            addExerciseCount.text = item.memo
//
//
//
//            val dateModel: DateModel = item
//
//            addType.text = type[dateModel.type]
//            addTime.text = dateModel.time
//            addMemo.text = dateModel.memo

        }
    }


    fun addAllData(exerciseSet: List<ExerciseSet>) =
        addExerciseSet.addAll(exerciseSet)

    fun addData(exerciseSet: ExerciseSet) {
        addExerciseSet.add(exerciseSet)
        notifyDataSetChanged()
    }


    fun clearListData() {
        addExerciseSet.clear()
        notifyDataSetChanged()
    }

    fun setItemClickListener(listenerAdapterAdapter: AdapterDataListener) {
        adapterListener = listenerAdapterAdapter
    }

}

