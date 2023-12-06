package com.example.finalskillsync.API.Model

import com.google.gson.annotations.SerializedName

data class Quotes (
    @SerializedName("text"   ) var text   : String? = null,
    @SerializedName("author" ) var author : String? = null

)