package com.example.polyword

import android.content.Context
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.polyword.database.WordDao
import com.example.polyword.database.WordDatabase
import com.example.polyword.repositories.WordRepository

import org.junit.Before
import org.junit.Test

//import org.junit.runner.manipulation.Ordering
import com.nhaarman.mockitokotlin2.mock
import org.junit.runner.RunWith

//import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
class WordRepositoryTest {

    private lateinit var database : WordDatabase //и свой вход в бд
    private lateinit var wordDao: WordDao //у нас свое dao от этой бд
    private lateinit var context: Context
    private lateinit var subject: WordRepository

    @Before
    fun setUp() {
        context = mock()
        database = Room.databaseBuilder(
            context.applicationContext,
            WordDatabase::class.java,
            WordRepository.DATABASE_NAME
            ).build()
        wordDao=database.wordDao()
        subject= WordRepository.get()
    }


    @Test
    fun updateWordUpdatesDb() {
    }

    @Test
    fun deleteWordDeletesFromDb() {
    }

    @Test
    fun addWordAddsInDb() {
    }
}