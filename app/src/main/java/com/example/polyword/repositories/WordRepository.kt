package com.example.polyword.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.polyword.database.WordDatabase
import com.example.polyword.model.Word
import java.lang.IllegalStateException
import java.util.*
import java.util.concurrent.Executors



class WordRepository private constructor(context: Context){


    private val database : WordDatabase = Room.databaseBuilder(
        context.applicationContext,
        WordDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val wordDao = database.wordDao()
   // private val executor = Executors.newSingleThreadExecutor()  //для выполнения запросов к бд без торможения
    //private val filesDir = context.applicationContext.filesDir //файловая директория


    fun getWords(): LiveData<List<Word>> = wordDao.getWords()
    fun getWord(id: UUID): LiveData<Word?> = wordDao.getWord(id)
    fun updateWord(word: Word){
      /*  executor.execute {
            wordDao.updateWord(word)
        }*/
    }
    fun deleteWord(word: Word){
       /* executor.execute {
            wordDao.deleteWord(word)
        }*/
    }
    fun addWord(word: Word){
       /* executor.execute {
            wordDao.addWord(word)
        }*/
    }


    companion object {
        const val DATABASE_NAME = "word-database"

        private var INSTANCE: WordRepository? = null

        fun initialize(context: Context) {
            if(INSTANCE ==null) {
                INSTANCE = WordRepository(context)
            }
        }
        fun get(): WordRepository {
            return INSTANCE ?:
            throw IllegalStateException("WordRepository must be initialized")
        }
    }


}