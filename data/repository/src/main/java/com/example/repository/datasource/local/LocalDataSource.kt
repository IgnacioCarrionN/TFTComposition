package com.example.repository.datasource.local

import com.example.repository.models.ChampionBo
import com.example.repository.models.CompositionBo
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    suspend fun getChampList(): Flow<List<ChampionBo>>

    suspend fun updateChampList(championList: List<ChampionBo>)

    suspend fun getCompList(): Flow<List<CompositionBo>>

    suspend fun getComp(id: Long): Flow<CompositionBo?>

    suspend fun setComp(comp: CompositionBo): Long

    suspend fun deleteComp(comp: CompositionBo)

}