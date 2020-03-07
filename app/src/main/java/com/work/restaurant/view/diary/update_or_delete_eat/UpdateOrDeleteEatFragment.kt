package com.work.restaurant.view.diary.update_or_delete_eat

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.RadioGroup
import android.widget.TimePicker
import android.widget.Toast
import com.work.restaurant.Injection
import com.work.restaurant.R
import com.work.restaurant.data.model.EatModel
import com.work.restaurant.util.App
import com.work.restaurant.util.DateAndTime
import com.work.restaurant.view.base.BaseDialogFragment
import com.work.restaurant.view.diary.update_or_delete_eat.presenter.UpdateOrDeleteEatContract
import com.work.restaurant.view.diary.update_or_delete_eat.presenter.UpdateOrDeleteEatPresenter
import kotlinx.android.synthetic.main.diary_update_or_delete_eat.*

class UpdateOrDeleteEatFragment : BaseDialogFragment(R.layout.diary_update_or_delete_eat),
    View.OnClickListener, UpdateOrDeleteEatContract.View {

    private lateinit var presenter: UpdateOrDeleteEatPresenter


    override fun showResult(sort: Int) {

        when (sort) {
            1 -> {
                val data = Intent().apply {
                    putExtra(SEND_RESULT_NUM, UPDATE_EAT)
                }
                targetFragment?.onActivityResult(
                    targetRequestCode,
                    Activity.RESULT_OK,
                    data
                )
                Toast.makeText(App.instance.context(), "수정되었습니다.", Toast.LENGTH_SHORT).show()
                dismiss()
            }
            2 -> {
                val data = Intent().apply {
                    putExtra(SEND_RESULT_NUM, DELETE_EAT)
                }

                targetFragment?.onActivityResult(
                    targetRequestCode,
                    Activity.RESULT_OK,
                    data
                )
                Toast.makeText(App.instance.context(), "삭제되었습니다.", Toast.LENGTH_SHORT).show()
                dismiss()
            }
            0 -> {
                Toast.makeText(App.instance.context(), "실패하였습니다.", Toast.LENGTH_SHORT).show()
//                dismiss()
            }

        }

    }


    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.btn_renew_eat_time -> {
                getTimePicker()
            }

            R.id.btn_renew_eat_cancel -> {
                dismiss()
            }

            R.id.btn_renew_save -> {
                if (App.prefs.login_state && App.prefs.login_state_id.isNotEmpty()) {

                    if (radioClick <= 1 && et_renew_eat_memo.text.isNotBlank()) {
                        if (et_renew_eat_memo.text.trim().isNotEmpty()) {

                            val getEatModel = arguments?.getParcelable<EatModel>(GET_DATA)

                            getEatModel?.let {
                                presenter.updateEat(
                                    DateAndTime.convertSaveTime(btn_renew_eat_time.text.toString()),
                                    radioClick,
                                    et_renew_eat_memo.text.toString(),
                                    it
                                )
                                radioClick = 2

                            }

                        } else {
                            Toast.makeText(this.context, "내용을 입력하세요.", Toast.LENGTH_SHORT).show()
                        }
                    } else if (radioClick > 1 && et_renew_eat_memo.text.isNotBlank()) {
                        Toast.makeText(this.context, "항목을 선택하세요.", Toast.LENGTH_SHORT).show()
                    } else if (radioClick <= 1 && et_renew_eat_memo.text.isEmpty()) {
                        Toast.makeText(this.context, "내용을 입력하세요.", Toast.LENGTH_SHORT).show()
                    } else if (radioClick <= 1 && et_renew_eat_memo.text.trim().isEmpty()) {
                        Toast.makeText(this.context, "내용을 입력하세요.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this.context, "항목과 내용을 입력하세요.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this.context, "로그아웃시에는 기록을 저장할 수 없습니다.", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            R.id.iv_delete_eat -> {
                AlertDialog.Builder(this.context)
                    .setTitle("기록 삭제")
                    .setMessage("선택한 기록을 삭제하시겠습니까?")
                    .setPositiveButton(
                        "삭제"
                    ) { _, _ ->

                        val getEatModel = arguments?.getParcelable<EatModel>(GET_DATA)

                        getEatModel?.let {
                            presenter.deleteEat(getEatModel)
                        }
//                        presenter.deleteEat(getEatModel)
                    }
                    .setNegativeButton(
                        "취소"
                    ) { _, _ ->

                    }
                    .show()
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        presenter = UpdateOrDeleteEatPresenter(
            this,
            Injection.provideEatRepository()
        )

        btn_renew_eat_time.setOnClickListener(this)
        btn_renew_eat_cancel.setOnClickListener(this)
        btn_renew_save.setOnClickListener(this)
        iv_delete_eat.setOnClickListener(this)


        val getEatModel = arguments?.getParcelable<EatModel>(GET_DATA)

        getEatModel?.let {
            et_renew_eat_memo.setText(getEatModel.memo)
            tv_renew_eat_today.text = getEatModel.date
            btn_renew_eat_time.text = DateAndTime.convertShowTime(getEatModel.time)
            radioClick = getEatModel.type
            if (getEatModel.type == 0) {
                renew_eat_radio_group.check(R.id.rb_meal_renew)
            } else {
                renew_eat_radio_group.check(R.id.rb_snack_renew)
            }
        }
    }

    private fun getRadioClickNum(radioGroup: RadioGroup) {
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_meal_renew -> {
                    radioClick = 0
                }
                R.id.rb_snack_renew -> {
                    radioClick = 1
                }
            }
        }
    }

    private fun init() {
        tv_renew_eat_today.text =
            DateAndTime.currentDate()
        btn_renew_eat_time.text =
            DateAndTime.convertShowTime(DateAndTime.currentTime())
        getRadioClickNum(renew_eat_radio_group)
    }

    private fun getTimePicker() {
        val dialogView = View.inflate(context, R.layout.time_picker, null)
        val timePicker = dialogView.findViewById<TimePicker>(R.id.time_picker)

        AlertDialog.Builder(
            ContextThemeWrapper(
                activity,
                R.style.Theme_AppCompat_Light_Dialog
            )
        ).setView(dialogView)
            .setPositiveButton("변경") { _, _ ->
                btn_renew_eat_time.text =
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

        const val TAG = "UpdateOrDeleteEatFragment"
        private var radioClick = 2

        const val SEND_RESULT_NUM = "result"
        const val GET_DATA = "data"
        const val DELETE_EAT = 1
        const val UPDATE_EAT = 2


        fun newInstance(
            eatModel: EatModel
        ) = UpdateOrDeleteEatFragment().apply {
            arguments = Bundle().apply {
                putParcelable(GET_DATA, eatModel)
            }


        }

    }

}
