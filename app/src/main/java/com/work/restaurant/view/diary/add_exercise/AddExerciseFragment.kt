package com.work.restaurant.view.diary.add_exercise

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.view.menu.MenuPopupHelper
import com.work.restaurant.Injection
import com.work.restaurant.R
import com.work.restaurant.data.model.ExerciseSet
import com.work.restaurant.util.App
import com.work.restaurant.view.base.BaseFragment
import com.work.restaurant.view.diary.add_exercise.adapter.ExerciseAdapter
import com.work.restaurant.view.diary.add_exercise.presenter.AddExerciseContract
import com.work.restaurant.view.diary.add_exercise.presenter.AddExercisePresenter
import kotlinx.android.synthetic.main.diary_add_exercise.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class AddExerciseFragment : BaseFragment(R.layout.diary_add_exercise),
    View.OnClickListener, AddExerciseContract.View {
    override fun showAddResult(msg: String) {
        Log.d("저장결과11", msg)
    }

    private lateinit var viewList: ArrayList<View>

    private lateinit var presenter: AddExercisePresenter

    private lateinit var exerciseAdapter: ExerciseAdapter


    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.iv_add_exercise -> {

                val addExerciseItem = layoutInflater.inflate(R.layout.add_exercise_item, null)

                viewList.add(addExerciseItem)

                ll_add_remove_exercise.addView(addExerciseItem)
            }


            R.id.iv_remove_exercise -> {

                if (viewList.size != 0) {

                    ll_add_remove_exercise.removeView(viewList[viewList.size - 1])
                    viewList.removeAt(viewList.size - 1)
                }

            }

            R.id.btn_add_exercise_category -> {
                getMenuClick()
            }


            R.id.btn_add_exercise_time -> {
                getTimePicker()
            }

            R.id.add_exercise_cancel -> {
                requireFragmentManager().beginTransaction()
                    .remove(this@AddExerciseFragment)
                    .commit()
            }

            R.id.add_exercise_save -> {


                if (btn_add_exercise_category.text != null && viewList.isNotEmpty()) {

                    viewList.forEach {

                        val addExerciseName: EditText =
                            it.findViewById(R.id.et_add_exercise_name)
                        val addExerciseKg: EditText =
                            it.findViewById(R.id.et_add_exercise_kg)
                        val addExerciseCount: EditText =
                            it.findViewById(R.id.et_add_exercise_count)

                        val exerciseSet = ExerciseSet(
                            addExerciseName.text.toString(),
                            addExerciseKg.text.toString(),
                            addExerciseCount.text.toString()
                        )

                        if (addExerciseName.text.toString() != "" && addExerciseKg.text.toString() != "" && addExerciseCount.text.toString() != "") {
                            presenter.addExercise(
                                tv_add_exercise_today.text.toString(),
                                btn_add_exercise_time.text.toString(),
                                btn_add_exercise_category.text.toString(),
                                exerciseSet
                            )
                        }

                    }

                } else {
                    Toast.makeText(this.context, "저장할 수 없습니다.", Toast.LENGTH_SHORT).show()
                }
            }


        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exerciseAdapter = ExerciseAdapter()
        viewList = ArrayList()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = AddExercisePresenter(
            this,
            Injection.provideExerciseRepository()
        )
        init()

        iv_add_exercise.setOnClickListener(this)
        iv_remove_exercise.setOnClickListener(this)
        btn_add_exercise_category.setOnClickListener(this)
        btn_add_exercise_time.setOnClickListener(this)
        add_exercise_cancel.setOnClickListener(this)
        add_exercise_save.setOnClickListener(this)


    }


    private fun init() {

        val currentTime = Calendar.getInstance().time

        val dateTextAll =
            SimpleDateFormat("yyyy-MM-dd-a-hh-mm", Locale.getDefault()).format(currentTime)

        val dateArray = dateTextAll.split("-")

        tv_add_exercise_today.text =
            getString(R.string.current_date, dateArray[0], dateArray[1], dateArray[2])
        btn_add_exercise_time.text =
            getString(R.string.current_time, dateArray[3], dateArray[4], dateArray[5])


    }

    private fun getTimePicker() {
        val dialogView = View.inflate(context, R.layout.time_picker, null)

        val timePicker = dialogView.findViewById<TimePicker>(R.id.time_picker)

        val alertDialog =
            android.app.AlertDialog.Builder(
                ContextThemeWrapper(
                    activity,
                    R.style.Theme_AppCompat_Light_Dialog
                )
            )

        alertDialog.setView(dialogView)
            .setPositiveButton("변경") { _, _ ->

                val changedTime =
                    "${getAmPm(timePicker.hour)} ${timePicker.minute}분"

                btn_add_exercise_time.text = changedTime

            }
            .setNegativeButton("취소") { _, _ ->

            }
            .show()

    }

    private fun getAmPm(hour: Int): String {
        return if (hour > 12) {
            "오후 ${hour - 12}시"
        } else {
            if (hour == 0) {
                "오전 ${hour + 12}시"
            } else {
                "오전 ${hour}시"
            }
        }
    }


    @SuppressLint("RtlHardcoded")
    private fun getMenuClick() {

        val menuBuilder = MenuBuilder(this.context)
        val inflater = MenuInflater(this.context)
        inflater.inflate(R.menu.exercise_menu, menuBuilder)
        val optionMenu =
            MenuPopupHelper(this.requireContext(), menuBuilder, btn_add_exercise_category)
        optionMenu.setForceShowIcon(true)

        optionMenu.gravity = Gravity.LEFT
        menuBuilder.setCallback(object : MenuBuilder.Callback {
            override fun onMenuModeChange(menu: MenuBuilder?) {

            }

            override fun onMenuItemSelected(menu: MenuBuilder?, item: MenuItem?): Boolean {

                when (item?.itemId) {

                    R.id.chest -> {
                        btn_add_exercise_category.text = "가슴"
                        Toast.makeText(App.instance.context(), "chest", Toast.LENGTH_SHORT)
                            .show()
                    }
                    R.id.back -> {
                        btn_add_exercise_category.text = "등"
                        Toast.makeText(App.instance.context(), "back", Toast.LENGTH_SHORT)
                            .show()

                    }
                    R.id.shoulder -> {
                        btn_add_exercise_category.text = "어깨"
                        Toast.makeText(App.instance.context(), "shoulder", Toast.LENGTH_SHORT)
                            .show()

                    }
                    R.id.legs -> {
                        btn_add_exercise_category.text = "다리"
                        Toast.makeText(App.instance.context(), "legs", Toast.LENGTH_SHORT)
                            .show()

                    }
                    R.id.arm -> {
                        btn_add_exercise_category.text = "팔"
                        Toast.makeText(App.instance.context(), "arm", Toast.LENGTH_SHORT).show()

                    }
                    else -> {
                        btn_add_exercise_category.text = "종목은 선택하세요."
                    }

                }
                return true
            }
        })

        optionMenu.show()
    }


    companion object {
        private const val TAG = "CalendarFragment"

    }


    override fun onResume() {
        super.onResume()

        if (::viewList.isInitialized) {
            Log.d("결곸", viewList.size.toString())
        }
    }

}