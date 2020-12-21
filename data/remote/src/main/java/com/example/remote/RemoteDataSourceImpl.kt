package com.example.remote

import com.example.remote.models.toBo
import com.example.remote.service.RemoteService
import com.example.remote.service.handleResponse
import com.example.remote.service.map
import com.example.repository.datasource.remote.RemoteDataSource
import com.example.repository.datasource.remote.RemoteResponse
import com.example.repository.models.ChampionBo
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val service: RemoteService) : RemoteDataSource {

    override suspend fun getChampList(): RemoteResponse<List<ChampionBo>> {
        val response = handleResponse {
            service.getChampList()
        }
        return response.map { champList ->
            champList.map {
                it.toBo()
            }
        }
    }

}