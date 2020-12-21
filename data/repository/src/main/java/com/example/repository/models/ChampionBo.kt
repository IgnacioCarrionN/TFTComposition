package com.example.repository.models

data class ChampionBo(
    val championId: Long,
    val name: String,
    val cost: Int,
    val costColor: String,
    val iconUrl: String
)