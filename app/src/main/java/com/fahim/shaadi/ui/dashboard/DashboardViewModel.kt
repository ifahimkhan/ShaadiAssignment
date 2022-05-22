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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DashboardViewModel @ViewModelInject constructor(
    private val repo: ProfileInterface,
) : ViewModel() {

    private val profileMutableLiveData = MutableLiveData<Resource<ApiProfileResponse>>()
    val profileLiveData: LiveData<Resource<ApiProfileResponse>>
        get() = profileMutableLiveData

    private var localProfileMutableLiveData = repo.getLocalProfiles()
    val localProfileLiveData: LiveData<List<ProfileModel>>
        get() = localProfileMutableLiveData

    private val _text = MutableLiveData<String>().apply {
        value = "No Data Available"
    }
    val text: LiveData<String> = _text

    val declinedList = ArrayList<ProfileModel>()
    val acceptedList = ArrayList<ProfileModel>()

    fun getProfiles() {
        updateList(isAllSwiped = true)
        viewModelScope.launch {
            profileMutableLiveData.value = Resource.loading(null)
            val data = repo.getOnlineProfile("10")
            if (data.status == Status.SUCCESS) {
                data.data?.let { repo.insertAllProfile(it.results.map { convertToProfileModel(it) }) }
            }
            profileMutableLiveData.value = data

        }
    }

    public fun convertToProfileModel(result: Results): ProfileModel {
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
//            repo.updateProfile(profileModel = updated)
            acceptedList.add(updated)
        }
    }

    fun declinedProfile(item: ProfileModel) {

        val updated = item.copy(isAccepted = 0)
        Log.e("TAG", "acceptedProfile: " + updated.isAccepted)
//            repo.updateProfile(profileModel = updated)
        declinedList.add(updated)

    }

    fun deleteProfile(profileModel: ProfileModel?) {
        profileModel?.let {
            viewModelScope.launch {
                repo.deleteProfile(profileModel)
            }
        }
    }

    fun updateList(isAllSwiped: Boolean) {

        Log.e("Before", "updateList: ${acceptedList.size} - ${declinedList.size}")
        viewModelScope.launch(Dispatchers.IO) {
            val iterator: MutableIterator<ProfileModel> = declinedList.iterator()
            while (iterator.hasNext()) {
                val value = iterator.next()
                repo.updateProfile(value)
                iterator.remove()
            }
            val iteratorAccepted: MutableIterator<ProfileModel> = acceptedList.iterator()
            while (iteratorAccepted.hasNext()) {
                val value = iteratorAccepted.next()
                repo.updateProfile(value)
                iteratorAccepted.remove()
            }
            Log.e("After", "updateList: ${acceptedList.size} - ${declinedList.size}")

//            if (isAllSwiped) localProfileMutableLiveData = repo.getLocalProfiles()
        }

    }


}