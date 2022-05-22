package com.fahim.shaadi.ui.declined

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fahim.shaadi.data.database.ProfileModel
import com.fahim.shaadi.data.repository.ProfileInterface
import kotlinx.coroutines.launch

class DeclinedViewModel @ViewModelInject constructor(
    private val repo: ProfileInterface,
) : ViewModel() {
    fun updateProfile(profile: ProfileModel) {
        viewModelScope.launch { repo.updateProfile(profile) }
    }
    val declinedProfiles = repo.getDeclinedLocalProfiles()
    private val _text = MutableLiveData<String>().apply {
        value = "No Declined Item"
    }
    val text: LiveData<String> = _text
}