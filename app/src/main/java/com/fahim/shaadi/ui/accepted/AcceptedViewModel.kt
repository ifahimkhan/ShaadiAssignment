package com.fahim.shaadi.ui.accepted

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AcceptedViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "No Accepted Item"
    }
    val text: LiveData<String> = _text
}