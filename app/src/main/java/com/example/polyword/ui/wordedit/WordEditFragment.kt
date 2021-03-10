package com.example.polyword.ui.wordedit

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import com.example.polyword.R
import com.example.polyword.model.Word
import com.example.polyword.ui.wordslist.SwipeController


private const val ARG_WORD_ID="word_id"

class WordEditFragment : Fragment(){

    interface Callbacks{
        fun onWordUpdated(word: Word)
    }

    private lateinit var mWord: Word


    private var mCallbacks: Callbacks? = null
    private lateinit var mSpellingEditText: EditText
    private lateinit var mMeaningEditText: EditText
    private lateinit var mSearchMeaningButton: ImageButton

    private val mWordEditViewModel: WordEditViewModel by lazy {
        ViewModelProvider(this).get(WordEditViewModel::class.java)
    }

    override fun onAttach(context: Context) {

        super.onAttach(context)
        mCallbacks=context as Callbacks
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
         mWord= Word()
        val wordId=WordEditFragmentArgs.fromBundle(requireArguments()).wordId
       // val wordId=arguments?.get(ARG_WORD_ID) as UUID
        mWordEditViewModel.loadWord(wordId)
        // Show the Up button in the action bar.
       // supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_word_edit,container,false)

        mSpellingEditText = view.findViewById(R.id.word_spelling_edit_text) as EditText
        mMeaningEditText = view.findViewById(R.id.word_meaning_edit_text) as EditText
        mSearchMeaningButton = view.findViewById(R.id.search_meaning_button) as ImageButton

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_edit_menu,menu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mWordEditViewModel.mWordLiveData.observe(
                viewLifecycleOwner,
                Observer { word ->
                    word?.let {
                        mWord=word
                        updateUI()
                    }


                }
        )
    }

    private fun updateUI() {
        mSpellingEditText.setText(mWord.spelling)
        mMeaningEditText.setText(mWord.meaning)
    }

    override fun onStart() {
        super.onStart()

        val spellingTextWatcher=object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(sequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                mWord.spelling=sequence.toString()
            }
            override fun afterTextChanged(p0: Editable?) { }
        }
         val meaningTextWatcher=object : TextWatcher {
             override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

             }

             override fun onTextChanged(sequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                mWord.meaning=sequence.toString()
             }

             override fun afterTextChanged(p0: Editable?) {
             }

         }

        mSpellingEditText.addTextChangedListener(spellingTextWatcher)
        mMeaningEditText.addTextChangedListener(meaningTextWatcher)
        mSearchMeaningButton.setOnClickListener {
            mMeaningEditText.setText(mWordEditViewModel.searchMeaning(mWord.spelling))
        }
    }

    override fun onStop() {
        super.onStop()
        mWordEditViewModel.saveWord(mWord)
    }

    override fun onDetach() {
        super.onDetach()
        mCallbacks=null
    }

    /*companion object{
        fun newInstance(wordID: UUID): WordEditFragment {
            val args = Bundle().apply {
                putSerializable(ARG_WORD_ID, wordID)
            }
            return WordEditFragment().apply {
                arguments = args
            }
        }
    }*/

}