package com.example.local

import com.example.local.dao.ChampionDao
import com.example.local.dao.CompositionDao
import com.example.local.models.CompositionDbo
import com.example.local.models.toDbo
import com.example.repository.models.ChampionBo
import com.example.repository.models.CompositionBo
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class LocalDataSourceImplTest {

    private val championDao: ChampionDao = mockk()
    private val compositionDao: CompositionDao = mockk()
    private val localDataSource = LocalDataSourceImpl(championDao, compositionDao)

    private val champion = ChampionBo(
        championId = 4062,
        name = "Wukong",
        cost = 1,
        costColor = "#213041",
        iconUrl = "https://images.tacter.app/set4/champions/portraits/wukong.png"
    )
    private val championList = listOf(champion)

    private val composition = CompositionBo(
        id = 1,
        name = "Test Comp",
        champList = listOf(
            champion,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
        )
    )
    private val compList = listOf(
        composition
    )

    @Test
    fun `should save champion list`() = runBlocking {

        every { championDao.insertChampionList(any()) } returns Unit

        localDataSource.updateChampList(championList)

        verify { championDao.insertChampionList(championList.map { it.toDbo() }) }
    }

    @Test
    fun `should get champion list`() = runBlocking {
        every { championDao.getChampionList() } returns flow { emit(championList.map { it.toDbo() }) }

        val response = localDataSource.getChampList().first()

        verify { championDao.getChampionList() }

        assertEquals(championList, response)

    }

    @Test
    fun `should return comp list`() = runBlocking {
        every { compositionDao.getCompList() } returns flow {
            emit(compList.map { comp ->
                comp.toDbo(
                    comp.champList.map { it?.championId }
                )
            } )
        }

        every { championDao.getChampionById(champion.championId) } returns champion.toDbo()
        every { championDao.getChampionById(0) } returns null

        val response = localDataSource.getCompList().first()

        verify { compositionDao.getCompList() }
        compList.forEach { comp ->
            comp.champList.forEach {
                verify { championDao.getChampionById(it?.championId ?: 0) }
            }
        }

        assertEquals(compList, response)
    }

}