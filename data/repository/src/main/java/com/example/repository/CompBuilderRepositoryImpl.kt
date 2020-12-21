package com.example.repository

import com.example.repository.datasource.local.LocalDataSource
import com.example.repository.datasource.remote.RemoteDataSource
import com.example.repository.datasource.remote.RemoteResponse
import com.example.repository.models.ChampionBo
import com.example.repository.models.CompositionBo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CompBuilderRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : CompBuilderRepository {

    override suspend fun getChampList(): Flow<List<ChampionBo>> {
        return localDataSource.getChampList()
    }

    // TODO: Improve error handling in this case
    override suspend fun updateChampList(): Boolean {
        return when (val remoteResponse = remoteDataSource.getChampList()) {
            is RemoteResponse.Success -> {
                localDataSource.updateChampList(remoteResponse.data)
                true
            }
            is RemoteResponse.Error -> {
                false
            }
        }
    }

    override suspend fun getCompList(): Flow<List<CompositionBo>> {
        return localDataSource.getCompList()
    }

    override suspend fun getComp(id: Long): Flow<CompositionBo?> {
        return localDataSource.getComp(id)
    }

    override suspend fun setComp(comp: CompositionBo): Long {
        return localDataSource.setComp(comp)
    }

    override suspend fun createComp(name: String): Flow<CompositionBo?> {
        return localDataSource.createComp(name)
    }

    override suspend fun deleteComp(comp: CompositionBo) {
        localDataSource.deleteComp(comp)
    }
}