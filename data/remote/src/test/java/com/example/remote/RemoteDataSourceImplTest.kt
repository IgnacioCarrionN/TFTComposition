package com.example.remote

import com.example.remote.models.ChampionDto
import com.example.remote.service.RemoteService
import com.example.remote.service.map
import com.example.repository.datasource.remote.RemoteDataSource
import com.example.repository.datasource.remote.RemoteResponse
import com.example.repository.models.ChampionBo
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class RemoteDataSourceImplTest {

    lateinit var mockWebServer: MockWebServer
    lateinit var remoteDataSource: RemoteDataSource

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(RemoteService::class.java)

        remoteDataSource = RemoteDataSourceImpl(service)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `should return RemoteResponse success`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse().setBody(
                "[\n" +
                        "  {\n" +
                        "    \"championId\": 4062,\n" +
                        "    \"name\": \"Wukong\",\n" +
                        "    \"cost\": 1,\n" +
                        "    \"costColor\": \"#213041\",\n" +
                        "    \"iconURL\": \"https://images.tacter.app/set4/champions/portraits/wukong.png\"\n" +
                        "  }\n" +
                        "]"
            )
        )

        val response = remoteDataSource.getChampList()

        val expected = RemoteResponse.Success(
            data = listOf(
                ChampionBo(
                    championId = 4062,
                    name = "Wukong",
                    cost = 1,
                    costColor = "#213041",
                    iconUrl = "https://images.tacter.app/set4/champions/portraits/wukong.png"
                )
            )
        )

        assertEquals(expected, response)
    }

    @Test
    fun `should return RemoteResponse error`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse().setHttp2ErrorCode(HttpURLConnection.HTTP_NOT_FOUND)
        )

        val response = remoteDataSource.getChampList()

        assert(response is RemoteResponse.Error)
    }

}