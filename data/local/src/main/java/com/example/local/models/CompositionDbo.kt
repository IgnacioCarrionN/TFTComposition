package com.example.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.repository.models.ChampionBo
import com.example.repository.models.CompositionBo

@Entity(tableName = "compositions")
data class CompositionDbo(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val champion1: Long?,
    val champion2: Long?,
    val champion3: Long?,
    val champion4: Long?,
    val champion5: Long?,
    val champion6: Long?,
    val champion7: Long?,
    val champion8: Long?,
    val champion9: Long?,
    val champion10: Long?
)

internal fun CompositionDbo.toBo(championList: List<ChampionBo?>): CompositionBo =
    CompositionBo(
        id = this.id,
        name = this.name,
        champList = championList
    )

internal fun CompositionBo.toDbo(championIds: List<Long?>): CompositionDbo =
    CompositionDbo(
        id = this.id,
        name = this.name,
        champion1 = championIds[0],
        champion2 = championIds[1],
        champion3 = championIds[2],
        champion4 = championIds[3],
        champion5 = championIds[4],
        champion6 = championIds[5],
        champion7 = championIds[6],
        champion8 = championIds[7],
        champion9 = championIds[8],
        champion10 = championIds[9]
    )