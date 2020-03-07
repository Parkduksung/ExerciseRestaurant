package com.work.restaurant.util

import java.text.SimpleDateFormat
import java.util.*

object DateAndTime {
    fun currentDate(): String {
        val currentTime = Calendar.getInstance().time
        val dateTextAll =
            SimpleDateFormat("yyyy-M-d", Locale.getDefault()).format(currentTime)
        val dateArray = dateTextAll.split("-")
        return "${dateArray[0]}년 ${dateArray[1]}월 ${dateArray[2]}일"
    }

    fun currentTime(): String {
        val currentTime = Calendar.getInstance().time
        return SimpleDateFormat("ahhmm", Locale.getDefault()).format(currentTime)
    }

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

        val getAmPm = if (hour >= 12) {
            "오후"
        } else {
            "오전"
        }
        val getHour = if (hour > 12) {
            "${hour - 12}시"
        } else {
            "${hour}시"
        }
        val getMinute = if (minute / 10 == 0) {
            "0${minute}분"
        } else {
            "${minute}분"
        }
        return "$getAmPm $getHour $getMinute"
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