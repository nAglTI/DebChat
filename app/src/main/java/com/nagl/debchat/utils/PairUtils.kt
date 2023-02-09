package com.nagl.debchat.utils

import com.nagl.debchat.App
import com.nagl.debchat.R

object PairUtils {
    @JvmStatic
    fun getCabinetString(classNumber: String): String {
        return App.getContext().resources.getString(R.string.str_class, classNumber.trim())
    }
    @JvmStatic
    fun getTeacherName(teacher: String): String {
        return teacher.trim()
    }

    @JvmStatic
    fun getPairTime(pairNumber: Int): String {
        return App.getContext().resources.getString(R.string.str_pair_time) + when (pairNumber) {
            1 -> " 9:00 - 10:30"
            2 -> " 10:40 - 12:10"
            3 -> " 12:50 - 14:20"
            4 -> " 14:30 - 16:00"
            5 -> " 16:10 - 17:40"
            6 -> " 17:50 - 19:20"
            else -> " No time to die!"
        }
    }

    @JvmStatic
    fun pairNumberToString(pairNumber: Int): String = pairNumber.toString()

    @JvmStatic
    fun getDayOfWeekByNumber(dayNumber: Int): String {
        val res = App.getContext().resources
        return when (dayNumber) {
            1 -> res.getStringArray(R.array.str_days_of_week)[0]
            2 -> res.getStringArray(R.array.str_days_of_week)[1]
            3 -> res.getStringArray(R.array.str_days_of_week)[2]
            4 -> res.getStringArray(R.array.str_days_of_week)[3]
            5 -> res.getStringArray(R.array.str_days_of_week)[4]
            6 -> res.getStringArray(R.array.str_days_of_week)[5]
            else -> res.getStringArray(R.array.str_days_of_week)[0]
        }
    }
}