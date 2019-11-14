package com.sw.model.base.redux

import io.reactivex.Observable

/**
 * @author ksu3101
 * @since 2019-10-15
 */

interface State

interface Action

interface Reducer<S : State> {
    val initializedState: S

    fun reduce(oldState: S, resultAction: Action): S
}

interface Store<S : State> {
    fun dispatch(action: Action)
    fun getStateListener(): Observable<S>
    fun getCurrentState(): S
}

typealias Dispatcher = (Action) -> Unit

interface Middleware<S : State> {
    fun create(store: Store<S>, next: Dispatcher): Dispatcher
}
