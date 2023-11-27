package com.example.finalskillsync.API

import com.google.gson.annotations.SerializedName


data class ExampleJson2KtKotlin (

  @SerializedName("recipes" ) var recipes : ArrayList<Recipes> = arrayListOf()

)