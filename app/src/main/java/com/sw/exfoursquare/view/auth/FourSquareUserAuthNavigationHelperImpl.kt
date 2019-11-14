package com.sw.exfoursquare.view.auth

import com.sw.exfoursquare.base.BaseActivity
import com.sw.model.base.helper.NavigationHelper
import com.sw.model.domain.auth.FourSquareAuthState

/**
 *
 * @author burkd
 * @since 2019-11-14
 */
class FourSquareUserAuthNavigationHelperImpl(
    private val activity: BaseActivity
) : NavigationHelper<FourSquareAuthState> {
    private val fm = activity.supportFragmentManager

    override fun handle(state: FourSquareAuthState) {
        when (state) {

        }
    }

}