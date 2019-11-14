package com.sw.model.domain.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @author burkd
 * @since 2019-11-15
 */
class FourSquarePlaceGroupItemVM(
    myPlacesListName: String
) : ViewModel() {
    private val _myPlacesListName = MutableLiveData<String>()
    val myPlacesListName: LiveData<String>
        get() = _myPlacesListName

    init {
        _myPlacesListName.value = myPlacesListName
    }
}