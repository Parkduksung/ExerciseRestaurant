package com.work.restaurant.view.diary.add_exercise

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TimePicker
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.view.menu.MenuPopupHelper
import com.work.restaurant.Injection
import com.work.restaurant.R
import com.work.restaurant.data.model.ExerciseSet
import com.work.restaurant.ext.showToast
import com.work.restaurant.util.DateAndTime
import com.work.restaurant.util.Keyboard
import com.work.restaurant.util.RelateLogin
import com.work.restaurant.util.ShowAlertDialog
import com.work.restaurant.view.base.BaseDialogFragment
import com.work.restaurant.view.diary.add_exercise.presenter.AddExerciseContract
import com.work.restaurant.view.diary.add_exercise.presenter.AddExercisePresenter
import kotlinx.android.synthetic.main.diary_add_exercise.*


class AddExerciseFragment : BaseDialogFragment(R.layout.diary_add_exercise),
    View.OnClickListener, AddExerciseContract.View {

    private lateinit var viewList: ArrayList<View>

    private lateinit var presenter: AddExercisePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewList = ArrayList()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter =
            AddExercisePresenter(
                this,
                Injection.provideExerciseRepository()
            )
        startView()

        iv_add_exercise.setOnClickListener(this)
        iv_remove_exercise.setOnClickListener(this)
        tv_add_exercise_category.setOnClickListener(this)
        tv_add_exercise_time.setOnClickListener(this)
        add_exercise_cancel.setOnClickListener(this)
        add_exercise_save.setOnClickListener(this)

        tv_add_exercise_today.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.tv_add_exercise_today -> {
                getDatePicker()
            }

            R.id.iv_add_exercise -> {

                Keyboard.hideEditText(context, et_add_exercise_name)

                val addExerciseItem =
                    layoutInflater.inflate(R.layout.add_exercise_item, null)

                viewList.add(addExerciseItem)

                ll_add_remove_exercise.addView(addExerciseItem)
            }

            R.id.iv_remove_exercise -> {

                Keyboard.hideEditText(context, et_add_exercise_name)

                if (viewList.size != 0) {
                    ll_add_remove_exercise.removeView(viewList[viewList.size - 1])
                    viewList.removeAt(viewList.size - 1)
                }
            }

            R.id.tv_add_exercise_category -> {
                getMenuClick()
            }

            R.id.tv_add_exercise_time -> {
                getTimePicker()
            }

            R.id.add_exercise_cancel -> {
                dismiss()
            }

            R.id.add_exercise_save -> {

                if (RelateLogin.loginState()) {

                    if (tv_add_exercise_category.text.isNotEmpty() && viewList.isNotEmpty() && et_add_exercise_name.text != null) {
                        val setList =
                            mutableListOf<ExerciseSet>()
                        viewList.forEach {
                            val addExerciseKg: EditText =
                                it.findViewById(R.id.et_add_exercise_kg)
                            val addExerciseCount: EditText =
                                it.findViewById(R.id.et_add_exercise_count)

                            if (addExerciseKg.text.toString().isNotEmpty() && addExerciseCount.text.toString().isNotEmpty()) {
                                val exerciseSet =
                                    ExerciseSet(
                                        addExerciseKg.text.toString(),
                                        addExerciseCount.text.toString()
                                    )
                                setList.add(exerciseSet)
                            }
                        }
                        if (setList.isNotEmpty()) {
                            presenter.addExercise(
                                RelateLogin.getLoginId(),
                                tv_add_exercise_today.text.toString(),
                                DateAndTime.convertSaveTime(tv_add_exercise_time.text.toString()),
                                tv_add_exercise_category.text.toString(),
                                et_add_exercise_name.text.toString(),
                                setList
                            )
                        } else {
                            showToast(getString(R.string.common_exercise_no_input_kg_and_count_error_message))
                        }
                    } else {
                        showToast(getString(R.string.common_save_no))
                    }

                } else {
                    showToast(getString(R.string.common_when_logout_not_save_records))
                }


            }

        }
    }

    private fun startView() {
        tv_add_exercise_today.text =
            arguments?.getString(DATE)
        tv_add_exercise_time.text =
            DateAndTime.convertShowTime(DateAndTime.currentTime())
    }

    override fun showAddSuccess() {
        targetFragment?.onActivityResult(
            targetRequestCode,
            Activity.RESULT_OK,
            null
        )
        dismiss()
        showToast(getString(R.string.common_save_ok))
    }

    private fun getTimePicker() {
        val dialogView =
            View.inflate(context, R.layout.time_picker, null)
        val timePicker =
            dialogView.findViewById<TimePicker>(R.id.time_picker)

        ShowAlertDialog(context).apply {
            alertDialog.setView(dialogView)
            alertDialog.setCancelable(false)
            alertDialog.setPositiveButton(getString(R.string.common_change)) { _, _ ->
                tv_add_exercise_time.text =
                    DateAndTime.convertPickerTime(timePicker.hour, timePicker.minute)
            }
            alertDialog.setNegativeButton(getString(R.string.common_no)) { _, _ -> }
            showDialog()
        }
    }

    private fun getDatePicker() {

        val dialogView =
            View.inflate(context, R.layout.date_picker, null)
        val datePicker =
            dialogView.findViewById<DatePicker>(R.id.date_picker)

        ShowAlertDialog(context).apply {
            alertDialog.setView(dialogView)
            alertDialog.setCancelable(false)
            alertDialog.setPositiveButton(getString(R.string.common_change)) { _, _ ->
                tv_add_exercise_today.text =
                    getString(
                        R.string.common_date,
                        datePicker.year.toString(),
                        (datePicker.month + 1).toString(),
                        datePicker.dayOfMonth.toString()
                    )
            }
            alertDialog.setNegativeButton(getString(R.string.common_no)) { _, _ -> }
            showDialog()
        }
    }

    @SuppressLint("RtlHardcoded")
    private fun getMenuClick() {
        val menuBuilder = MenuBuilder(context)
        val inflater = MenuInflater(context)
        inflater.inflate(R.menu.exercise_menu, menuBuilder)
        val optionMenu =
            MenuPopupHelper(requireContext(), menuBuilder, tv_add_exercise_category)
        optionMenu.setForceShowIcon(true)

        optionMenu.gravity = Gravity.LEFT
        menuBuilder.setCallback(object : MenuBuilder.Callback {
            override fun onMenuModeChange(menu: MenuBuilder?) {
            }

            override fun onMenuItemSelected(menu: MenuBuilder?, item: MenuItem?): Boolean {

                when (item?.itemId) {
                    R.id.chest -> {
                        tv_add_exercise_category.text =
                            resources.getStringArray(R.array.exercise_part)[0]
                    }
                    R.id.back -> {
                        tv_add_exercise_category.text =
                            resources.getStringArray(R.array.exercise_part)[1]
                    }
                    R.id.shoulder -> {
                        tv_add_exercise_category.text =
                            resources.getStringArray(R.array.exercise_part)[2]
                    }
                    R.id.legs -> {
                        tv_add_exercise_category.text =
                            resources.getStringArray(R.array.exercise_part)[4]
                    }
                    R.id.arm -> {
                        tv_add_exercise_category.text =
                            resources.getStringArray(R.array.exercise_part)[3]
                    }
                }
                return true
            }
        })

        optionMenu.show()
    }

    companion object {
        const val TAG = "AddExerciseFragment"

        private const val DATE = "date"

        fun newInstance(
            date: String
        ) = AddExerciseFragment().apply {
            arguments = Bundle().apply {
                putString(DATE, date)
            }
        }
    }


}