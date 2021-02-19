package com.example.polyword.ui.wordedit

import android.os.Bundle
import androidx.fragment.app.Fragment
import java.util.*

private const val ARG_WORD_ID="word_id"

class WordEditFragment : Fragment(){

    interface Callbacks{
        fun onWordUpdated(wordID: UUID)
    }





    companion object{
        fun newInstance(wordID: UUID): WordEditFragment {
            val args = Bundle().apply {
                putSerializable(ARG_WORD_ID, wordID)
            }
            return WordEditFragment().apply {
                arguments = args
            }
        }
    }

}