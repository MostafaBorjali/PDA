package com.android.networkapi.core

import android.content.res.Resources
import com.android.networkapi.domain.repository.ApiRepository
import com.android.networkapi.domain.usecase.base.ApiResponce
import com.android.networkapi.utils.MyShared
import org.koin.core.KoinComponent
import org.koin.core.inject


object BaseCore : KoinComponent {
    val ms: MyShared by inject()
    val response: ApiResponce by inject()
    val create: ApiRepository by inject()
    val res: Resources by inject()
}


