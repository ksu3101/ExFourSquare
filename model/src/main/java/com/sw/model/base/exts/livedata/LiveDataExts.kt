package com.sw.model.base.exts.livedata

import androidx.lifecycle.MutableLiveData

/**
 * @author burkd
 * @since 2019-11-15
 */

fun MutableLiveData<Boolean>.setTrue() {
    this.value = true
}

fun MutableLiveData<Boolean>.setFalse() {
    this.value = false
}
