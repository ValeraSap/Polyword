package com.example.polyword.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.androiddevs.shoppinglisttestingyt.getOrAwaitValueTest
import com.example.polyword.model.Word
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class WordDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule() //это чтобы асинхронный код выполнялся синхронно

    private lateinit var database: WordDatabase
    private lateinit var dao: WordDao

    @Before
    fun setup(){
        database= Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            WordDatabase::class.java
        ).allowMainThreadQueries().build()
        dao=database.wordDao()
    }


    @After
    fun teardown() {
        database.close()
    }


    @Test
    fun insertWord() = runBlockingTest{
        val word = Word(UUID.randomUUID(),"spell","mean","url")
        dao.addWord(word)

        val allWords = dao.getWords().getOrAwaitValueTest()
        assertThat(allWords).contains(word)
    }

    @Test
    fun deleteWord() = runBlockingTest {
        val word = Word(UUID.randomUUID(),"spell","mean","url")
        dao.addWord(word)
        dao.deleteWord(word)

        val allWords = dao.getWords().getOrAwaitValueTest()
        assertThat(allWords).doesNotContain(word)
    }

    @Test
    fun updateWord() = runBlockingTest {
        val word = Word(UUID.randomUUID(),"spell","mean","url")
        dao.addWord(word)

        val newSpell="spell2"
        word.spelling=newSpell
        dao.updateWord(word)

        val word2=dao.getWord(word.id).getOrAwaitValueTest()
        assertThat(word2?.spelling).isEqualTo(newSpell)
    }

}