package com.android.networkapi.utils

import android.content.Context
import android.content.SharedPreferences

open class MyShared(var context: Context) {
    private lateinit var edit: SharedPreferences.Editor
    open fun setShared(key: String, value: Any?) {
        val userInfo: SharedPreferences =
            context.getSharedPreferences("pda", Context.MODE_PRIVATE)
        edit = userInfo.edit()
        when (value) {
            is String? -> edit.putString(key, value)
            is Int -> edit.putInt(key, value)
            is Boolean -> edit.putBoolean(key, value)
            is Float -> edit.putFloat(key, value)
            is Long -> edit.putLong(key, value)
            else -> throw UnsupportedOperationException("Not yet implemented")
        }
        edit.apply()
        edit.clear()
    }


    open fun getShared(): SharedPreferences {

        return context.getSharedPreferences("pda", 0)

    }

    fun clean() {
        edit.clear()
    }

    var token: String?
        get() = getShared().getString("token", null)
        set(value) = setShared("token", value)

    var isLogIn: Boolean
        get() = getShared().getBoolean("isLogIn", false)
        set(value) = setShared("isLogIn", value)
}




