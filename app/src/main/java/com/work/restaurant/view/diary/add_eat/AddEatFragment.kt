package com.work.restaurant.view.diary.add_eat

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.RadioGroup
import android.widget.TimePicker
import com.work.restaurant.R
import com.work.restaurant.ext.showToast
import com.work.restaurant.util.DateAndTime
import com.work.restaurant.util.RelateLogin
import com.work.restaurant.util.ShowAlertDialog
import com.work.restaurant.base.BaseDialogFragment
import com.work.restaurant.view.diary.add_eat.presenter.AddEatContract
import kotlinx.android.synthetic.main.diary_add_eat.*
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf


class AddEatFragment : BaseDialogFragment(R.layout.diary_add_eat),
    View.OnClickListener, AddEatContract.View {

    private lateinit var presenter: AddEatContract.Presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = get { parametersOf(this) }
        startView()

        tv_add_eat_time.setOnClickListener(this)
        tv_add_eat_today.setOnClickListener(this)
        add_eat_cancel.setOnClickListener(this)
        add_eat_save.setOnClickListener(this)

    }

    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.tv_add_eat_today -> {
                getDatePicker()
            }
            R.id.tv_add_eat_time -> {
                getTimePicker()
            }
            R.id.add_eat_cancel -> {
                dismiss()
            }
            R.id.add_eat_save -> {

                if (RelateLogin.loginState()) {
                    when {
                        radioClick <= CHECK_RADIO_STATE && et_add_eat_memo.text.isNotBlank() -> {
                            if (et_add_eat_memo.text.trim().isNotEmpty()) {
                                presenter.addEat(
                                    RelateLogin.getLoginId(),
                                    tv_add_eat_today.text.toString(),
                                    DateAndTime.convertSaveTime(tv_add_eat_time.text.toString()),
                                    radioClick,
                                    et_add_eat_memo.text.toString()
                                )
                                radioClick = 2
                            } else {
                                showToast(getString(R.string.common_context_error_message1))
                            }
                        }

                        radioClick > CHECK_RADIO_STATE && et_add_eat_memo.text.isNotBlank() -> {
                            showToast(getString(R.string.common_context_error_message2))
                        }
                        radioClick <= CHECK_RADIO_STATE && et_add_eat_memo.text.isEmpty() -> {
                            showToast(getString(R.string.common_context_error_message1))
                        }
                        radioClick <= CHECK_RADIO_STATE && et_add_eat_memo.text.trim().isEmpty() -> {
                            showToast(getString(R.string.common_context_error_message1))
                        }
                        else -> {
                            showToast(getString(R.string.common_context_error_message3))
                        }
                    }
                } else {
                    showToast(getString(R.string.common_when_logout_not_save_records))
                }
            }

        }
    }

    private fun startView() {
        tv_add_eat_today.text =
            arguments?.getString(DATE)
        tv_add_eat_time.text =
            DateAndTime.convertShowTime(DateAndTime.currentTime())
        getRadioClickNum(add_eat_radio_group)
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

    private fun getRadioClickNum(radioGroup: RadioGroup) {
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_meal -> {
                    radioClick = CHECK_MEAL
                }
                R.id.rb_snack -> {
                    radioClick = CHECK_SNACK
                }
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
                tv_add_eat_time.text =
                    DateAndTime.convertPickerTime(timePicker.hour, timePicker.minute)
            }
            alertDialog.setNegativeButton(getString(R.string.common_no)) { _, _ -> }
            showDialog()
        }

    }

    private fun getDatePicker() {

        val dialogView = View.inflate(context, R.layout.date_picker, null)
        val datePicker = dialogView.findViewById<DatePicker>(R.id.date_picker)

        ShowAlertDialog(context).apply {
            alertDialog.setView(dialogView)
            alertDialog.setCancelable(false)
            alertDialog.setPositiveButton(getString(R.string.common_change)) { _, _ ->
                tv_add_eat_today.text =
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

    override fun onResume() {
        radioClick = 2
        super.onResume()
    }

    companion object {
        const val TAG = "AddEatFragment"
        private var radioClick = 2
        private const val DATE = "date"

        private const val CHECK_MEAL = 0
        private const val CHECK_SNACK = 1

        private const val CHECK_RADIO_STATE = 1

        fun newInstance(
            date: String
        ) = AddEatFragment().apply {
            arguments = Bundle().apply {
                putString(DATE, date)
            }
        }
    }

}
