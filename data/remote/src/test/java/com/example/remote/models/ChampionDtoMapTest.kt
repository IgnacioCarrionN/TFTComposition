package com.example.remote.models

import com.example.repository.models.ChampionBo
import org.junit.Test

import org.junit.Assert.*

class ChampionDtoMapTest {

    @Test
    fun `should map dto to bo`() {
        val dto = ChampionDto(
            championId = 4062,
            name = "Wukong",
            cost = 1,
            costColor = "#213041",
            iconUrl = "https://images.tacter.app/set4/champions/portraits/wukong.png"
        )

        val bo = ChampionBo(
            championId = 4062,
            name = "Wukong",
            cost = 1,
            costColor = "#213041",
            iconUrl = "https://images.tacter.app/set4/champions/portraits/wukong.png"
        )

        assertEquals(bo, dto.toBo())

    }

}