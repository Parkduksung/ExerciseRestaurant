package com.work.restaurant.view.diary.update_or_delete_eat

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import android.widget.TimePicker
import android.widget.Toast
import com.work.restaurant.Injection
import com.work.restaurant.R
import com.work.restaurant.data.model.EatModel
import com.work.restaurant.util.App
import com.work.restaurant.util.DateAndTime
import com.work.restaurant.util.RelateLogin
import com.work.restaurant.util.ShowAlertDialog
import com.work.restaurant.view.base.BaseDialogFragment
import com.work.restaurant.view.diary.update_or_delete_eat.presenter.UpdateOrDeleteEatContract
import com.work.restaurant.view.diary.update_or_delete_eat.presenter.UpdateOrDeleteEatPresenter
import kotlinx.android.synthetic.main.diary_update_or_delete_eat.*

class UpdateOrDeleteEatFragment : BaseDialogFragment(R.layout.diary_update_or_delete_eat),
    View.OnClickListener, UpdateOrDeleteEatContract.View {

    private lateinit var presenter: UpdateOrDeleteEatPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        presenter =
            UpdateOrDeleteEatPresenter(
                this,
                Injection.provideEatRepository()
            )

        startView()

        tv_renew_eat_time.setOnClickListener(this)
        btn_renew_eat_cancel.setOnClickListener(this)
        btn_renew_save.setOnClickListener(this)
        iv_delete_eat.setOnClickListener(this)

        getEatData()
    }

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.tv_renew_eat_time -> {
                getTimePicker()
            }
            R.id.btn_renew_eat_cancel -> {
                dismiss()
            }
            R.id.btn_renew_save -> {
                if (RelateLogin.loginState()) {

                    if (radioClick <= 1 && et_renew_eat_memo.text.isNotBlank()) {
                        if (et_renew_eat_memo.text.trim().isNotEmpty()) {

                            val getEatModel =
                                arguments?.getParcelable<EatModel>(GET_DATA)

                            getEatModel?.let {
                                presenter.updateEat(
                                    DateAndTime.convertSaveTime(tv_renew_eat_time.text.toString()),
                                    radioClick,
                                    et_renew_eat_memo.text.toString(),
                                    it
                                )
                                radioClick = 2
                            }

                        } else {
                            Toast.makeText(
                                context,
                                getString(R.string.common_context_error_message1),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else if (radioClick > 1 && et_renew_eat_memo.text.isNotBlank()) {
                        Toast.makeText(
                            context,
                            getString(R.string.common_context_error_message2),
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if (radioClick <= 1 && et_renew_eat_memo.text.isEmpty()) {
                        Toast.makeText(
                            context,
                            getString(R.string.common_context_error_message1),
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if (radioClick <= 1 && et_renew_eat_memo.text.trim().isEmpty()) {
                        Toast.makeText(
                            context,
                            getString(R.string.common_context_error_message1),
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            context,
                            getString(R.string.common_context_error_message3),
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

            R.id.iv_delete_eat -> {

                ShowAlertDialog(context).apply {
                    titleAndMessage(
                        getString(R.string.update_or_delete_eat_delete),
                        getString(R.string.update_or_delete_eat_delete_message)
                    )
                    alertDialog.setCancelable(false)
                    alertDialog.setPositiveButton(getString(R.string.common_delete)) { _, _ ->
                        val getEatModel =
                            arguments?.getParcelable<EatModel>(GET_DATA)

                        getEatModel?.let {
                            presenter.deleteEat(getEatModel)
                        }
                    }
                    alertDialog.setNegativeButton(getString(R.string.common_no)) { _, _ -> }
                    showDialog()
                }
            }
        }
    }

    private fun startView() {
        tv_renew_eat_today.text =
            DateAndTime.currentDate()
        tv_renew_eat_time.text =
            DateAndTime.convertShowTime(DateAndTime.currentTime())
        getRadioClickNum(renew_eat_radio_group)
    }

    private fun getEatData() {
        val getEatModel =
            arguments?.getParcelable<EatModel>(GET_DATA)

        getEatModel?.let {
            et_renew_eat_memo.setText(getEatModel.memo)
            tv_renew_eat_today.text = getEatModel.date
            tv_renew_eat_time.text = DateAndTime.convertShowTime(getEatModel.time)
            radioClick = getEatModel.type
            if (getEatModel.type == CHECK_MEAL) {
                renew_eat_radio_group.check(R.id.rb_meal_renew)
            } else {
                renew_eat_radio_group.check(R.id.rb_snack_renew)
            }
        }
    }

    override fun showResult(sort: Int) {

        when (sort) {
            UpdateOrDeleteEatPresenter.SUCCESS_UPDATE -> {
                val data =
                    Intent().apply {
                        putExtra(SEND_RESULT_NUM, UPDATE_EAT)
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
            UpdateOrDeleteEatPresenter.SUCCESS_DELETE -> {
                val data =
                    Intent().apply {
                        putExtra(SEND_RESULT_NUM, DELETE_EAT)
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
            UpdateOrDeleteEatPresenter.FAIL_UPDATE -> {
                Toast.makeText(
                    App.instance.context(),
                    getString(R.string.common_update_fail),
                    Toast.LENGTH_SHORT
                ).show()
            }

            UpdateOrDeleteEatPresenter.FAIL_DELETE -> {
                Toast.makeText(
                    App.instance.context(),
                    getString(R.string.common_delete_fail),
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

    }

    private fun getRadioClickNum(radioGroup: RadioGroup) {
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_meal_renew -> {
                    radioClick = CHECK_MEAL
                }
                R.id.rb_snack_renew -> {
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
                tv_renew_eat_time.text =
                    DateAndTime.convertPickerTime(timePicker.hour, timePicker.minute)
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

        const val TAG = "UpdateOrDeleteEatFragment"
        private var radioClick = 2

        const val SEND_RESULT_NUM = "result"
        const val GET_DATA = "data"
        const val DELETE_EAT = 1
        const val UPDATE_EAT = 2

        private const val CHECK_MEAL = 0
        private const val CHECK_SNACK = 1

        fun newInstance(
            eatModel: EatModel
        ) = UpdateOrDeleteEatFragment().apply {
            arguments = Bundle().apply {
                putParcelable(GET_DATA, eatModel)
            }


        }

    }

}
