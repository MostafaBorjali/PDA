package com.okala.test.utils.extention

import androidx.fragment.app.FragmentActivity
import com.okala.test.R
import com.okala.test.utils.customview.CustomDialog

fun FragmentActivity.BarcodeScannedDialog(
    dialogTitle: String? = null,
    dialogContent1: String,
    dialogContent2: String,
    isClose: Boolean? = null,
    actionOk: (() -> Unit)? = null,
    actionLess: (() -> Unit)? = null,
    actionCancel: (() -> Unit)? = null
) {
    val fm = this.supportFragmentManager

    CustomDialog.BarcodeScannedDialog(
        dialogTitle ?: "", dialogContent1, dialogContent2, isClose ?: false,
        { actionOk?.invoke() }, { actionLess?.invoke() }, { actionCancel?.invoke() })
        .show(fm, getString(R.string.barcodeScannedDialogTag))
}

fun FragmentActivity.BarcodeScannedConfirmLessDialog(
    dialogContent1: String? = null,
    isClose: Boolean? = null,
    actionOk: (() -> Unit)? = null,
    actionCancell: (() -> Unit)? = null
) {
    val fm = this.supportFragmentManager
    CustomDialog.BarcodeScannedConfirmLessDialog(
        dialogContent1 ?: resources.getString(R.string.barcodeLessMessage), isClose ?: false,
        { actionOk?.invoke() }, { actionCancell?.invoke() }
    ).show(fm, getString(R.string.BarcodeScannedConfirmLessDialogTag))
}

fun FragmentActivity.ALertDialogOkala(
    dialogContent1: String? = null,
    isClose: Boolean? = null,
    actionOk: (() -> Unit)? = null
) {
    val fm = this.supportFragmentManager

    CustomDialog.AlertDialogOkala(
        dialogContent1 ?: resources.getString(R.string.barcodeLessMessage), isClose ?: false

    ) { actionOk?.invoke() }.show(fm, getString(R.string.BarcodeScannedConfirmLessDialogTag))
}
