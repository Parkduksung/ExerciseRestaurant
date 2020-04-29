package com.work.restaurant.view.diary.main.adapter

import android.annotation.SuppressLint
import android.view.Gravity
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
import com.work.restaurant.util.DateAndTime
import com.work.restaurant.view.adapter.AdapterDataListener

class DiaryDetailsAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var adapterListener: AdapterDataListener.GetList

    private val diaryList = mutableListOf<DiaryModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            KIND_EAT -> {
                val view =
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.eat_item,
                        parent,
                        false
                    )
                return EatViewHolder(view)
            }
            KIND_EXERCISE -> {
                val view =
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.exercise_item,
                        parent,
                        false
                    )
                return ExerciseViewHolder(view)
            }
            KIND_NO_DATA -> {
                val view =
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.no_diary_item,
                        parent,
                        false
                    )
                return NoDataViewHolder(view)

            }
            else -> throw RuntimeException(UNKNOWN_ERROR_TYPE)
        }
    }

    override fun getItemViewType(position: Int): Int =
        diaryList[position].kind

    override fun getItemCount(): Int =
        diaryList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val obj = diaryList[position]
        when (obj.kind) {
            KIND_EAT -> {
                (holder as EatViewHolder).bind(obj)
            }
            KIND_EXERCISE -> {
                (holder as ExerciseViewHolder).bind(obj)
            }
            KIND_NO_DATA -> {
                (holder as NoDataViewHolder).bind()
            }
        }
    }

    inner class NoDataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val noDataMessage: TextView = itemView.findViewById(R.id.tv_no_data)

        fun bind() {
            noDataMessage.text =
                itemView.resources.getString(R.string.et_calendar_main_context_ok_login_state)
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

            if (::adapterListener.isInitialized) {
                itemView.setOnLongClickListener {
                    adapterListener.getData(item)
                    true
                }
            } else {
                adapterListener = object : AdapterDataListener.GetList {
                    override fun getData(data: DiaryModel) {

                    }
                }
                itemView.setOnLongClickListener {
                    adapterListener.getData(item)
                    true
                }
            }

            var allSetNum = INIT_SET
            addType.text = EXERCISE
            addExerciseType.text = item.type
            addTime.text = DateAndTime.convertShowTime(item.time)
            addName.text = item.exerciseSetName

            when (item.type) {
                itemView.resources.getStringArray(R.array.add_exercise_part)[CHEST] -> {
                    addBtnType.backgroundTintList =
                        ContextCompat.getColorStateList(App.instance.context(), R.color.colorAccent)
                }
                itemView.resources.getStringArray(R.array.add_exercise_part)[BACK] -> {
                    addBtnType.backgroundTintList =
                        ContextCompat.getColorStateList(App.instance.context(), R.color.colorYellow)
                }
                itemView.resources.getStringArray(R.array.add_exercise_part)[SHOULDER] -> {
                    addBtnType.backgroundTintList =
                        ContextCompat.getColorStateList(App.instance.context(), R.color.colorMint)
                }
                itemView.resources.getStringArray(R.array.add_exercise_part)[ARMS] -> {
                    addBtnType.backgroundTintList =
                        ContextCompat.getColorStateList(App.instance.context(), R.color.colorPurple)
                }
                itemView.resources.getStringArray(R.array.add_exercise_part)[LEGS] -> {
                    addBtnType.backgroundTintList =
                        ContextCompat.getColorStateList(App.instance.context(), R.color.colorBlue)
                }
            }

            item.exerciseSet.forEach {
                allSetNum += 1
                val exerciseSetView =
                    LayoutInflater.from(App.instance.context()).inflate(
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

                setNum.text = allSetNum.toString() + "세트"
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

        private val type = listOf(MEAL, SNACK)

        fun bind(item: DiaryModel) {

            if (::adapterListener.isInitialized) {
                addMemo.setOnLongClickListener {
                    adapterListener.getData(item)
                    true
                }
            } else {
                adapterListener = object : AdapterDataListener.GetList {
                    override fun getData(data: DiaryModel) {

                    }

                }
                itemView.setOnLongClickListener {
                    adapterListener.getData(item)
                    true
                }
            }
            addType.text = type[item.type.toInt()]
            addTime.text = DateAndTime.convertShowTime(item.time)

            if (item.memo.contains(ENTER)) {
                addMemo.gravity = Gravity.CENTER_VERTICAL
            } else {
                addMemo.gravity = Gravity.CENTER
            }
            addMemo.text = item.memo

        }
    }

    fun addAllData(diaryModel: List<DiaryModel>) {
        if (diaryModel.isEmpty()) {
            val emptyDiaryModel =
                DiaryModel(
                    EMPTY_NUM,
                    EMPTY_NUM,
                    EMPTY_TEXT,
                    KIND_NO_DATA,
                    EMPTY_TEXT,
                    EMPTY_TEXT,
                    EMPTY_TEXT,
                    EMPTY_TEXT,
                    EMPTY_TEXT,
                    emptyList()
                )
            diaryList.add(emptyDiaryModel)
        } else {
            diaryList.addAll(diaryModel.sortedBy { it.time })
        }
    }

    fun clearListData() {
        diaryList.clear()
        notifyDataSetChanged()
    }

    fun setItemClickListener(listener: AdapterDataListener.GetList) {
        adapterListener = listener
    }

    companion object {

        const val KIND_EAT = 0
        const val KIND_EXERCISE = 1
        private const val KIND_NO_DATA = 2

        private const val INIT_SET = 0

        private const val ENTER = "\n"

        private const val EMPTY_NUM = 0
        private const val EMPTY_TEXT = ""
        private const val SNACK = "간식"
        private const val MEAL = "식사"
        private const val EXERCISE = "운동"
        private const val UNKNOWN_ERROR_TYPE = "알 수 없는 뷰 타입 에러"

        private const val CHEST = 0
        private const val BACK = 1
        private const val SHOULDER = 2
        private const val ARMS = 3
        private const val LEGS = 4

    }

}

