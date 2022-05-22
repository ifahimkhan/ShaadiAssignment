package com.fahim.shaadi.ui.accepted

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fahim.shaadi.data.database.ProfileModel
import com.fahim.shaadi.data.repository.ProfileInterface
import kotlinx.coroutines.launch

class AcceptedViewModel @ViewModelInject constructor(
    private val repo: ProfileInterface,
)  : ViewModel() {
    fun updateProfile(profile: ProfileModel) {
        viewModelScope.launch { repo.updateProfile(profile) }
    }

    private val _text = MutableLiveData<String>().apply {
        value = "No Accepted Item"
    }
    val text: LiveData<String> = _text

    val acceptedProfiles = repo.getAcceptedLocalProfiles()

}