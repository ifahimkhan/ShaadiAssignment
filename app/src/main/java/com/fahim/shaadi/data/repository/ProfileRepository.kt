package com.fahim.shaadi.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.fahim.bookapptesting.util.Resource
import com.fahim.shaadi.api.RetrofitAPI
import com.fahim.shaadi.data.database.ProfileDAO
import com.fahim.shaadi.data.database.ProfileModel
import com.fahim.shaadi.data.model.ApiProfileResponse
import com.google.gson.Gson
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val dao: ProfileDAO,
    private val retrofitAPI: RetrofitAPI
) : ProfileInterface {
    override suspend fun insertProfile(profileModel: ProfileModel) {
        dao.insertProfile(profileModel)
    }

    override suspend fun deleteProfile(profileModel: ProfileModel) {
        dao.deleteProfile(profileModel)
    }

    override suspend fun updateProfile(profileModel: ProfileModel) {
        dao.updateProfile(profileModel)
    }


    override suspend fun getOnlineProfile(count: String): Resource<ApiProfileResponse> {
        return try {

            val response = retrofitAPI.getProfiles(count)
//            Log.e("TAG", "getOnlineProfile: ${call.request().url().toString()}" )
//            val response = call.execute()
//            Log.e("TAG", "getOnlineProfile: ${Gson().toJson(response)}")
            Log.e("TAG", "getOnlineProfile: " + response.isSuccessful)
            if (response.isSuccessful) {

                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error", null)
            } else {
                Resource.error("No data", null)
            }
        } catch (e: Exception) {
            Resource.error("No data ${e.message}", null)
        }
    }

    override fun getLocalProfiles(): LiveData<List<ProfileModel>> {
        return dao.observeProfile()
    }


}