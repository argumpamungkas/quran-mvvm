package com.argumelar.quranmvvm

import android.app.Application
import com.argumelar.quranmvvm.model.repositoryModule
import com.argumelar.quranmvvm.network.networkModule
import com.argumelar.quranmvvm.ui.detail.detailViewModel
import com.argumelar.quranmvvm.ui.detail.moduleDetail
import com.argumelar.quranmvvm.ui.home.homeModule
import com.argumelar.quranmvvm.ui.home.homeViewModel
import com.argumelar.quranmvvm.ui.moduleSplash
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

//BASE CLASS
class QuranApps: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@QuranApps)
            modules(
                networkModule,
                repositoryModule,
                moduleSplash,
                homeViewModel,
                homeModule,
                moduleDetail,
                detailViewModel
            )
        }
    }
}