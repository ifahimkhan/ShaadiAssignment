package com.fahim.shaadi.ui.declined

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DeclinedViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "No Declined Item"
    }
    val text: LiveData<String> = _text
}