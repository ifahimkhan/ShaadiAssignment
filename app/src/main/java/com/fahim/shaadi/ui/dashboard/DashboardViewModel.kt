package com.fahim.shaadi.ui.dashboard

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fahim.bookapptesting.util.Resource
import com.fahim.bookapptesting.util.Status
import com.fahim.shaadi.data.database.ProfileModel
import com.fahim.shaadi.data.model.ApiProfileResponse
import com.fahim.shaadi.data.model.Results
import com.fahim.shaadi.data.repository.ProfileInterface
import kotlinx.coroutines.launch

class DashboardViewModel @ViewModelInject constructor(
    private val repo: ProfileInterface,
) : ViewModel() {

    private val profileMutableLiveData = MutableLiveData<Resource<ApiProfileResponse>>()
    val profileLiveData: LiveData<Resource<ApiProfileResponse>>
        get() = profileMutableLiveData

    private val localProfileMutableLiveData = repo.getLocalProfiles()
    val localProfileLiveData: LiveData<List<ProfileModel>>
        get() = localProfileMutableLiveData

    private val _text = MutableLiveData<String>().apply {
        value = "No Data Available"
    }
    val text: LiveData<String> = _text

    fun getProfiles() {
        viewModelScope.launch {
            profileMutableLiveData.value = Resource.loading(null)
            val data = repo.getOnlineProfile("10")
            if (data.status == Status.SUCCESS) {
                data.data?.let { repo.insertAllProfile(it.results.map { convertToProfileModel(it) }) }
            }
            profileMutableLiveData.value = data

        }
    }

    private fun convertToProfileModel(result: Results): ProfileModel {
        return ProfileModel(
            name = result.name?.title + " " + result.name?.first + " " + result.name?.last,
            age = result.dob?.age,
            gender = result.gender,
            picture = result.picture?.large,
            email = result.email,
            phone = result.phone,
            city = result.location?.city + "," + result.location?.state,

            )

    }

    fun acceptedProfile(item: ProfileModel) {
        viewModelScope.launch {
            val updated = item.copy(isAccepted = 1)
            Log.e("TAG", "acceptedProfile: " + updated.isAccepted)
            repo.updateProfile(profileModel = updated)
        }
    }

    fun declinedProfile(item: ProfileModel) {
        viewModelScope.launch {
            val updated = item.copy(isAccepted = 0)
            Log.e("TAG", "acceptedProfile: " + updated.isAccepted)
            repo.updateProfile(profileModel = updated)
        }
    }


}