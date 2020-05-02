package com.android.networkapi.domain.repository


import com.google.gson.JsonObject
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*


interface ApiRepository {
    //////// postApi hash map
   /* @FormUrlEncoded
    @POST("/C/CustomerAccount/OTPRegister")
    fun connectToken(@FieldMap map: HashMap<String, Any>): Call<JsonObject>
*/
    @POST
    fun post(@Url url: String, @Body body: Any): Call<JsonObject>


    @Multipart
    @POST
    fun upload(@Url url: String, @Part file: MultipartBody.Part, @Part("name") name: String): Call<JsonObject>

    @PUT
    fun put(@Url url: String, @Body body: Any): Call<JsonObject>

    @GET
    fun get(@Url url: String): Call<JsonObject>

    @DELETE
    fun delete(@Url url: String): Call<JsonObject>


}
