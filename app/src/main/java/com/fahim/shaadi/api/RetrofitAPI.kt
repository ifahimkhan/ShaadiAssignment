package com.fahim.shaadi.api

import com.fahim.shaadi.data.model.ApiProfileResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitAPI {
    //    https://randomuser.me/api/?results=10
    @GET("/api/")
    suspend fun getProfiles(
        @Query("results") q: String,
    ): Response<ApiProfileResponse>
}
