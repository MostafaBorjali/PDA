package com.okala.test.ui.activity

import com.android.networkapi.domain.usecase.getbarcodlist.GetBarcodeList
import com.okala.test.utils.base.BaseViewModel
import com.okala.test.utils.SingleLiveEvent

class MainViewModel : BaseViewModel(), GetBarcodeList {
    val scannedQr = SingleLiveEvent<Boolean>()

}
