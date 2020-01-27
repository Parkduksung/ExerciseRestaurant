package com.work.restaurant.view.diary.add_eat

import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.RadioGroup
import android.widget.TimePicker
import android.widget.Toast
import com.work.restaurant.Injection
import com.work.restaurant.R
import com.work.restaurant.view.base.BaseFragment
import com.work.restaurant.view.diary.add_eat.presenter.AddEatContract
import com.work.restaurant.view.diary.add_eat.presenter.AddEatPresenter
import kotlinx.android.synthetic.main.diary_add_eat.*
import java.text.SimpleDateFormat
import java.util.*


class AddEatFragment : BaseFragment(R.layout.diary_add_eat),
    View.OnClickListener, AddEatContract.View {
    override fun showAddResult(msg: String) {
        Log.d("저장결과", msg)
    }


    private lateinit var presenter: AddEatPresenter


    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.btn_add_eat_time -> {

                getTimePicker()

            }

            R.id.add_eat_cancel -> {
                requireFragmentManager().beginTransaction()
                    .remove(this@AddEatFragment)
                    .commit()

            }

            R.id.add_eat_save -> {

                if (radioClick <= 1) {
                    presenter.addEat(
                        tv_add_eat_today.text.toString(),
                        btn_add_eat_time.text.toString(),
                        radioClick,
                        et_add_eat_memo.text.toString()
                    )
                    radioClick = 2

                    requireFragmentManager().beginTransaction()
                        .remove(this@AddEatFragment)
                        .commit()
                    Toast.makeText(this.context, "저장되었습니다.", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(this.context, "저장할 수 없습니다.", Toast.LENGTH_SHORT).show()
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
                    Toast.makeText(this.context, "0", Toast.LENGTH_SHORT).show()
                    radioClick = 0
                }

                R.id.rb_snack -> {
                    Toast.makeText(this.context, "1", Toast.LENGTH_SHORT).show()
                    radioClick = 1
                }
            }
        }
    }

    private fun init() {

        val currentTime = Calendar.getInstance().time

        val dateTextAll =
            SimpleDateFormat("yyyy-MM-dd-a-h-m", Locale.getDefault()).format(currentTime)

        val dateArray = dateTextAll.split("-")

        tv_add_eat_today.text =
            getString(R.string.current_date, dateArray[0], dateArray[1], dateArray[2])
        btn_add_eat_time.text =
            getString(R.string.current_time, dateArray[3], dateArray[4], dateArray[5])
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

                val changedTime =
                    "${getAmPm(timePicker.hour)} ${timePicker.minute}분"

                btn_add_eat_time.text = changedTime

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


    companion object {
        private const val TAG = "AddEatFragment"
        var radioClick = 2

    }

}
