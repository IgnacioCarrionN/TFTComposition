package com.example.repository.models

data class CompositionBo(
    val id: Long,
    val name: String,
    val champList: List<ChampionBo?>
)