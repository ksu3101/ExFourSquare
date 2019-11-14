package com.sw.model.domain.auth

import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sw.common.extensions.appendOf
import com.sw.model.base.BaseLifecycleOwnViewModel
import com.sw.model.base.exts.livedata.setFalse
import com.sw.model.base.exts.livedata.setTrue
import com.sw.model.base.helper.FourSquareAuthCodeHelper
import com.sw.model.base.helper.SharedPreferenceHelper
import com.sw.model.domain.AppStore

/**
 * @author burkd
 * @since 2019-11-13
 */
class FourSquareAuthVM(
    private val appStore: AppStore,
    private val prefHelper: SharedPreferenceHelper,
    private val authHelper: FourSquareAuthCodeHelper
) : BaseLifecycleOwnViewModel<FourSquareAuthState>() {

    private val _isConnectionCompleted = MutableLiveData<Boolean>()
    val isConnectionCompleted: LiveData<Boolean>
        get() = _isConnectionCompleted

    private val _isAuthenticationFailed = MutableLiveData<Boolean>()
    val isAuthenticationFailed: LiveData<Boolean>
        get() = _isAuthenticationFailed

    private val _userImage = MutableLiveData<String>()
    val userImage: LiveData<String>
        get() = _userImage

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String>
        get() = _userName

    private val _userLocation = MutableLiveData<String>()
    val userLocation: LiveData<String>
        get() = _userLocation

    private val _tipsCounter = MutableLiveData<Int>()
    val tipsCounter: LiveData<Int>
        get() = _tipsCounter

    private val _photosCounter = MutableLiveData<Int>()
    val photosCounter: LiveData<Int>
        get() = _photosCounter

    private val _followersCounter = MutableLiveData<Int>()
    val followersCounter: LiveData<Int>
        get() = _followersCounter

    val myPlacesGroupItems: ObservableList<FourSquarePlaceGroupItemVM> =
        ObservableArrayList<FourSquarePlaceGroupItemVM>()
    val rvItemsOnClickedListner: ((FourSquarePlaceGroupItemVM) -> Unit) = { item ->

    }

    init {
        _isConnectionCompleted.setFalse()
        _isAuthenticationFailed.setFalse()

        prefHelper.removeAccessToken()  // reset access token

        // get new accesstoken
        authHelper.connectToFourSquare()
    }

    fun onClickedRetryToRequestAuthentication() {
        appStore.dispatch(InitlaizeAction)  // reset states
        authHelper.connectToFourSquare()
        _isConnectionCompleted.setFalse()
        _isAuthenticationFailed.setFalse()
    }

    override fun render(state: FourSquareAuthState): Boolean {
        when (state) {
            is ReceiveActingUserDetailFailed -> {
                _isConnectionCompleted.setFalse()
                _isAuthenticationFailed.setTrue()
                return true
            }
            is ReceivedActingUserDetails -> {
                _isAuthenticationFailed.setFalse()
                _isConnectionCompleted.setTrue()
                _userImage.value = state.user.photo.getImageUrlWithSize()
                _userName.value = "${state.user.lastName} ${state.user.firstName}"
                _userLocation.value = state.user.homeCity
                _tipsCounter.value = state.user.tips.count
                _photosCounter.value = state.user.photos.count
                _followersCounter.value = state.user.friends.count

                var appendedGroupNames = ""
                myPlacesGroupItems.clear()
                state.user.lists.groups.map {
                    it.items.map { item ->
                        appendedGroupNames = appendedGroupNames.appendOf("${item.name} ")
                    }
                }
                myPlacesGroupItems.add(FourSquarePlaceGroupItemVM(appendedGroupNames))
                return true
            }
        }
        return false
    }

}
