package com.example.local.dao

import androidx.room.*
import com.example.local.models.CompositionDbo
import kotlinx.coroutines.flow.Flow

@Dao
interface CompositionDao {

    @Query("SELECT * FROM compositions")
    fun getCompList(): Flow<List<CompositionDbo>>

    @Query("SELECT * FROM compositions WHERE id = :id LIMIT 1")
    fun getCompById(id: Long): Flow<CompositionDbo?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setComp(comp: CompositionDbo): Long

    @Query("DELETE FROM compositions WHERE id = :id")
    fun deleteComp(id: Long)

}