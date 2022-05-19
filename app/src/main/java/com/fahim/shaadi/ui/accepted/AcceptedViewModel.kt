package com.fahim.shaadi.ui.accepted

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fahim.shaadi.data.repository.ProfileInterface

class AcceptedViewModel @ViewModelInject constructor(
    private val repo: ProfileInterface,
)  : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "No Accepted Item"
    }
    val text: LiveData<String> = _text

    val acceptedProfiles = repo.getAcceptedLocalProfiles()

}