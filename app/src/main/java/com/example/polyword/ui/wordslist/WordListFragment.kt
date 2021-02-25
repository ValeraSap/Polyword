package com.example.polyword.ui.wordslist

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.polyword.R
import com.example.polyword.model.Word
import com.example.polyword.ui.wordedit.WordEditFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import java.util.*

class WordListFragment : Fragment() {

    interface Callbacks{
        fun onWordSelected (wordId: UUID)
    }

    private var mCallbacks: Callbacks? = null
    private lateinit var mAddButton: FloatingActionButton
    private lateinit var mListEmptyTextView: TextView

    private lateinit var mWordRecyclerView: RecyclerView
    private var mAdapter: WordAdapter? = WordAdapter(emptyList())
   
    private val mWordListViewModel: WordListViewModel by lazy {
        ViewModelProvider(this).get(WordListViewModel::class.java)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mCallbacks = context as Callbacks?
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view= inflater.inflate(R.layout.fragment_word_list,container,false)

        mWordRecyclerView=view.findViewById(R.id.word_recycler_view) as RecyclerView
        mWordRecyclerView.apply {
            layoutManager= LinearLayoutManager(context)
            adapter=mAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

         val mSwipeHelper=object: SwipeHelper(
                context,
                mWordRecyclerView,
                150
        ) {
             override fun instantiateMyButton(viewHolder: RecyclerView.ViewHolder?,
                                              buffer: MutableList<Any?>?) {
                 buffer?.add(
                         MyButton(context,
                                 R.drawable.ic_archive,
                                 Color.TRANSPARENT
                         ) { pos ->
                             mAdapter?.archiveElem(pos)
                              })
                 buffer?.add(
                         MyButton(context,
                                 R.drawable.ic_delete,
                                 Color.TRANSPARENT
                         ) { pos ->

                            mAdapter?.deleteElem(pos)
                         })
             }
         }
        val itemTouchHelper = ItemTouchHelper(mSwipeHelper)
        itemTouchHelper.attachToRecyclerView(mWordRecyclerView)

        mAddButton =view.findViewById(R.id.add_word_button)
        mListEmptyTextView=view.findViewById(R.id.empty_list_textview)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_list_menu, menu)

        val searchItem = menu.findItem(R.id.menu_item_search)
        val searchView = searchItem.actionView as SearchView

        searchView.apply {
            //todo OnQueryTextListener
            //todo OnClickListener
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mWordListViewModel.mWordLiveData.observe(
                viewLifecycleOwner,
                Observer { words ->
                    words?.let {
                        updateUI(words)
                    }
                }
        )
    }

    private fun updateUI(words: List<Word>) {
        mAdapter=WordAdapter(words)
        mWordRecyclerView.adapter=mAdapter

        mListEmptyTextView.visibility = if(mAdapter!!.words.isNullOrEmpty()){
            View.VISIBLE
        } else {
            View.GONE
        }

    }


    override fun onStart() {
        super.onStart()
        mAddButton.setOnClickListener { view ->
            val word = Word()
            mWordListViewModel.addWord(word)
            mCallbacks?.onWordSelected(word.id)
        }
    }


    override fun onDetach() {
        super.onDetach()
        mCallbacks = null
    }


    private inner class WordHolder(view: View) :
        RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var mWord: Word
        private val titleTextView: TextView = itemView.findViewById(R.id.word_text_view)

        init {
            view.setOnClickListener(this)
        }

        fun bind(word: Word) {
            mWord=word
            titleTextView.text=mWord.spelling
        }
        override fun onClick(p0: View?) {
            mCallbacks?.onWordSelected(mWord.id)
        }

    }

    private inner class WordAdapter(var words: List<Word>): RecyclerView.Adapter<WordHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordHolder {
            val view=layoutInflater.inflate(R.layout.list_item_word,parent,false)

            return WordHolder(view)
        }

        override fun onBindViewHolder(holder: WordHolder, position: Int) {
           val word = words[position]
            holder.bind(word)
        }

        override fun getItemCount(): Int {
            return words.size
        }

        fun deleteElem(position: Int) {
            mWordListViewModel.deleteWord(words[position])
        }
        fun archiveElem(position: Int) {
            Toast.makeText(context,"archived",Toast.LENGTH_SHORT).show()
        }


    }
}