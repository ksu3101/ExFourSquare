package com.sw.model.domain.auth

import com.sw.model.base.BaseLifecycleOwnViewModel
import com.sw.model.base.helper.FourSquareAuthCodeHelper
import com.sw.model.base.helper.SharedPreferenceHelper
import com.sw.model.domain.AppStore

/**
 * @author burkd
 * @since 2019-11-13
 */
class FourSquareAuthVM(
    val appStore: AppStore,
    val prefHelper: SharedPreferenceHelper,
    val authHelper : FourSquareAuthCodeHelper
) : BaseLifecycleOwnViewModel<FourSquareAuthState>() {

    init {
        prefHelper.removeAccessToken()  // reset access token

        // get new accesstoken
        authHelper.connectToFourSquare()
    }

    override fun render(state: FourSquareAuthState): Boolean {
        //Log.w("AuthVM", "$state")
        return true
    }

}