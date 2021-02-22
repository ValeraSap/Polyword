package com.example.polyword.ui.wordedit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.polyword.WordRepository
import com.example.polyword.model.Word
import java.util.*

class WordEditViewModel : ViewModel(){

    private val mWordRepository = WordRepository.get()
    private val mWordIdLiveData = MutableLiveData<UUID>() //id of word
    val mWordLiveData: LiveData<Word?> =
            Transformations.switchMap(mWordIdLiveData){ wordId ->
                mWordRepository.getWord(wordId)
            }

    fun loadWord(wordId: UUID) {
        mWordIdLiveData.value=wordId
    }
    fun saveWord(word: Word) {
        mWordRepository.updateWord(word)
    }
    fun deleteWord(word: Word) {
        mWordRepository.deleteWord(word)
    }

}