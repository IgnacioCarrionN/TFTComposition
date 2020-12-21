package com.example.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.repository.models.ChampionBo

@Entity(tableName = "champions")
data class ChampionDbo(
    @PrimaryKey @ColumnInfo(name = "champion_id") val championId: Long,
    val name: String,
    val cost: Int,
    @ColumnInfo(name= "cost_color") val costColor: String,
    @ColumnInfo(name = "icon_url") val iconUrl: String
)

internal fun ChampionDbo.toBo() =
    ChampionBo(
        championId = this.championId,
        name = this.name,
        cost = this.cost,
        costColor = this.costColor,
        iconUrl = this.iconUrl
    )

internal fun ChampionBo.toDbo() =
    ChampionDbo(
        championId = this.championId,
        name = this.name,
        cost = this.cost,
        costColor = this.costColor,
        iconUrl = this.iconUrl
    )