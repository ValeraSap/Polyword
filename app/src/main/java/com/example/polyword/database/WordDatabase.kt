package com.example.polyword.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.polyword.model.Word

//для миграции
//import androidx.room.migration.Migration
//import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [Word::class],version = 1)
@TypeConverters(WordTypeConverters::class)
abstract class WordDatabase : RoomDatabase(){
    abstract fun wordDao(): WordDao
}