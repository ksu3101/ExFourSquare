package com.sw.model.domain

import android.util.Log
import com.sw.common.LOG_TAG
import com.sw.common.extensions.getSuperClassNames
import com.sw.model.base.redux.Action
import com.sw.model.base.redux.Dispatcher
import com.sw.model.base.redux.Middleware
import com.sw.model.base.redux.Reducer
import com.sw.model.base.redux.State
import com.sw.model.base.redux.Store
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.BehaviorSubject
import org.koin.core.KoinComponent

/**
 * @author ksu3101
 * @since 2019-10-22
 */

class AppStore(
    val reducer: Reducer<AppState>,
    initializedState: AppState
) : Store<AppState>, KoinComponent {
    private val stateEmitter: BehaviorSubject<AppState> = BehaviorSubject.create()
    private val middleWares: Array<Middleware<AppState>> = getKoin().get()
    private var state: AppState = initializedState
    private var dispatcher: Dispatcher = { action: Action ->
        state = reducer.reduce(getCurrentState(), action)
        stateEmitter.onNext(state)
    }

    init {
        dispatcher = middleWares.foldRight(dispatcher) { middleWare, next ->
            middleWare.create(this, next)
        }
    }

    override fun getStateListener(): Observable<AppState> =
        stateEmitter.hide().observeOn(AndroidSchedulers.mainThread())

    override fun getCurrentState(): AppState = state

    override fun dispatch(action: Action) {
        dispatcher(action)
    }
}

data class AppState(
    val states: Map<String, State>
) : State {
    inline fun <reified S : State> getCurrentState(key: String): S? {
        val currentState = states.get(key) ?: return null
        return currentState as S
    }

    fun printStateLogs() {
        Log.d(LOG_TAG, "AppState (\n")
        for (state in states.values) {
            Log.d(LOG_TAG, String.format("\t[%-24s]\t%s \n", state.getSuperClassNames(), state))
        }
        Log.d(LOG_TAG, ")")
    }
}

class AppReducer : Reducer<AppState>, KoinComponent {
    override val initializedState: AppState = getKoin().get()

    override fun reduce(oldState: AppState, resultAction: Action): AppState {
        return reduces<State, Reducer<State>>(oldState, resultAction)
    }

    private inline fun <reified S : State, reified R : Reducer<S>> reduces(
        oldState: AppState,
        resultAction: Action
    ): AppState {
        val states = mutableMapOf<String, S>()
        getReducers<S, R>().map {
            val reducerName = it.javaClass.simpleName
            states.put(
                reducerName,
                it.reduce(oldState.getCurrentState(reducerName) ?: it.initializedState, resultAction)
            )
        }
        return AppState(states)
    }

    private inline fun <reified S : State, reified R : Reducer<S>> getReducers(): List<R> =
        getKoin().get()

}

