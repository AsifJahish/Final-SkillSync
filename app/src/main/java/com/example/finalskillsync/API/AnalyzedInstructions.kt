package com.example.finalskillsync.API

import com.google.gson.annotations.SerializedName


data class AnalyzedInstructions (

  @SerializedName("name"  ) var name  : String?          = null,
  @SerializedName("steps" ) var steps : ArrayList<Steps> = arrayListOf()

)