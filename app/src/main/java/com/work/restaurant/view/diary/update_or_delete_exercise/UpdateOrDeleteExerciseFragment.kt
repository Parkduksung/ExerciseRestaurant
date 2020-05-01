package com.work.restaurant.view.diary.update_or_delete_exercise

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.view.menu.MenuPopupHelper
import com.work.restaurant.Injection
import com.work.restaurant.R
import com.work.restaurant.data.model.ExerciseModel
import com.work.restaurant.data.model.ExerciseSet
import com.work.restaurant.util.*
import com.work.restaurant.view.base.BaseDialogFragment
import com.work.restaurant.view.diary.update_or_delete_exercise.presenter.UpdateOrDeleteExerciseContract
import com.work.restaurant.view.diary.update_or_delete_exercise.presenter.UpdateOrDeleteExercisePresenter
import kotlinx.android.synthetic.main.diary_update_or_delete_exercise.*

class UpdateOrDeleteExerciseFragment : BaseDialogFragment(R.layout.diary_update_or_delete_exercise),
    View.OnClickListener, UpdateOrDeleteExerciseContract.View {

    private lateinit var viewList: ArrayList<View>

    private lateinit var presenter: UpdateOrDeleteExercisePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewList = ArrayList()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter =
            UpdateOrDeleteExercisePresenter(
                this,
                Injection.provideExerciseRepository()
            )
        startView()

        iv_renew_add_exercise.setOnClickListener(this)
        iv_renew_remove_exercise.setOnClickListener(this)
        tv_renew_exercise_category.setOnClickListener(this)
        tv_renew_exercise_time.setOnClickListener(this)
        renew_exercise_cancel.setOnClickListener(this)
        renew_exercise_save.setOnClickListener(this)
        iv_delete_exercise.setOnClickListener(this)

        getExerciseData()
    }

    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.iv_renew_add_exercise -> {

                Keyboard.hideEditText(context, et_renew_exercise_name)


                val addExerciseItem =
                    layoutInflater.inflate(R.layout.add_exercise_item, null)

                viewList.add(addExerciseItem)

                ll_renew_add_remove_exercise.addView(addExerciseItem)
            }

            R.id.iv_renew_remove_exercise -> {


                Keyboard.hideEditText(context, et_renew_exercise_name)

                if (viewList.size != 0) {
                    ll_renew_add_remove_exercise.removeView(viewList[viewList.size - 1])
                    viewList.removeAt(viewList.size - 1)
                }

            }

            R.id.iv_delete_exercise -> {

                ShowAlertDialog(context).apply {
                    titleAndMessage(
                        getString(R.string.update_or_delete_eat_delete),
                        getString(R.string.update_or_delete_eat_delete_message)
                    )
                    alertDialog.setCancelable(false)
                    alertDialog.setPositiveButton(getString(R.string.common_delete)) { _, _ ->
                        val getExerciseModel =
                            arguments?.getParcelable<ExerciseModel>(EXERCISE_MODEL)

                        getExerciseModel?.let {
                            presenter.deleteExercise(it)
                        }
                    }
                    alertDialog.setNegativeButton(getString(R.string.common_no)) { _, _ -> }
                    showDialog()
                }

            }

            R.id.tv_renew_exercise_category -> {
                getMenuClick()
            }

            R.id.tv_renew_exercise_time -> {
                getTimePicker()
            }

            R.id.renew_exercise_cancel -> {
                dismiss()
            }

            R.id.renew_exercise_save -> {

                if (RelateLogin.loginState()) {

                    if (tv_renew_exercise_category.text.isNotEmpty() && viewList.isNotEmpty() && et_renew_exercise_name.text.isNotEmpty()) {
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
                                    DateAndTime.convertSaveTime(tv_renew_exercise_time.text.toString()),
                                    tv_renew_exercise_category.text.toString(),
                                    et_renew_exercise_name.text.toString(),
                                    setList,
                                    getExerciseModel
                                )
                            }
                        } else {
                            Toast.makeText(
                                context,
                                getString(R.string.common_exercise_no_input_kg_and_count_error_message),
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    } else {
                        Toast.makeText(
                            context,
                            getString(R.string.common_save_no),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        context,
                        getString(R.string.common_when_logout_not_save_records),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
    }

    private fun startView() {
        tv_renew_exercise_today.text =
            DateAndTime.currentDate()
        tv_renew_exercise_time.text =
            DateAndTime.convertShowTime(DateAndTime.currentTime())
    }

    private fun getExerciseData() {

        val getExerciseModel =
            arguments?.getParcelable<ExerciseModel>(EXERCISE_MODEL)

        getExerciseModel?.let {
            tv_renew_exercise_today.text = it.date
            tv_renew_exercise_time.text = DateAndTime.convertShowTime(it.time)
            tv_renew_exercise_category.text = it.type
            et_renew_exercise_name.setText(it.exerciseSetName)

            it.exerciseSet.forEach { set ->
                val addExerciseItem =
                    layoutInflater.inflate(R.layout.add_exercise_item, null)
                val addExerciseKg: EditText =
                    addExerciseItem.findViewById(R.id.et_add_exercise_kg)
                val addExerciseCount: EditText =
                    addExerciseItem.findViewById(R.id.et_add_exercise_count)
                addExerciseKg.setText(set.exerciseSetKg)
                addExerciseCount.setText(set.exerciseSetCount)
                viewList.add(addExerciseItem)
                ll_renew_add_remove_exercise.addView(addExerciseItem)
            }
        }
    }

    override fun showResult(sort: Int) {

        when (sort) {

            UpdateOrDeleteExercisePresenter.SUCCESS_UPDATE -> {
                val data =
                    Intent().apply {
                        putExtra(SEND_RESULT_NUM, UPDATE_EXERCISE)
                    }
                targetFragment?.onActivityResult(
                    targetRequestCode,
                    Activity.RESULT_OK,
                    data
                )
                Toast.makeText(
                    App.instance.context(),
                    getString(R.string.common_update_ok),
                    Toast.LENGTH_SHORT
                ).show()

                dismiss()
            }

            UpdateOrDeleteExercisePresenter.SUCCESS_DELETE -> {
                val data =
                    Intent().apply {
                        putExtra(SEND_RESULT_NUM, DELETE_EXERCISE)
                    }
                targetFragment?.onActivityResult(
                    targetRequestCode,
                    Activity.RESULT_OK,
                    data
                )
                Toast.makeText(
                    App.instance.context(),
                    getString(R.string.common_delete_ok),
                    Toast.LENGTH_SHORT
                ).show()
                dismiss()


            }

            UpdateOrDeleteExercisePresenter.FAIL_UPDATE -> {
                Toast.makeText(
                    App.instance.context(),
                    getString(R.string.common_update_fail),
                    Toast.LENGTH_SHORT
                ).show()
            }

            UpdateOrDeleteExercisePresenter.FAIL_DELETE -> {
                Toast.makeText(
                    App.instance.context(),
                    getString(R.string.common_delete_fail),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
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
                tv_renew_exercise_time.text =
                    DateAndTime.convertPickerTime(timePicker.hour, timePicker.minute)
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
            MenuPopupHelper(requireContext(), menuBuilder, tv_renew_exercise_category)
        optionMenu.setForceShowIcon(true)

        optionMenu.gravity = Gravity.LEFT
        menuBuilder.setCallback(object : MenuBuilder.Callback {
            override fun onMenuModeChange(menu: MenuBuilder?) {
            }

            override fun onMenuItemSelected(menu: MenuBuilder?, item: MenuItem?): Boolean {

                when (item?.itemId) {
                    R.id.chest -> {
                        tv_renew_exercise_category.text =
                            resources.getStringArray(R.array.add_exercise_part)[0]
                    }
                    R.id.back -> {
                        tv_renew_exercise_category.text =
                            resources.getStringArray(R.array.add_exercise_part)[1]
                    }
                    R.id.shoulder -> {
                        tv_renew_exercise_category.text =
                            resources.getStringArray(R.array.add_exercise_part)[2]
                    }
                    R.id.legs -> {
                        tv_renew_exercise_category.text =
                            resources.getStringArray(R.array.add_exercise_part)[4]
                    }
                    R.id.arm -> {
                        tv_renew_exercise_category.text =
                            resources.getStringArray(R.array.add_exercise_part)[3]
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