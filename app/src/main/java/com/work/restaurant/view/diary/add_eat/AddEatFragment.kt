package com.work.restaurant.view.diary.add_eat

import android.app.Activity
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.RadioGroup
import android.widget.TimePicker
import android.widget.Toast
import com.work.restaurant.Injection
import com.work.restaurant.R
import com.work.restaurant.util.App
import com.work.restaurant.util.DateAndTime
import com.work.restaurant.view.base.BaseDialogFragment
import com.work.restaurant.view.diary.add_eat.presenter.AddEatContract
import com.work.restaurant.view.diary.add_eat.presenter.AddEatPresenter
import kotlinx.android.synthetic.main.diary_add_eat.*


class AddEatFragment : BaseDialogFragment(R.layout.diary_add_eat),
    View.OnClickListener, AddEatContract.View {

    private lateinit var presenter: AddEatPresenter

    override fun showAddSuccess() {
        targetFragment?.onActivityResult(
            targetRequestCode,
            Activity.RESULT_OK,
            null
        )
        dismiss()
        Toast.makeText(this.context, "저장되었습니다.", Toast.LENGTH_SHORT).show()

    }

    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.btn_add_eat_time -> {
                getTimePicker()
            }
            R.id.add_eat_cancel -> {
                dismiss()
            }
            R.id.add_eat_save -> {

                if (App.prefs.login_state && App.prefs.login_state_id.isNotEmpty()) {

                    if (radioClick <= 1 && et_add_eat_memo.text.isNotBlank()) {
                        if (et_add_eat_memo.text.trim().isNotEmpty()) {
                            presenter.addEat(
                                App.prefs.login_state_id,
                                tv_add_eat_today.text.toString(),
                                DateAndTime.convertSaveTime(btn_add_eat_time.text.toString()),
                                radioClick,
                                et_add_eat_memo.text.toString()
                            )
                            radioClick = 2
                        } else {
                            Toast.makeText(this.context, "내용을 입력하세요.", Toast.LENGTH_SHORT).show()
                        }
                    } else if (radioClick > 1 && et_add_eat_memo.text.isNotBlank()) {
                        Toast.makeText(this.context, "항목을 선택하세요.", Toast.LENGTH_SHORT).show()
                    } else if (radioClick <= 1 && et_add_eat_memo.text.isEmpty()) {
                        Toast.makeText(this.context, "내용을 입력하세요.", Toast.LENGTH_SHORT).show()
                    } else if (radioClick <= 1 && et_add_eat_memo.text.trim().isEmpty()) {
                        Toast.makeText(this.context, "내용을 입력하세요.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this.context, "항목과 내용을 입력하세요.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this.context, "로그아웃시에는 기록을 저장할 수 없습니다.", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        presenter = AddEatPresenter(
            this,
            Injection.provideEatRepository()
        )
        btn_add_eat_time.setOnClickListener(this)
        add_eat_cancel.setOnClickListener(this)
        add_eat_save.setOnClickListener(this)

    }

    private fun getRadioClickNum(radioGroup: RadioGroup) {
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_meal -> {
                    radioClick = 0
                }
                R.id.rb_snack -> {
                    radioClick = 1
                }
            }
        }
    }

    private fun init() {
        tv_add_eat_today.text =
            DateAndTime.currentDate()
        btn_add_eat_time.text =
            DateAndTime.convertShowTime(DateAndTime.currentTime())
        getRadioClickNum(add_eat_radio_group)
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

                btn_add_eat_time.text =
                    DateAndTime.convertPickerTime(timePicker.hour, timePicker.minute)

            }
            .setNegativeButton("취소") { _, _ ->

            }
            .show()

    }

    override fun onResume() {
        radioClick = 2
        super.onResume()
    }

    companion object {
        const val TAG = "AddEatFragment"
        private var radioClick = 2

    }

}
