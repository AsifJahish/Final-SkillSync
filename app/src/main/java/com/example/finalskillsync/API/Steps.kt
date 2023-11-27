package com.example.finalskillsync.API

import com.google.gson.annotations.SerializedName


data class Steps (

  @SerializedName("number"      ) var number      : Int?                   = null,
  @SerializedName("step"        ) var step        : String?                = null,
  @SerializedName("ingredients" ) var ingredients : ArrayList<Ingredients> = arrayListOf(),
  @SerializedName("equipment"   ) var equipment   : ArrayList<Equipment>   = arrayListOf()

)