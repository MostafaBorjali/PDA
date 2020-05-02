package com.okala.test.ui.fragment.pending

import android.annotation.SuppressLint
import com.android.networkapi.data.model.getbarcodlist.QRData
import com.okala.test.R
import com.okala.test.databinding.RowBarcodeListFragmentPendingBinding
import com.okala.test.utils.base.BaseAdapter
import com.squareup.picasso.Picasso


class PendingAdapter(list: List<QRData?>?) :
    BaseAdapter<QRData?, RowBarcodeListFragmentPendingBinding>(list) {


    override fun getLayoutResourceId(): Int {
        return R.layout.row_barcode_list_fragment_pending
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder<RowBarcodeListFragmentPendingBinding>, position: Int) {
        val binding = holder.binding
        list?.let {
            binding.barcodeKala.text = list!![position]?.first_name +" " + list!![position]?.last_name
            binding.tedadKol.text = list!![position]?.email
            Picasso.get().load(list!![position]?.avatar).into(binding.imageView11);        }


    }

}


