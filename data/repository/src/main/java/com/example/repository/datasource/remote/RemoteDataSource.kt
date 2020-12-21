package com.example.repository.datasource.remote

import com.example.repository.models.ChampionBo

interface RemoteDataSource {

    suspend fun getChampList(): RemoteResponse<List<ChampionBo>>

}