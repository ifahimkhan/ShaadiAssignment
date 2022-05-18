package com.fahim.shaadi.data.model

import com.google.gson.annotations.SerializedName


data class Info (

  @SerializedName("seed"    ) var seed    : String? = null,
  @SerializedName("results" ) var results : Int?    = null,
  @SerializedName("page"    ) var page    : Int?    = null,
  @SerializedName("version" ) var version : String? = null

)