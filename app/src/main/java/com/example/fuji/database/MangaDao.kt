package com.example.fuji.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MangaDao {
    @Query("SELECT * FROM mangas")
    fun getAll(): List<MangaEntity>

    @Query("SELECT * FROM mangas WHERE manga_slug LIKE :slug")
    fun findById(slug: String): MangaEntity

    @Insert
    fun insertAll(vararg manga: MangaEntity)

    @Delete
    fun delete(manga: MangaEntity)

    @Update
    fun updateSource(vararg manga: MangaEntity)
}