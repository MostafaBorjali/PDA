package com.android.networkapi.domain.usecase.base

import com.android.networkapi.data.model.ResponseState

import com.android.networkapi.utils.Utils
import com.google.gson.JsonObject
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback


@Suppress("UNNECESSARY_SAFE_CALL", "UNREACHABLE_CODE")
open class ApiResponce  {

    var call: Call<JsonObject>? = null
    fun <T> setCall(
        tClass: Class<T>?,
        call: Call<JsonObject>?,
        serverListener: (ResponseState, data: T?) -> Unit
    ) {

        this.call = call
        call?.enqueue(object : Callback<JsonObject> {

            override fun onResponse(
                call: Call<JsonObject>,
                response: retrofit2.Response<JsonObject>
            ) {
                when {
                    response.isSuccessful -> {

                        var jsonObject: JSONObject? = null
                        try {
                            jsonObject = JSONObject(response.body().toString())
                        } catch (e: JSONException) {

                            e.printStackTrace()

                        }

                        serverListener(
                            ResponseState.OKAY,
                            tClass?.let { Utils.mapperTojson(tClass, response.body()) }
                                ?: run { null }
                        )
                        return
                        try {
                            if (jsonObject?.getBoolean("success") == true) {

                                if (jsonObject?.getJSONArray("data").length() > 0) {
                                    serverListener(
                                        ResponseState.OKAY,
                                        tClass?.let { Utils.mapperTojson(tClass, response.body()) }
                                            ?: run { null }
                                    )

                                } else {

                                    try {
                                        serverListener(
                                            ResponseState.ListCountIsZERO,
                                            tClass?.let {
                                                Utils.mapperTojson(
                                                    tClass,
                                                    response.body()
                                                )
                                            }
                                                ?: run { null })
                                    } catch (e: java.lang.Exception) {


                                    }


                                }

                                return

                            } else {
                                try {
                                    serverListener(
                                        ResponseState.SuccessNot3,
                                        tClass?.let { Utils.mapperTojson(tClass, response.body()) }
                                            ?: run { null }
                                    )

                                } catch (e: Exception) {
                                    e.printStackTrace()

                                }
                                return
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                            return


                        }


                    }
                    response.code() == 401 -> {
                        /*refreshToken(done = {
                            serverListener(ResponseState.RefreshToken, null)
                        })*/
                    }
                    response.code() == 404 -> {
                        serverListener(ResponseState.ServerError, null)
                    }
                    response.code() >= 500 -> serverListener(ResponseState.ServerError, null)
                    else -> {
                        serverListener(ResponseState.ResponceNot200, tClass?.let {
                            Utils.mapperTojsonResponseBody(tClass, response.errorBody().apply {

                            })
                        } ?: run { null })

                    }
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                serverListener(ResponseState.InternetFailed, null)
            }
        })
    }
}
