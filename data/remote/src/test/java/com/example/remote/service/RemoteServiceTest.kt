package com.example.remote.service

import com.example.remote.models.ChampionDto
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteServiceTest {

    lateinit var mockWebServer: MockWebServer
    lateinit var service: RemoteService

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(RemoteService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `should return champ list on success`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse().setBody("[\n" +
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

        val response = service.getChampList()

        val expected = listOf(
            ChampionDto(
                championId = 4062,
                name = "Wukong",
                cost = 1,
                costColor = "#213041",
                iconUrl = "https://images.tacter.app/set4/champions/portraits/wukong.png"
            )
        )

        assertEquals(expected, response.body())

        val request = mockWebServer.takeRequest()
        val expectedPath = "/v3/ef0ec22f-c5a4-4316-a479-eea14078deae"
        assertEquals(expectedPath, request.path)
    }

}