package com.work.restaurant.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
object DateAndTime {

    private const val DATE_PATTERN = "yyyy년 M월 d일"
    private val simpleDateFormat = SimpleDateFormat(DATE_PATTERN)

    fun currentDate(): String =
        simpleDateFormat.format(Date())

    fun convertDate(date: String): List<String> =
        date.split(" ").map {
            Regex("[^0-9]").replace(it, "")
        }


    fun convertDayOfWeek(date: String): String {

        val list = convertDate(date)

        val calendar = Calendar.getInstance()
        calendar.set(list[0].toInt(), list[1].toInt() - 1, list[2].toInt())

        return SimpleDateFormat("EEEE", Locale.getDefault()).format(calendar.time)

    }

    fun beforeDate(date: List<String>): String {

        val calendar = Calendar.getInstance()
        calendar.apply {
            set(date[0].toInt(), date[1].toInt() - 1, date[2].toInt())
            add(Calendar.DATE, -1)
        }
        return simpleDateFormat.format(calendar.time)
    }


    fun beforeWeek(date: List<String>): String {

        val calendar = Calendar.getInstance()
        calendar.apply {
            set(date[0].toInt(), date[1].toInt() - 1, date[2].toInt())
            add(Calendar.DATE, -7)
        }

        return simpleDateFormat.format(calendar.time)
    }

    fun afterDate(date: List<String>): String {

        val calendar = Calendar.getInstance()
        calendar.apply {
            set(date[0].toInt(), date[1].toInt() - 1, date[2].toInt())
            add(Calendar.DATE, 1)
        }
        return simpleDateFormat.format(calendar.time)
    }


    fun afterWeek(date: List<String>): String {
        val calendar = Calendar.getInstance()
        calendar.apply {
            set(date[0].toInt(), date[1].toInt() - 1, date[2].toInt())
            add(Calendar.DATE, 7)
        }
        return simpleDateFormat.format(calendar.time)
    }


    fun currentTime(): String {
        val currentTime = Calendar.getInstance().time
        return SimpleDateFormat("ahhmm", Locale.getDefault()).format(currentTime)
    }

    fun lastDayOfThisMonth(): Int =
        Calendar.getInstance().getMaximum(Calendar.DAY_OF_MONTH)


    fun convertSaveTime(time: String): String {

        val splitTime = time.split(" ")

        val splitHour = if (splitTime[1].substring(
                0,
                splitTime[1].length - 1
            ).toInt() / 10 == 0
        ) {
            if (splitTime[0] == "오후") {
                "${(splitTime[1].substring(0, splitTime[1].length - 1)).toInt() + 12}"
            } else {
                "0" + "${(splitTime[1].substring(0, splitTime[1].length - 1)).toInt()}"
            }
        } else {
            if (splitTime[0] == "오후") {
                if ((splitTime[1].substring(0, splitTime[1].length - 1)) == "12") {
                    "${(splitTime[1].substring(0, splitTime[1].length - 1)).toInt()}"
                } else {
                    "${(splitTime[1].substring(0, splitTime[1].length - 1)).toInt() + 12}"
                }
            } else {
                "${(splitTime[1].substring(0, splitTime[1].length - 1)).toInt()}"
            }
        }

        val minute = splitTime[2].substring(
            0,
            splitTime[2].length - 1
        )

        return splitTime[0] + splitHour + minute
    }

    fun convertPickerTime(hour: Int, minute: Int): String {
        val calendar = Calendar.getInstance().apply {
            if (hour > 12) {
                set(Calendar.HOUR, hour - 12)
                set(Calendar.MINUTE, minute)
            } else {
                set(Calendar.HOUR, hour + 12)
                set(Calendar.MINUTE, minute)
            }
        }
        return SimpleDateFormat("a h시 mm분").format(calendar.time)

    }

    fun convertShowTime(time: String): String {
        val getAmPm = time.substring(0, 2)
        val getHour =
            if (time.substring(2, 4).toInt() > 12) {
                "${time.substring(2, 4).toInt() - 12}"
            } else {
                if (time.substring(2, 4).toInt() / 10 == 0) {
                    "${time[3]}"
                } else {
                    "${time.substring(2, 4).toInt()}"
                }
            }
        val getMinute = time.substring(4)
        return "$getAmPm $getHour" + "시 " + getMinute + "분"
    }


}