package com.sw.model

import com.sw.model.base.helper.ResourceHelper
import com.sw.model.base.helper.SharedPreferenceHelper
import com.sw.model.base.redux.ActionProcessorMiddleWare
import com.sw.model.base.redux.CombinedActionProcessors
import com.sw.model.base.redux.Middleware
import com.sw.model.base.redux.State
import com.sw.model.domain.AppReducer
import com.sw.model.domain.AppState
import com.sw.model.domain.AppStore
import com.sw.model.domain.auth.FourSquareAuthActionProcessor
import com.sw.model.domain.auth.FourSquareAuthReducer
import com.sw.model.domain.auth.FourSquareAuthState
import com.sw.model.domain.auth.InitializedState
import com.sw.model.domain.auth.ReceivedActingUserDetails
import com.sw.model.domain.auth.RequestAccessTokenByAuthCodeAction
import com.sw.model.domain.auth.dto.AccessToken
import com.sw.model.domain.auth.dto.DUMMY_USER
import com.sw.model.domain.auth.repos.FourSquareAuthRepository
import com.sw.model.domain.auth.repos.FourSquareUserRepository
import com.sw.model.domain.common.HandledMessageState
import com.sw.model.domain.common.MessageReducer
import com.sw.model.domain.common.MessageState
import com.sw.model.utils.given
import com.sw.model.utils.mock
import com.sw.model.utils.willReturn
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.observers.TestObserver
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.mockito.ArgumentMatchers.anyString

/**
 * @author burkd
 * @since 2019-11-15
 */
class FourSquareAuthActionProcessorAndReducerTest
    : KoinTest {
    val authRepo: FourSquareAuthRepository = mock()
    val userRepo: FourSquareUserRepository = mock()
    val prefHelper: SharedPreferenceHelper = mock()
    val resourceHelper: ResourceHelper = mock()

    lateinit var stateSubscriber: TestObserver<FourSquareAuthState>
    lateinit var messagerStateSubscriber: TestObserver<MessageState>

    private val scheduler = TestScheduler()
    private val actionProcessor = FourSquareAuthActionProcessor(
        authRepo, userRepo, prefHelper, resourceHelper
    )

    private val testModules = module {
        single { MessageReducer() }
        single { AppState(mutableMapOf()) }
        single { AppStore(AppReducer(), get()) }
        single<Array<Middleware<AppState>>> {
            arrayOf(ActionProcessorMiddleWare(CombinedActionProcessors(listOf(actionProcessor))))
        }
        single<List<FourSquareAuthReducer>> {
            listOf(FourSquareAuthReducer())
        }
    }

    private val store: AppStore by inject()

    @Before
    fun setUp() {
        startKoin {
            modules(testModules)
        }

        RxAndroidPlugins.setMainThreadSchedulerHandler { schedulerCallable -> Schedulers.trampoline() }
        RxJavaPlugins.setIoSchedulerHandler { schedulerCallable -> Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { ignore -> scheduler }

        stateSubscriber = store.getStateListener()
            .map { it.getCurrentState<FourSquareAuthState>(FourSquareAuthReducer::class.java.simpleName) ?: InitializedState }
            .map { it!! }
            .test()
        messagerStateSubscriber = store.getStateListener()
            .map { it.getCurrentState<MessageState>(getKoin().get<MessageReducer>().javaClass.simpleName)}
            .map { it!! }
            .test()
    }

    @After
    fun tearDown() {
        RxAndroidPlugins.reset()
        RxJavaPlugins.reset()
        stopKoin()
    }

    @Test
    fun requestAccessToken_GivenReceiveTokenSuccess_ThenReceivedActingUserDetailsState() {
        val authCode = "1234567898"
        val accessToken = AccessToken("abcdefg01010zxcv")
        given(authRepo.requestAccessToken(anyString())) willReturn Single.just(accessToken)
        given(userRepo.retrieveActingUserSelfDetails()) willReturn Single.just(DUMMY_USER)

        store.dispatch(RequestAccessTokenByAuthCodeAction(authCode))

        stateSubscriber.assertNoErrors()
        stateSubscriber.assertValueCount(3)
        stateSubscriber.assertValueAt(2) {
            if (it is ReceivedActingUserDetails) {
                return@assertValueAt true
            }
            false
        }
        messagerStateSubscriber.assertNoValues()
    }

}
