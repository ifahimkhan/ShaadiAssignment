package com.fahim.shaadi.data.model

import com.google.gson.annotations.SerializedName


data class ApiProfileResponse (

  @SerializedName("results" ) var results : ArrayList<Results> = arrayListOf(),
  @SerializedName("info"    ) var info    : Info?              = Info()

)