package com.android.networkapi.utils


import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import okhttp3.ResponseBody


class Utils {
    companion object {
        fun <T> mapperTojson(tClass: Class<T>?, data: JsonObject?): T {

            val gson = GsonBuilder().serializeNulls().create()
            val gsonLog = GsonBuilder().setPrettyPrinting().create()
            Log.e("<<<<<<<<" + tClass?.simpleName + ">>>>>>>>", gsonLog.toJson(data))
            return gson.fromJson(gson.toJson(data), tClass)
        }

        fun <T> mapperTojsonResponseBody(tClass: Class<T>?, data: ResponseBody?): T {

            val gson = GsonBuilder().serializeNulls().create()
            val gsonLog = GsonBuilder().setPrettyPrinting().create()
            Log.e("<<<<<<<<" + tClass?.simpleName + ">>>>>>>>", gsonLog.toJson(data))
            return gson.fromJson(gson.toJson(data), tClass)
        }

    }
}