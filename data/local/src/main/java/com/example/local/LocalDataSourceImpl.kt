package com.example.local

import com.example.local.dao.ChampionDao
import com.example.local.dao.CompositionDao
import com.example.local.models.toBo
import com.example.local.models.toDbo
import com.example.repository.datasource.local.LocalDataSource
import com.example.repository.models.ChampionBo
import com.example.repository.models.CompositionBo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val championDao: ChampionDao,
    private val compositionDao: CompositionDao
) : LocalDataSource {

    override suspend fun getChampList(): Flow<List<ChampionBo>> {
        return championDao.getChampionList().map { champList ->
            champList.map {
                it.toBo()
            }
        }
    }

    override suspend fun updateChampList(championList: List<ChampionBo>) {
        championDao.insertChampionList(championList.map { it.toDbo() })
    }

    override suspend fun getCompList(): Flow<List<CompositionBo>> {
        return compositionDao.getCompList().map { compList ->
            compList.map {
                val championList = listOf(
                    championDao.getChampionById(it.champion1 ?: 0)?.toBo(),
                    championDao.getChampionById(it.champion2 ?: 0)?.toBo(),
                    championDao.getChampionById(it.champion3 ?: 0)?.toBo(),
                    championDao.getChampionById(it.champion4 ?: 0)?.toBo(),
                    championDao.getChampionById(it.champion5 ?: 0)?.toBo(),
                    championDao.getChampionById(it.champion6 ?: 0)?.toBo(),
                    championDao.getChampionById(it.champion7 ?: 0)?.toBo(),
                    championDao.getChampionById(it.champion8 ?: 0)?.toBo(),
                    championDao.getChampionById(it.champion9 ?: 0)?.toBo(),
                    championDao.getChampionById(it.champion10 ?: 0)?.toBo()
                )
                it.toBo(championList)
            }
        }
    }

    override suspend fun getComp(id: Long): Flow<CompositionBo?> {
        return compositionDao.getCompById(id).map {
            it?.toBo(
                listOf(
                    championDao.getChampionById(it.champion1 ?: 0)?.toBo(),
                    championDao.getChampionById(it.champion2 ?: 0)?.toBo(),
                    championDao.getChampionById(it.champion3 ?: 0)?.toBo(),
                    championDao.getChampionById(it.champion4 ?: 0)?.toBo(),
                    championDao.getChampionById(it.champion5 ?: 0)?.toBo(),
                    championDao.getChampionById(it.champion6 ?: 0)?.toBo(),
                    championDao.getChampionById(it.champion7 ?: 0)?.toBo(),
                    championDao.getChampionById(it.champion8 ?: 0)?.toBo(),
                    championDao.getChampionById(it.champion9 ?: 0)?.toBo(),
                    championDao.getChampionById(it.champion10 ?: 0)?.toBo()
                )
            )
        }
    }

    override suspend fun setComp(comp: CompositionBo): Long {
        return compositionDao.setComp(comp.toDbo(comp.champList.map { it?.championId }))
    }

    override suspend fun deleteComp(comp: CompositionBo) {
        compositionDao.deleteComp(comp.id)
    }
}