package com.example.remote.models

import com.example.repository.models.ChampionBo
import com.google.gson.annotations.SerializedName

data class ChampionDto(
    val championId: Long,
    val name: String,
    val cost: Int,
    val costColor: String,
    @SerializedName("iconURL")
    val iconUrl: String
)

internal fun ChampionDto.toBo(): ChampionBo =
    ChampionBo(
        championId = this.championId,
        name = this.name,
        cost = this.cost,
        costColor = this.costColor,
        iconUrl = this.iconUrl
    )