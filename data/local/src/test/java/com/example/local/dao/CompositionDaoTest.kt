package com.example.local.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.local.AppDatabase
import com.example.local.models.CompositionDbo
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
class CompositionDaoTest {

    private lateinit var appDatabase: AppDatabase
    private lateinit var compositionDao: CompositionDao

    private val composition = CompositionDbo(
        name = "Test Comp",
        champion1 = 4062,
        champion2 = null,
        champion3 = null,
        champion4 = null,
        champion5 = null,
        champion6 = null,
        champion7 = null,
        champion8 = null,
        champion9 = null,
        champion10 = null
    )

    @Before
    fun setUp() = runBlocking {
        appDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        compositionDao = appDatabase.compositionDao()
    }

    @After
    fun tearDown() = runBlocking {
        appDatabase.close()
    }

    @Test
    fun insertComposition() = runBlocking {
        compositionDao.setComp(composition)
        val response = compositionDao.getCompList().first()
        assert(response.isNotEmpty())
    }

    @Test
    fun getCompositionList() = runBlocking {
        compositionDao.setComp(composition)
        val response = compositionDao.getCompList().first()
        assertEquals(listOf(composition.copy(id = 1)), response)
    }

    @Test
    fun getCompById() = runBlocking {
        compositionDao.setComp(composition)
        val response = compositionDao.getCompById(1).first()

        assertEquals(composition.copy(id = 1), response)
    }

    @Test
    fun getCompByIdReturnsNull() = runBlocking {
        compositionDao.setComp(composition)

        val response = compositionDao.getCompById(2).first()

        assertEquals(null, response)
    }

    @Test
    fun getCompListReturnsEmptyList() = runBlocking {
        val response = compositionDao.getCompList().first()

        assertEquals(emptyList<CompositionDbo>(), response)
    }

    @Test
    fun deleteComp() = runBlocking {
        compositionDao.setComp(composition)

        compositionDao.deleteComp(1)

        val response = compositionDao.getCompById(1).first()

        assertEquals(null, response)
    }


}