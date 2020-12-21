package com.example.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.local.dao.ChampionDao
import com.example.local.dao.CompositionDao
import com.example.local.models.ChampionDbo
import com.example.local.models.CompositionDbo

@Database(entities = [CompositionDbo::class, ChampionDbo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun championDao(): ChampionDao
    abstract fun compositionDao(): CompositionDao

}