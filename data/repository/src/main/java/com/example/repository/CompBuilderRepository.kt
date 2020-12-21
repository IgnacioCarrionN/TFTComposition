package com.example.repository

import com.example.repository.models.ChampionBo
import com.example.repository.models.CompositionBo
import kotlinx.coroutines.flow.Flow

interface CompBuilderRepository {

    suspend fun getChampList(): Flow<List<ChampionBo>>

    suspend fun updateChampList(): Boolean

    suspend fun getCompList(): Flow<List<CompositionBo>>

    suspend fun getComp(id: Long): Flow<CompositionBo?>

    suspend fun setComp(comp: CompositionBo): Long

    suspend fun createComp(name: String): Flow<CompositionBo?>

    suspend fun deleteComp(comp: CompositionBo)

}