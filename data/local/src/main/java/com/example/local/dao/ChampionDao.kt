package com.example.local.dao

import androidx.room.*
import com.example.local.models.ChampionDbo
import com.example.local.models.CompositionDbo
import com.example.repository.models.ChampionBo
import com.example.repository.models.CompositionBo
import kotlinx.coroutines.flow.Flow

@Dao
interface ChampionDao {

    @Query("SELECT * FROM champions")
    fun getChampionList(): Flow<List<ChampionDbo>>

    @Query("SELECT * FROM champions WHERE champion_id = :championId LIMIT 1")
    fun getChampionById(championId: Long): ChampionDbo?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChampionList(championList: List<ChampionDbo>)

}