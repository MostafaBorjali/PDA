package com.android.networkapi.di

import android.content.Context
import com.android.networkapi.domain.repository.ApiRepository
import com.android.networkapi.domain.usecase.base.ApiResponce
import com.android.networkapi.domain.usecase.base.ServiceGenerator
import com.android.networkapi.utils.MyShared
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val sdkModule = module {
    single { MyShared(androidContext()) }
    single { ApiResponce() }
    single { apiInterface(get()) }
    single { androidContext().resources }
}

    fun apiInterface(context: Context): ApiRepository {
        return ServiceGenerator.createService(ApiRepository::class.java, MyShared(context))
    }




