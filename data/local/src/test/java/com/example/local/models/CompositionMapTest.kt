package com.example.local.models

import com.example.repository.models.ChampionBo
import com.example.repository.models.CompositionBo
import org.junit.Assert.assertEquals
import org.junit.Test

class CompositionMapTest {

    private val compositionDbo = CompositionDbo(
        id = 1,
        name = "Test",
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

    private val championBo = ChampionBo(
        championId = 4062,
        name = "Wukong",
        cost = 1,
        costColor = "#213041",
        iconUrl = "https://images.tacter.app/set4/champions/portraits/wukong.png"
    )

    private val champList = listOf(
        championBo,
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

    private val compositionBo = CompositionBo(
        id = 1,
        name = "Test",
        champList = champList
    )

    @Test
    fun `should map to bo`() {

        assertEquals(compositionBo, compositionDbo.toBo(champList))

    }

    @Test
    fun `should map to dbo`() {
        assertEquals(compositionDbo, compositionBo.toDbo(champList.map { it?.championId }))
    }
}