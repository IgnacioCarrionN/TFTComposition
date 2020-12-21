package com.example.remote.service

import com.example.remote.models.ChampionDto
import retrofit2.Response
import retrofit2.http.GET


interface RemoteService {

    @GET("v3/ef0ec22f-c5a4-4316-a479-eea14078deae")
    suspend fun getChampList(): Response<List<ChampionDto>>


}