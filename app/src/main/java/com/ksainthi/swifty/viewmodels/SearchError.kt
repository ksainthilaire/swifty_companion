package com.ksainthi.swifty.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class SearchError : ViewModel() {


    private val _text = MutableLiveData<String>()

    val text: LiveData<String> get() = _text

    private val _isHidden = MutableLiveData(true)
    val isHidden: LiveData<Boolean> get() = _isHidden



    fun createError(errorText: String) {
        _text.postValue(errorText)
        _isHidden.postValue(false)
    }

}