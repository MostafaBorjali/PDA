package com.okala.test.ui.fragment.underreview

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.android.networkapi.data.model.getbarcodlist.QRData
import com.google.zxing.Result
import com.okala.test.model.DataScanned
import com.okala.test.utils.extention.ALertDialogOkala
import me.dm7.barcodescanner.zxing.ZXingScannerView


class UnderReviewFragment : Fragment(), ZXingScannerView.ResultHandler {

    private var mScannerView: ZXingScannerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mScannerView = ZXingScannerView(activity)
        return mScannerView
    }

    override fun onResume() {
        super.onResume()
        permissions()
        DataScanned.totalItem.clear()
        mScannerView!!.setResultHandler(this)
        mScannerView!!.startCamera()
    }

    override fun handleResult(rawResult: Result) {
        val name = rawResult.text.toString().split("-")
        val lastname = name[1].split("@")
        activity?.ALertDialogOkala(dialogContent1 = rawResult.text) {
            DataScanned.totalItem.add(
                QRData(
                    email = rawResult.text,
                    avatar = "https://s3.amazonaws.com/uifaces/faces/twitter/bigmancho/128.jpg",
                    first_name = name[0],
                    id = -5,
                    last_name = lastname[0]
                )

            )
        }

        val handler = Handler()
        handler.postDelayed({ mScannerView?.resumeCameraPreview(this) }, 2000)
    }

    override fun onPause() {
        super.onPause()
        mScannerView?.stopCamera()
    }

    private fun permissions() {
        val permissionCheck = ContextCompat.checkSelfPermission(
            activity!!,
            Manifest.permission.CAMERA
        )
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            activity!!.ALertDialogOkala(dialogContent1 = "شما به دوربین دستری ندارید!")
        }

    }
}