package com.example.fuji.database

import androidx.room.*

@Dao
interface SourcesDao {
    @Query("SELECT * FROM sources")
    fun getAll(): List<SourcesEntity>

    @Query("SELECT * FROM sources WHERE id LIKE :id")
    fun findById(id: String): SourcesEntity

    @Insert
    fun insertAll(vararg source: SourcesEntity)

    @Delete
    fun delete(source: SourcesEntity)

    @Update
    fun updateSource(vararg source: SourcesEntity)
}