package com.example.local.models

import com.example.repository.models.ChampionBo
import org.junit.Assert.assertEquals
import org.junit.Test

class ChampionMapTest {

    private val championBo = ChampionBo(
        championId = 4062,
        name = "Wukong",
        cost = 1,
        costColor = "#213041",
        iconUrl = "https://images.tacter.app/set4/champions/portraits/wukong.png"
    )

    private val championDbo = ChampionDbo(
        championId = 4062,
        name = "Wukong",
        cost = 1,
        costColor = "#213041",
        iconUrl = "https://images.tacter.app/set4/champions/portraits/wukong.png"
    )

    @Test
    fun `should map bo to dbo`() {
        assertEquals(championDbo, championBo.toDbo())
    }

    @Test
    fun `should map dbo to bo`() {
        assertEquals(championBo, championDbo.toBo())
    }
}