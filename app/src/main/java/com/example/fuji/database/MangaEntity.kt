package com.example.fuji.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mangas")
data class MangaEntity(
    @PrimaryKey(autoGenerate = true) var id: Int,

    @ColumnInfo(name = "source_id") var source_id: String,
    @ColumnInfo(name = "manga_slug") var manga_slug: String,
    @ColumnInfo(name = "manga_title") var manga_title: String,
    @ColumnInfo(name = "manga_img") var manga_img: String,
)
