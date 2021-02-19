package com.example.polyword.ui.wordedit

import androidx.lifecycle.ViewModel
import com.example.polyword.WordRepository

class WordEditViewModel : ViewModel(){

    private val mWordRepository = WordRepository.get()
    //val mWordLiveData = mWordRepository.getWord()
}