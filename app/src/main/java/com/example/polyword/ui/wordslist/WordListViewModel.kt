package com.example.polyword.ui.wordslist

import androidx.lifecycle.ViewModel
import com.example.polyword.WordRepository
import com.example.polyword.model.Word

class WordListViewModel: ViewModel() {

    private val mWordRepository = WordRepository.get()
    val mWordLiveData = mWordRepository.getWords()

    fun addWord(word: Word) {
        mWordRepository.addWord(word)
    }
}