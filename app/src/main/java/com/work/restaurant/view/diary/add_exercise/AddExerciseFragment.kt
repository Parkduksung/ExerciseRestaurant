package com.work.restaurant.view.diary.add_exercise

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.view.menu.MenuPopupHelper
import com.work.restaurant.Injection
import com.work.restaurant.R
import com.work.restaurant.data.model.ExerciseSet
import com.work.restaurant.util.App
import com.work.restaurant.util.DateAndTime
import com.work.restaurant.view.base.BaseDialogFragment
import com.work.restaurant.view.diary.add_exercise.presenter.AddExerciseContract
import com.work.restaurant.view.diary.add_exercise.presenter.AddExercisePresenter
import kotlinx.android.synthetic.main.diary_add_exercise.*


class AddExerciseFragment : BaseDialogFragment(R.layout.diary_add_exercise),
    View.OnClickListener, AddExerciseContract.View {

    private lateinit var viewList: ArrayList<View>

    private lateinit var presenter: AddExercisePresenter

    override fun showAddSuccess() {

        targetFragment?.onActivityResult(
            targetRequestCode,
            Activity.RESULT_OK,
            null
        )
        dismiss()
        Toast.makeText(this.context, "저장되었습니다.", Toast.LENGTH_SHORT).show()
    }

    private fun hideKeyboard(editText: EditText) {
        val inputMethodManager =
            this.activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(editText.windowToken, 0)
    }

    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.iv_add_exercise -> {

                hideKeyboard(et_add_exercise_name)

                val addExerciseItem = layoutInflater.inflate(R.layout.add_exercise_item, null)

                viewList.add(addExerciseItem)

                ll_add_remove_exercise.addView(addExerciseItem)
            }

            R.id.iv_remove_exercise -> {

                hideKeyboard(et_add_exercise_name)

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
                dismiss()
            }

            R.id.add_exercise_save -> {

                if (App.prefs.login_state && App.prefs.login_state_id.isNotEmpty()) {

                    if (btn_add_exercise_category.text.isNotEmpty() && viewList.isNotEmpty() && et_add_exercise_name.text != null) {
                        val setList = mutableListOf<ExerciseSet>()
                        viewList.forEach {
                            val addExerciseKg: EditText =
                                it.findViewById(R.id.et_add_exercise_kg)
                            val addExerciseCount: EditText =
                                it.findViewById(R.id.et_add_exercise_count)

                            if (addExerciseKg.text.toString().isNotEmpty() && addExerciseCount.text.toString().isNotEmpty()) {
                                val exerciseSet = ExerciseSet(
                                    addExerciseKg.text.toString(),
                                    addExerciseCount.text.toString()
                                )
                                setList.add(exerciseSet)
                            }
                        }

                        if (setList.isNotEmpty()) {
                            presenter.addExercise(
                                App.prefs.login_state_id,
                                tv_add_exercise_today.text.toString(),
                                DateAndTime.convertSaveTime(btn_add_exercise_time.text.toString()),
                                btn_add_exercise_category.text.toString(),
                                et_add_exercise_name.text.toString(),
                                setList
                            )
                        } else {
                            Toast.makeText(this.context, "추가하려는 무게와 횟수를 입력하세요.", Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else {
                        Toast.makeText(this.context, "저장할 수 없습니다.", Toast.LENGTH_SHORT).show()
                    }

                } else {
                    Toast.makeText(this.context, "로그아웃시에는 기록을 저장할 수 없습니다.", Toast.LENGTH_SHORT)
                        .show()
                }


            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        tv_add_exercise_today.text =
            DateAndTime.currentDate()
        btn_add_exercise_time.text =
            DateAndTime.convertShowTime(DateAndTime.currentTime())
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

                btn_add_exercise_time.text =
                    DateAndTime.convertPickerTime(timePicker.hour, timePicker.minute)

            }
            .setNegativeButton("취소") { _, _ ->

            }
            .show()
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
                }
                return true
            }
        })

        optionMenu.show()
    }

    companion object {
        const val TAG = "AddExerciseFragment"
    }


}