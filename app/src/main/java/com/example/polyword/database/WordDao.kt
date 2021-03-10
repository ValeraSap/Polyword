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

    @Update
    fun updateWord(word: Word)

    @Insert
    fun addWord(word: Word)

    @Delete
    fun deleteWord(word: Word)

}