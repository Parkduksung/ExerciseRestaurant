package com.work.restaurant.view.diary.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.work.restaurant.R
import com.work.restaurant.data.model.DiaryModel
import com.work.restaurant.util.App
import com.work.restaurant.view.adapter.AdapterDataListener

class DiaryAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private lateinit var adapterListener: AdapterDataListener
    private val diaryList = mutableListOf<DiaryModel>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            DiaryModel.EAT -> {
                val view =
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.eat_item,
                        parent,
                        false
                    )
                return EatViewHolder(view)
            }
            DiaryModel.EXERCISE -> {
                val view =
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.exercise_item,
                        parent,
                        false
                    )
                return ExerciseViewHolder(view)

            }
            else -> throw RuntimeException("알 수 없는 뷰 타입 에러")
        }
    }

    override fun getItemViewType(position: Int): Int =
        diaryList[position].kind


    override fun getItemCount(): Int =
        diaryList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val obj = diaryList[position]
        when (obj.kind) {
            DiaryModel.EAT -> {
                (holder as EatViewHolder).bind(obj)
            }
            DiaryModel.EXERCISE -> {
                (holder as ExerciseViewHolder).bind(obj)
            }
        }
    }

    inner class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val addType: TextView = itemView.findViewById(R.id.add_exercise_type)
        private val addExerciseType: TextView = itemView.findViewById(R.id.tv_add_exercise_type)
        private val addTime: TextView = itemView.findViewById(R.id.add_exercise_time)
        private val addBtnType: Button = itemView.findViewById(R.id.btn_add_exercise_type)
        private val addName: TextView = itemView.findViewById(R.id.tv_add_exercise_name)
        private val addSet: LinearLayout = itemView.findViewById(R.id.ll_add_exercise_set)


        @SuppressLint("ResourceAsColor")
        fun bind(item: DiaryModel) {

            addSet.removeAllViews()

            var i = 0
            addType.text = "운동"
            addExerciseType.text = item.type
            addTime.text = item.time
            addName.text = item.exerciseSetName

            when (item.type) {

                "가슴" -> {
                    addBtnType.backgroundTintList =
                        ContextCompat.getColorStateList(App.instance.context(), R.color.colorAccent)
                }
                "등" -> {
                    addBtnType.backgroundTintList =
                        ContextCompat.getColorStateList(App.instance.context(), R.color.colorYellow)
                }
                "어깨" -> {
                    addBtnType.backgroundTintList =
                        ContextCompat.getColorStateList(App.instance.context(), R.color.colorMint)
                }
                "팔" -> {
                    addBtnType.backgroundTintList =
                        ContextCompat.getColorStateList(App.instance.context(), R.color.colorPurple)
                }
                "다리" -> {
                    addBtnType.backgroundTintList =
                        ContextCompat.getColorStateList(App.instance.context(), R.color.colorBlue)
                }
            }

            item.exerciseSet?.forEach {

                i += 1

                val exerciseSetView = LayoutInflater.from(App.instance.context()).inflate(
                    R.layout.exercise_set_item,
                    null
                )

                exerciseSetView.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    3f
                )


                val setNum: TextView = exerciseSetView.findViewById(R.id.set_num)
                val setKg: TextView = exerciseSetView.findViewById(R.id.set_kg)
                val setCount: TextView = exerciseSetView.findViewById(R.id.set_count)

                setNum.text = i.toString() + "세트"
                setKg.text = it.exerciseSetKg + "Kg"
                setCount.text = it.exerciseSetCount + "회"


                addSet.addView(exerciseSetView)
            }

        }


    }

    inner class EatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val addType: TextView = itemView.findViewById(R.id.add_eat_type)
        private val addTime: TextView = itemView.findViewById(R.id.add_eat_time)
        private val addMemo: TextView = itemView.findViewById(R.id.add_eat_memo)

        private val type = listOf("식사", "간식")
        fun bind(item: DiaryModel) {

            if (::adapterListener.isInitialized) {
                itemView.setOnClickListener {
                    adapterListener.getData(item.date)
                }
            } else {
                adapterListener = object : AdapterDataListener {
                    override fun getData(data: String) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                }
                itemView.setOnClickListener {
                    adapterListener.getData(item.date)
                }
            }

            addType.text = type[item.type.toInt()]
            addTime.text = item.time
            addMemo.text = item.memo

        }
    }


    fun addAllData(diaryModel: List<DiaryModel>) =
        diaryList.addAll(diaryModel.sortedBy { it.time })


    fun addData(diaryModel: DiaryModel) {
        diaryList.add(diaryModel)
        notifyDataSetChanged()
    }

    fun clearListData() {
        diaryList.clear()
        notifyDataSetChanged()
    }

    fun setItemClickListener(listenerAdapterAdapter: AdapterDataListener) {
        adapterListener = listenerAdapterAdapter
    }


}

