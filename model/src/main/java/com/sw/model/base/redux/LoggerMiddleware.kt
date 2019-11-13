package com.sw.model.base.redux

import android.util.Log
import com.sw.common.BuildConfig
import com.sw.common.LOG_TAG
import com.sw.common.extensions.getSuperClassNames
import com.sw.model.domain.AppState

/**
 * @author burkd
 * @since 2019-11-13
 */
class LoggerMiddleware<S : State> : Middleware<S> {

    override fun create(store: Store<S>, next: Dispatcher): Dispatcher {
        return { action: Action ->
            if (BuildConfig.DEBUG) {
                Log.d(LOG_TAG, "action dispatch : [${action.getSuperClassNames()}] $action")
            }
            val prevState = store.getCurrentState()
            next(action)

            if (BuildConfig.DEBUG) {
                val currentState = store.getCurrentState()
                if (prevState != currentState) {
                    (currentState as AppState).printStateLogs()
                }
            }
        }
    }

}