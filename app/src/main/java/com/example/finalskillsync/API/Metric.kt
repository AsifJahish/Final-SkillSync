package com.example.finalskillsync.API

import com.google.gson.annotations.SerializedName


data class Metric (

  @SerializedName("amount"    ) var amount    : Int?    = null,
  @SerializedName("unitShort" ) var unitShort : String? = null,
  @SerializedName("unitLong"  ) var unitLong  : String? = null

)