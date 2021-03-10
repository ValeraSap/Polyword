package com.example.polyword.repositories

import android.content.Context
import android.database.Cursor
import com.example.polyword.database.DbHelper
import java.lang.IllegalStateException


private const val DATABASE_NAME = "wordbook"
private const val TABLE_NAME="words"
class WordbookRepository private constructor(context: Context) {
     private  val wordbookDb = DbHelper(context).readableDatabase

    fun getWordMean(spell: String): String? {

        val cursor: Cursor = wordbookDb.rawQuery(
                "SELECT mean from words where spell = ?",
                arrayOf(spell.toUpperCase()))
        return cursor.use { cursor ->
            if (cursor.count>0) {
                cursor.moveToFirst()
                return cursor.getString(cursor.getColumnIndex("mean"))
            } else return null
        }
    }

    companion object {
        private var INSTANCE: WordbookRepository? = null

        fun initialize(context: Context) {
            if(INSTANCE==null) {
                INSTANCE= WordbookRepository(context)
            }
        }
        fun get(): WordbookRepository {
            return INSTANCE ?:
            throw IllegalStateException("WordRepository must be initialized")
        }
    }
}