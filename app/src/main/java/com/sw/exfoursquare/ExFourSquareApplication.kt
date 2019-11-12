package com.sw.exfoursquare

import androidx.multidex.MultiDexApplication
import com.sw.exfoursquare.base.di.appModule
import com.sw.exfoursquare.base.di.helpers
import com.sw.exfoursquare.base.di.middleWares
import com.sw.exfoursquare.base.di.reducers
import com.sw.exfoursquare.base.di.repositories
import com.sw.exfoursquare.base.di.viewModels
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * @author burkd
 * @since 2019-11-13
 */
class ExFourSquareApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@ExFourSquareApplication)
            modules(appModule)
            modules(repositories)
            modules(middleWares)
            modules(viewModels)
            modules(helpers)
            modules(reducers)
        }
    }
}