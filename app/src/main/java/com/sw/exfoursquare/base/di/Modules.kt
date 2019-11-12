package com.sw.exfoursquare.base.di

import android.preference.PreferenceManager
import com.example.model.domain.common.MessageReducer
import com.sw.common.IS_MOCK
import com.sw.common.KOIN_CURRENT_ACTIVITY
import com.sw.common.QUALIFIER_FOURSQUARE_AUTH
import com.sw.exfoursquare.base.BaseActivity
import com.sw.exfoursquare.base.helper.FourSquareAuthCodeHelperImpl
import com.sw.exfoursquare.base.helper.MessageHelperImpl
import com.sw.exfoursquare.base.helper.ResourceHelperImpl
import com.sw.exfoursquare.base.helper.SharedPreferenceHelperImpl
import com.sw.exfoursquare.repositories.auth.FourSquareAuthApi
import com.sw.exfoursquare.repositories.auth.FourSquareAuthRepositoryImpl
import com.sw.exfoursquare.repositories.auth.FourSquareAuthRepositoryMockImpl
import com.sw.model.base.BaseLifecycleOwnViewModel
import com.sw.model.base.helper.FourSquareAuthCodeHelper
import com.sw.model.base.helper.MessageHelper
import com.sw.model.base.helper.ResourceHelper
import com.sw.model.base.helper.SharedPreferenceHelper
import com.sw.model.base.redux.ActionProcessorMiddleWare
import com.sw.model.base.redux.CombinedActionProcessors
import com.sw.model.domain.AppReducer
import com.sw.model.domain.AppState
import com.sw.model.domain.AppStore
import com.sw.model.domain.auth.FourSquareAuthActionProcessor
import com.sw.model.domain.auth.FourSquareAuthReducer
import com.sw.model.domain.auth.FourSquareAuthState
import com.sw.model.domain.auth.FourSquareAuthVM
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.Koin
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author ksu3101
 * @since 2019-10-18
 */
val appModule = module {
    single {
        MessageReducer()
    }
    single {
        AppState(mutableMapOf())
    }
    single {
        AppStore(AppReducer(), get())
    }

    single<SharedPreferenceHelper> {
        SharedPreferenceHelperImpl(PreferenceManager.getDefaultSharedPreferences(androidApplication()))
    }
}

private const val TIMEOUT_SEC = 10L
val repositories = module {
    single {
        OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_SEC, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SEC, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }
    single(named(QUALIFIER_FOURSQUARE_AUTH)) {
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl("https://foursquare.com/oauth2")
            .client(get<OkHttpClient>())
            .build()
    }

    single<FourSquareAuthApi> {
        get<Retrofit>(named(QUALIFIER_FOURSQUARE_AUTH))
            .create(FourSquareAuthApi::class.java)
    }
    single { (isMock: Boolean) ->
        if (isMock)
            FourSquareAuthRepositoryMockImpl()
        else
            FourSquareAuthRepositoryImpl(get())
    }
}

val middleWares = module {
    single {
        ActionProcessorMiddleWare(
            CombinedActionProcessors(
                listOf(
                    FourSquareAuthActionProcessor(
                        get(),
                        get(parameters = { parametersOf(IS_MOCK) }),
                        get(),
                        get()
                    )

                )
            )
        )
    }
}

val helpers = module {
    // todo : create activity scope
    single<FourSquareAuthCodeHelper> { FourSquareAuthCodeHelperImpl(getKoin().currentActivity()) }
    single<MessageHelper> { MessageHelperImpl(getKoin().currentActivity()) }
    single<ResourceHelper> { ResourceHelperImpl(androidApplication()) }
}

val reducers = module {
    single { FourSquareAuthReducer() }
}

val viewModels = module {
    viewModel<BaseLifecycleOwnViewModel<FourSquareAuthState>> { FourSquareAuthVM(get(), get(), get()) }
}

private fun Koin.currentActivity(): BaseActivity {
    return getProperty(KOIN_CURRENT_ACTIVITY)
        ?: throw NullPointerException("CurrentActivity is Null.")
}