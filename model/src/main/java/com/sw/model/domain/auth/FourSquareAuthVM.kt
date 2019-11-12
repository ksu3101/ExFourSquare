package com.sw.model.domain.auth

import com.sw.model.base.BaseLifecycleOwnViewModel
import com.sw.model.domain.AppStore

/**
 * @author burkd
 * @since 2019-11-13
 */
class FourSquareAuthVM(
    val appStore: AppStore
) : BaseLifecycleOwnViewModel<FourSquareAuthState>() {

    override fun render(state: FourSquareAuthState): Boolean {

        return true
    }

}