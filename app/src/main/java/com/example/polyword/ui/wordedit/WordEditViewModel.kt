package com.example.polyword.ui.wordedit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.polyword.repositories.WordRepository
import com.example.polyword.model.Word
import com.example.polyword.repositories.WordbookRepository
import java.util.*

class WordEditViewModel : ViewModel(){

    private val mWordRepository = WordRepository.get()
    private val mWordbookRepository = WordbookRepository.get()
    private val mWordIdLiveData = MutableLiveData<UUID>() //id of word
    val mWordLiveData: LiveData<Word?> =
            Transformations.switchMap(mWordIdLiveData){ wordId ->
                mWordRepository.getWord(wordId)
            }
    /*val mWordbookLiveData: LiveData<String?> =
        Transformations.switchMap(mWordIdLiveData){ wordId ->
            mWordbookRepository.getWordMean(mWordLiveData?.value.spelling)
    }*/

    fun loadWord(wordId: UUID) {
        mWordIdLiveData.value=wordId
    }
    fun saveWord(word: Word) {
        mWordRepository.updateWord(word)
    }
    fun deleteWord(word: Word) {
        mWordRepository.deleteWord(word)
    }

    fun searchMeaning(spell: String): String { //todo use livedata and observers
        return mWordbookRepository.getWordMean(spell) ?: "not found"
    }

}