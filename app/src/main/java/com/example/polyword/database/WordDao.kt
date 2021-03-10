package com.example.polyword.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.polyword.model.Word
import java.util.*

@Dao
interface WordDao {

    @Query("SELECT * FROM word")
    fun getWords(): LiveData<List<Word>>

    @Query("SELECT * FROM word WHERE id=(:id)")
    fun getWord(id: UUID):LiveData<Word?>

   // @Query("SELECT COUNT(*) FROM word")

    @Update
    suspend fun updateWord(word: Word)

    @Insert
    suspend fun addWord(word: Word)

    @Delete
    suspend fun deleteWord(word: Word)

}