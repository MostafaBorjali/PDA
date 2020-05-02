package com.okala.test.utils.base


import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.android.networkapi.utils.MyShared
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 * Created by mostafa borjali on 5/27/18.
 */

//BaseAdapter : a generic class  that necessary method  definition in it for recycler view
// @exception ViewDataBinding
// @exception RecyclerView.FinancialReportAdapter

abstract class BaseAdapter<T, B : ViewDataBinding>(var list: List<T?>?) :
    RecyclerView.Adapter<BaseAdapter.ViewHolder<B>>(),KoinComponent {
     val ms: MyShared by inject()
     val res: Resources by inject()


    lateinit var binding: B

    open class ViewHolder<V : ViewDataBinding>(val binding: V) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<B> {

        val inflater = parent.context.getSystemService(
            Context
                .LAYOUT_INFLATER_SERVICE
        ) as LayoutInflater


        binding = DataBindingUtil.inflate(inflater, getLayoutResourceId(), parent, false)

        return ViewHolder(binding)
    }

    protected abstract fun getLayoutResourceId(): Int
    //    protected abstract fun setViews(position: Int, list: List<T>, holder: ViewHolder<*>)
    private fun onBindViewHolder(holder: ViewHolder<*>, position: Int) {

        //setViews(position, list, holder)
    }

    override fun getItemCount(): Int {
        return if (list == null) 0 else list?.size ?: 0
    }


}