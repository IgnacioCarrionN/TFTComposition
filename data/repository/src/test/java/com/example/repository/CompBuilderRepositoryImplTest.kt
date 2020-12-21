package com.example.repository

import com.example.repository.datasource.local.LocalDataSource
import com.example.repository.datasource.remote.RemoteDataSource
import com.example.repository.datasource.remote.RemoteResponse
import com.example.repository.models.ChampionBo
import com.example.repository.models.CompositionBo
import io.mockk.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test

class CompBuilderRepositoryImplTest {

    private val localDataSource: LocalDataSource = mockk()
    private val remoteDataSource: RemoteDataSource = mockk()

    private val compBuilderRepository = CompBuilderRepositoryImpl(remoteDataSource, localDataSource)

    private val champList = listOf(ChampionBo(
        championId = 4062,
        name = "Wukong",
        cost = 1,
        costColor = "#213041",
        iconUrl = "https://images.tacter.app/set4/champions/portraits/wukong.png"
    ))

    private val comp1 = CompositionBo(
        id = 1,
        name = "Test Comp",
        champList = champList
    )

    private val compList = listOf(comp1)

    @After
    fun tearDown() {
        confirmVerified(localDataSource, remoteDataSource)
    }

    @Test
    fun `should return flow with champion List`() = runBlocking {

        coEvery {  localDataSource.getChampList() } returns flow { emit(champList) }

        val response = compBuilderRepository.getChampList().first()

        coVerify {  localDataSource.getChampList() }

        assertEquals(champList, response)
    }

    @Test
    fun `should update champ when remote returns success`() = runBlocking {
        coEvery { remoteDataSource.getChampList() } returns RemoteResponse.Success(champList)
        coEvery { localDataSource.updateChampList(any()) } returns Unit

        val response = compBuilderRepository.updateChampList()

        coVerify { remoteDataSource.getChampList() }
        coVerify { localDataSource.updateChampList(champList) }

        assertEquals(true, response)
    }

    @Test
    fun `should return false when remote returns false`() = runBlocking {
        coEvery { remoteDataSource.getChampList() } returns RemoteResponse.Error("error")

        val response = compBuilderRepository.updateChampList()

        coVerify { remoteDataSource.getChampList() }

        assertEquals(false, response)
    }

    @Test
    fun `should return compositions list`() = runBlocking {
        coEvery { localDataSource.getCompList() } returns flow {
            emit(compList)
        }

        val response = compBuilderRepository.getCompList()

        coVerify { localDataSource.getCompList() }

        assertEquals(compList, response.first())
    }

    @Test
    fun `should update composition`() = runBlocking {
        coEvery { localDataSource.setComp(comp1) } returns 1

        val response = compBuilderRepository.setComp(comp1)

        coVerify { localDataSource.setComp(comp1) }

        assertEquals(1, response)
    }

    @Test
    fun `should remove comp`() = runBlocking {
        coEvery { localDataSource.deleteComp(comp1) } returns Unit

        compBuilderRepository.deleteComp(comp1)

        coVerify { localDataSource.deleteComp(comp1) }
    }

    @Test
    fun `should get comp by id`() = runBlocking {
        coEvery { localDataSource.getComp(1) } returns flow {
            emit(comp1)
        }

        val response = compBuilderRepository.getComp(1)

        coVerify { localDataSource.getComp(1) }

        assertEquals(comp1, response.first())
    }
}