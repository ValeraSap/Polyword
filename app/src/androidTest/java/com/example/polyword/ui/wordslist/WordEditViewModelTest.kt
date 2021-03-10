package com.example.polyword.ui.wordslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.polyword.repositories.WordRepository
import com.example.polyword.model.Word
import com.example.polyword.repositories.WordbookRepository
import com.example.polyword.ui.wordedit.WordEditViewModel
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import java.util.*

@RunWith(AndroidJUnit4::class)
class WordEditViewModelTest {

    private lateinit var mWordRepository: WordRepository
    private lateinit var mWordbookRepository: WordbookRepository
    private lateinit var mWordIdLiveData: MutableLiveData<UUID>
    private lateinit var mWordLiveData: LiveData<Word?>
    private lateinit var subject: WordEditViewModel

    @Before
    fun setUp() {
    }

    @Test
    fun getMWordLiveData() {
    }

    @Test
    fun loadWord() {
    }


    @Test
    fun searchMeaning() {
    }
}