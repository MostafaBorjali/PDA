package com.okala.test.utils.customview

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import com.okala.test.R
import com.okala.test.model.SingleToneData

class CustomDialog : DialogFragment() {
    private var dialogTitle: TextView? = null
    private var dialogContent1: TextView? = null
    private var dialogContent2: TextView? = null
    private var dialogContent2Title: TextView? = null
    private var dialogContent3: TextView? = null
    private var dialogButtonOk: AppCompatButton? = null
    private var dialogButtonCancel: AppCompatButton? = null
    private var edDialog: EditText? = null
    private var editLAyout: ConstraintLayout? = null
    private var switchActive = false
    private var switch: Switch? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.custom_dialog, container)
        this.dialogTitle = view.findViewById(R.id.dialogTitle)
        this.dialogContent1 = view.findViewById(R.id.dialogContent1)
        this.dialogContent2 = view.findViewById(R.id.dialogContent2)
        this.dialogContent2Title = view.findViewById(R.id.dialogContent2Title)
        this.dialogContent3 = view.findViewById(R.id.dialogContent3)
        this.dialogButtonOk = view.findViewById(R.id.dialogButtonOk)
        this.dialogButtonCancel = view.findViewById(R.id.dialogButtonCancel)
        this.switch = view.findViewById(R.id.switch1)
        this.editLAyout = view.findViewById(R.id.editLayout)
        this.edDialog = view.findViewById(R.id.edDialog)

        dialog?.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        isCancelable = false

        return view
    }

    @SuppressLint("SetTextI18n", "RtlHardcoded")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when {
            arguments?.getInt(getString(R.string.type)) == 1 -> {
            }
            arguments?.getInt(getString(R.string.type)) == 2 -> {
            }
            arguments?.getInt(getString(R.string.type)) == 3 -> {

                dialogContent1?.text = arguments?.getString(getString(R.string.dialogContent1))
                isCancelable = arguments?.getBoolean(getString(R.string.isclose))!!
                dialogButtonOk?.setOnClickListener {
                    actionOk()
                    dismiss()
                }
                dialogButtonOk?.visibility = View.VISIBLE
                dialogContent1?.visibility = View.VISIBLE
                dialogContent3?.visibility = View.GONE
                dialogContent2?.visibility = View.GONE
                edDialog?.visibility = View.GONE
                dialogContent2Title?.visibility = View.GONE
                dialogButtonCancel?.visibility = View.GONE
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog?.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    companion object {

        lateinit var actionOk: (() -> Unit)
        lateinit var actionLess: (() -> Unit)
        lateinit var actionCancel: (() -> Unit)

        fun BarcodeScannedDialog(
            dialogTitle: String,
            dialogContent1: String,
            dialogContent2: String,
            isClose: Boolean,
            actionA: () -> Unit,
            actionB: () -> Unit,
            actionC: () -> Unit
        ): CustomDialog {
            actionOk = actionA
            actionLess = actionB
            actionCancel = actionC
            val myFragment = CustomDialog()
            val args = Bundle()
            args.putInt("type", 1)
            args.putString("title", dialogTitle)
            args.putString("dialogContent1", dialogContent1)
            args.putString("dialogContent2", dialogContent2)
            args.putBoolean("isclose", isClose)
            myFragment.arguments = args

            return myFragment
        }

        fun BarcodeScannedConfirmLessDialog(
            dialogContent1: String,
            isClose: Boolean,
            actionA: (() -> Unit),
            actionB: (() -> Unit)
        ): CustomDialog {
            actionOk = actionA
            actionCancel = actionB
            val myFragment = CustomDialog()
            val args = Bundle()
            args.putInt("type", 2)
            args.putString("dialogContent1", dialogContent1)
            args.putBoolean("isclose", isClose)
            myFragment.arguments = args

            return myFragment
        }

        fun AlertDialogOkala(
            dialogContent1: String,
            isClose: Boolean,
            actionA: (() -> Unit)
        ): CustomDialog {
            actionOk = actionA

            val myFragment = CustomDialog()
            val args = Bundle()
            args.putInt("type", 3)
            args.putString("dialogContent1", dialogContent1)
            args.putBoolean("isclose", isClose)
            myFragment.arguments = args

            return myFragment
        }
    }


    fun actionIsOK(holder: Int) {
        SingleToneData.quantity = holder
        actionOk()
        dismiss()
    }
}
