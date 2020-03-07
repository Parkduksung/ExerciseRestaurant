package com.work.restaurant.view.diary.update_or_delete_exercise

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
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
import com.work.restaurant.data.model.ExerciseModel
import com.work.restaurant.data.model.ExerciseSet
import com.work.restaurant.util.App
import com.work.restaurant.util.DateAndTime
import com.work.restaurant.view.base.BaseDialogFragment
import com.work.restaurant.view.diary.update_or_delete_exercise.presenter.UpdateOrDeleteExerciseContract
import com.work.restaurant.view.diary.update_or_delete_exercise.presenter.UpdateOrDeleteExercisePresenter
import kotlinx.android.synthetic.main.diary_update_or_delete_exercise.*

class UpdateOrDeleteExerciseFragment : BaseDialogFragment(R.layout.diary_update_or_delete_exercise),
    View.OnClickListener, UpdateOrDeleteExerciseContract.View {

    private lateinit var viewList: ArrayList<View>

    private lateinit var presenter: UpdateOrDeleteExercisePresenter

    override fun showResult(sort: Int) {

        when (sort) {

            1 -> {
                val data = Intent().apply {
                    putExtra(SEND_RESULT_NUM, DELETE_EXERCISE)
                }
                targetFragment?.onActivityResult(
                    targetRequestCode,
                    Activity.RESULT_OK,
                    data
                )
                dismiss()
                Toast.makeText(App.instance.context(), "삭제되었습니다.", Toast.LENGTH_SHORT).show()
            }

            2 -> {
                val data = Intent().apply {
                    putExtra(SEND_RESULT_NUM, UPDATE_EXERCISE)
                }
                targetFragment?.onActivityResult(
                    targetRequestCode,
                    Activity.RESULT_OK,
                    data
                )
                dismiss()
                Toast.makeText(App.instance.context(), "수정되었습니다.", Toast.LENGTH_SHORT).show()
            }


            0 -> {
                Toast.makeText(App.instance.context(), "실패하였습니다.", Toast.LENGTH_SHORT).show()
            }

        }


    }

    private fun hideKeyboard(editText: EditText) {
        val inputMethodManager =
            this.activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(editText.windowToken, 0)
    }

    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.iv_renew_add_exercise -> {

                hideKeyboard(et_renew_exercise_name)

                val addExerciseItem = layoutInflater.inflate(R.layout.add_exercise_item, null)

                viewList.add(addExerciseItem)

                ll_renew_add_remove_exercise.addView(addExerciseItem)
            }

            R.id.iv_renew_remove_exercise -> {

                hideKeyboard(et_renew_exercise_name)

                if (viewList.size != 0) {
                    ll_renew_add_remove_exercise.removeView(viewList[viewList.size - 1])
                    viewList.removeAt(viewList.size - 1)
                }

            }

            R.id.iv_delete_exercise -> {

                val getExerciseModel = arguments?.getParcelable<ExerciseModel>(EXERCISE_MODEL)

                getExerciseModel?.let {
                    presenter.deleteExercise(it)
                }

            }

            R.id.btn_renew_exercise_category -> {
                getMenuClick()
            }

            R.id.btn_renew_exercise_time -> {
                getTimePicker()
            }

            R.id.renew_exercise_cancel -> {
                dismiss()
            }

            R.id.renew_exercise_save -> {

                if (App.prefs.login_state && App.prefs.login_state_id.isNotEmpty()) {

                    if (btn_renew_exercise_category.text.isNotEmpty() && viewList.isNotEmpty() && et_renew_exercise_name.text.isNotEmpty()) {
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
                            val getExerciseModel =
                                arguments?.getParcelable<ExerciseModel>(EXERCISE_MODEL)

                            getExerciseModel?.let {
                                presenter.updateExercise(
                                    DateAndTime.convertSaveTime(btn_renew_exercise_time.text.toString()),
                                    btn_renew_exercise_category.text.toString(),
                                    et_renew_exercise_name.text.toString(),
                                    setList,
                                    getExerciseModel
                                )
                            }
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

        presenter = UpdateOrDeleteExercisePresenter(
            this,
            Injection.provideExerciseRepository()
        )
        init()

        iv_renew_add_exercise.setOnClickListener(this)
        iv_renew_remove_exercise.setOnClickListener(this)
        btn_renew_exercise_category.setOnClickListener(this)
        btn_renew_exercise_time.setOnClickListener(this)
        renew_exercise_cancel.setOnClickListener(this)
        renew_exercise_save.setOnClickListener(this)
        iv_delete_exercise.setOnClickListener(this)


        val getExerciseModel = arguments?.getParcelable<ExerciseModel>(EXERCISE_MODEL)

        getExerciseModel?.let {
            tv_renew_exercise_today.text = it.date
            btn_renew_exercise_time.text = DateAndTime.convertShowTime(it.time)
            btn_renew_exercise_category.text = it.type
            et_renew_exercise_name.setText(it.exerciseSetName)

            it.exerciseSet.forEach {
                val addExerciseItem = layoutInflater.inflate(R.layout.add_exercise_item, null)
                val addExerciseKg: EditText =
                    addExerciseItem.findViewById(R.id.et_add_exercise_kg)
                val addExerciseCount: EditText =
                    addExerciseItem.findViewById(R.id.et_add_exercise_count)
                addExerciseKg.setText(it.exerciseSetKg)
                addExerciseCount.setText(it.exerciseSetCount)
                viewList.add(addExerciseItem)
                ll_renew_add_remove_exercise.addView(addExerciseItem)
            }
        }
    }

    private fun init() {
        tv_renew_exercise_today.text =
            DateAndTime.currentDate()
        btn_renew_exercise_time.text =
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

                btn_renew_exercise_time.text =
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
            MenuPopupHelper(this.requireContext(), menuBuilder, btn_renew_exercise_category)
        optionMenu.setForceShowIcon(true)

        optionMenu.gravity = Gravity.LEFT
        menuBuilder.setCallback(object : MenuBuilder.Callback {
            override fun onMenuModeChange(menu: MenuBuilder?) {
            }

            override fun onMenuItemSelected(menu: MenuBuilder?, item: MenuItem?): Boolean {

                when (item?.itemId) {
                    R.id.chest -> {
                        btn_renew_exercise_category.text = "가슴"

                    }
                    R.id.back -> {
                        btn_renew_exercise_category.text = "등"

                    }
                    R.id.shoulder -> {
                        btn_renew_exercise_category.text = "어깨"

                    }
                    R.id.legs -> {
                        btn_renew_exercise_category.text = "다리"

                    }
                    R.id.arm -> {
                        btn_renew_exercise_category.text = "팔"
                    }
                }
                return true
            }
        })

        optionMenu.show()
    }

    companion object {
        const val TAG = "UpdateOrDeleteExerciseFragment"
        const val EXERCISE_MODEL = "data"
        const val SEND_RESULT_NUM = "result"
        const val DELETE_EXERCISE = 1
        const val UPDATE_EXERCISE = 2
        fun newInstance(
            exerciseModel: ExerciseModel
        ) = UpdateOrDeleteExerciseFragment().apply {
            arguments = Bundle().apply {
                putParcelable(EXERCISE_MODEL, exerciseModel)
            }
        }

    }


}