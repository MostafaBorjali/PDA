package com.okala.test.ui.fragment.pending

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.android.networkapi.data.model.getbarcodlist.QRData
import com.okala.test.R
import com.okala.test.databinding.FragmentPendingBinding
import com.okala.test.ui.fragment.underreview.DataSCaned
import com.okala.test.utils.base.BaseFragment
import com.okala.test.utils.extention.ALertDialogOkala
import java.util.*

class PendingFragment : BaseFragment<FragmentPendingBinding>() {
    private var page = 1
    private var totalItem = ArrayList<QRData>()
    private var adapter: PendingAdapter? = null
    private val pendingViewModel: PendingViewModel by lazy {
        ViewModelProviders.of(this).get(PendingViewModel::class.java)
    }

    override fun getLayoutResourceId() = R.layout.fragment_pending

    override fun oncreate() {

        viewModelOperation()
        pendingViewModel.getBarcodeList(page)
        binding.recyclerBarcode.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!recyclerView.canScrollVertically(1)) {
                    callApiPageDown()

                }
            }
        })

    }

    private fun callApiPageDown() {
        if (page < 3) {
            page++
            pendingViewModel.getBarcodeList(page)
        }

    }

    private fun viewModelOperation() {
        pendingViewModel.liveResponse.observe(this, Observer {
            fillRecyclerView(it.data)
        })
        pendingViewModel.liveSaveQuantityResponseError.observe(this, Observer {
            activity?.ALertDialogOkala(it)
        })
    }

    private fun fillRecyclerView(data: List<QRData?>?) {
        totalItem.addAll(data as MutableList<QRData>)
        if (adapter == null) {
            adapter = PendingAdapter(totalItem)
            binding.recyclerBarcode.adapter = adapter
        } else {
            adapter!!.notifyItemChanged(totalItem.size - 1)
            adapter!!.notifyDataSetChanged()
        }

    }

    override fun onResume() {
        super.onResume()
        if (!DataSCaned.totalItem.isNullOrEmpty()) {
            totalItem.addAll(DataSCaned.totalItem)
            adapter!!.notifyDataSetChanged()
            adapter!!.notifyItemChanged(totalItem.size - 1)

        }
    }




}
