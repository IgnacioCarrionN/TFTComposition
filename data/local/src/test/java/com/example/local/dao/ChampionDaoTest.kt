package com.example.local.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.local.AppDatabase
import com.example.local.models.ChampionDbo
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class ChampionDaoTest {

    private lateinit var appDatabase: AppDatabase
    private lateinit var championDao: ChampionDao

    private val champion = ChampionDbo(
        championId = 4062,
        name = "Wukong",
        cost = 1,
        costColor = "#213041",
        iconUrl = "https://images.tacter.app/set4/champions/portraits/wukong.png"
    )

    @Before
    fun setUp() = runBlocking {
        appDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        championDao = appDatabase.championDao()
    }

    @After
    @Throws(Exception::class)
    fun tearDown() = runBlocking {
        appDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertChampionList() = runBlocking {
        championDao.insertChampionList(listOf(champion))
        val response = championDao.getChampionList().first()
        assert(response.isNotEmpty())
    }

    @Test
    fun getChampionList() = runBlocking {
        championDao.insertChampionList(listOf(champion))
        val response = championDao.getChampionList().first()

        assertEquals(listOf(champion), response)
    }

    @Test
    fun getChampionById() = runBlocking {
        championDao.insertChampionList(listOf(champion))

        val response = championDao.getChampionById(champion.championId)

        assertEquals(champion, response)
    }

    @Test
    fun getChampionReturnsNull() = runBlocking {
        championDao.insertChampionList(listOf(champion))

        val response = championDao.getChampionById(0)

        assertEquals(null, response)
    }

}