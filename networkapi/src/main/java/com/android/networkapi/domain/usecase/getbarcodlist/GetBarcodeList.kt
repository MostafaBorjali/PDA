package com.android.networkapi.domain.usecase.getbarcodlist

import com.android.networkapi.R
import com.android.networkapi.data.model.ResponseState
import com.android.networkapi.core.BaseCore
import com.android.networkapi.data.model.getbarcodlist.GetQrCodeList

interface GetBarcodeList


fun GetBarcodeList.getListOfBarcode(
    pageNumber: Int,
    done: (GetQrCodeList) -> Unit,
    errorState: (String) -> Unit
) {
    BaseCore.apply {
        response.setCall(
            GetQrCodeList::class.java,
            create.get("users?page=$pageNumber")

        ) { responseState, respons ->

            when (responseState) {
                ResponseState.OKAY -> {

                    respons?.let { done(it) }
                }
                ResponseState.InternetFailed -> {
                    errorState(res.getString(R.string.internetError))
                }
                ResponseState.ResponceNot200 -> {
                    errorState(res.getString(R.string.responceNot200))
                }
                ResponseState.SuccessNot3 -> {
                   // errorState(respons?.message.toString())
                }
                ResponseState.ListCountIsZERO -> {
                    errorState(res.getString(R.string.listCountIsZERO))
                }
                ResponseState.RefreshToken ->{
                    errorState(res.getString(R.string.refreshToken))
                }
                ResponseState.ServerError -> {
                    errorState(res.getString(R.string.serverError))
                }
            }
        }
    }

}