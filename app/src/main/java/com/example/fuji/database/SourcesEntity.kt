package com.example.fuji.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sources")
data class SourcesEntity(
    @PrimaryKey var id: String,

    @ColumnInfo(name = "version") var version: String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "img") var img: String,
    @ColumnInfo(name = "url") var url: String,
    @ColumnInfo(name = "url_latests") var url_latests: String,
    @ColumnInfo(name = "url_manga") var url_manga: String,
    @ColumnInfo(name = "url_chapter") var url_chapter: String,
    @ColumnInfo(name = "url_search") var url_search: String,
    @ColumnInfo(name = "active") var active: Boolean
)
