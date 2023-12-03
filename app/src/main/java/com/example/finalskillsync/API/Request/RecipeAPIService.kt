package com.example.finalskillsync.API.Request

import com.example.finalskillsync.API.Model.TodoList
import com.google.android.gms.common.api.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface RecipeAPIService {

        @GET("api/activity/")
        suspend fun getALl(
        ): retrofit2.Response<List<TodoList>>
    }
