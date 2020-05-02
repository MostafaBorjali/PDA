package com.okala.test.utils.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import com.android.networkapi.domain.usecase.base.ApiResponce
import com.android.networkapi.utils.MyShared
import org.koin.android.ext.android.inject

abstract class BaseFragment<B : ViewDataBinding> : Fragment(), LifecycleObserver {
    lateinit var binding: B
    val ms: MyShared by inject()
    private var vieww: View? = null
    val response: ApiResponce by inject()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (vieww == null) {
            binding = DataBindingUtil.inflate(inflater, getLayoutResourceId(), container, false)

            vieww = binding.root

            oncreate()
        }

        return binding.root
    }

    protected abstract fun getLayoutResourceId(): Int
    protected abstract fun oncreate()
}
