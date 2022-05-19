package com.fahim.shaadi.ui.declined

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fahim.shaadi.data.repository.ProfileInterface

class DeclinedViewModel @ViewModelInject constructor(
    private val repo: ProfileInterface,
) : ViewModel() {

    val declinedProfiles = repo.getDeclinedLocalProfiles()
    private val _text = MutableLiveData<String>().apply {
        value = "No Declined Item"
    }
    val text: LiveData<String> = _text
}