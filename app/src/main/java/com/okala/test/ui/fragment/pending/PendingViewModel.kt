package com.okala.test.ui.fragment.pending

import com.android.networkapi.domain.usecase.getbarcodlist.GetBarcodeList
import com.android.networkapi.data.model.getbarcodlist.GetQrCodeList
import com.android.networkapi.domain.usecase.getbarcodlist.getListOfBarcode
import com.okala.test.utils.base.BaseViewModel
import com.okala.test.utils.SingleLiveEvent

class PendingViewModel : BaseViewModel(), GetBarcodeList {

    val liveResponse =
        SingleLiveEvent<GetQrCodeList>()
    val liveSaveQuantityResponseError =
        SingleLiveEvent<String>()

    fun getBarcodeList( page: Int){
    getListOfBarcode(page,done = {
        liveResponse.value = it
    },errorState = {
        liveSaveQuantityResponseError.value = "api error"
    })

    }
}
