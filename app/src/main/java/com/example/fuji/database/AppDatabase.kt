package com.example.fuji.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 2,
    entities = [(SourcesEntity::class), (SettingsEntity::class), (MangaEntity::class)],
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun sourcesDao() : SourcesDao
    abstract fun settingsDao() : SettingsDao
    abstract fun MangaDao() : MangaDao
}
