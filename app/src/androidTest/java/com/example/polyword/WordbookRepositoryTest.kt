package com.example.polyword

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.polyword.database.DbHelper
import com.example.polyword.repositories.WordbookRepository
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
//import org.junit.Assert
import org.junit.Before
import org.junit.Test
//import org.hamcrest.core.Is.`is`
import org.hamcrest.core.IsNull;
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WordbookRepositoryTest {

    private lateinit var subject: WordbookRepository

    @Before
    fun setUp() {
        subject=WordbookRepository.get()
    }

    @Test
    fun dbCaseIndependentSearch() {
        assertThat(subject.getWordMean("аббат")).isEqualTo(subject.getWordMean("АББАТ"))
   }

    @Test
    fun dbDoesntReturnNullOnExistingItem() {
        assertThat(subject.getWordMean("аббат")).isEqualTo(IsNull.notNullValue())
    }

    @Test
    fun dbReturnsNullOnItemsThatNotExist() {
        assertThat(subject.getWordMean("watermelon")).isEqualTo (IsNull.nullValue())
    }

}