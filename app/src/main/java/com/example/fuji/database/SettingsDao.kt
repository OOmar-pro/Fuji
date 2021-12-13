package com.example.fuji.database

import androidx.room.*

@Dao
interface SettingsDao {
    @Query("SELECT * FROM settings")
    fun getAll(): List<SettingsEntity>

    @Query("SELECT * FROM settings WHERE name LIKE :name")
    fun findByName(name: String): SettingsEntity

    @Insert
    fun insertAll(vararg setting: SettingsEntity)

    @Delete
    fun delete(setting: SettingsEntity)

    @Update
    fun updateSetting(vararg setting: SettingsEntity)
}