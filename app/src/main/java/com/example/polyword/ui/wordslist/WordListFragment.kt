package com.example.polyword.ui.wordslist

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.TextView
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

        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
        val activ=activity as AppCompatActivity
        activ.setSupportActionBar(toolbar)

        /***
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        //Это соотношение элементов выдвижного меню с фрагентами
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        appBarConfiguration = AppBarConfiguration(setOf(
        R.id.nav_word_list, R.id.nav_edit), drawerLayout)
        //сама кнопка-гамбургер
        setupActionBarWithNavController(navController, appBarConfiguration)
         **/

       /* val navView: NavigationView = view.findViewById(R.id.nav_view)
        val navController = activity?.findNavController(R.id.nav_host_fragment)
        if (navController != null) {
            navView.setupWithNavController(navController)
        }*/

        mWordRecyclerView=view.findViewById(R.id.word_recycler_view) as RecyclerView
        mWordRecyclerView.apply {
            layoutManager= LinearLayoutManager(context)
            adapter=mAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

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
    /* //обрабатывает нажатие на кнопку-гамбургер
   override fun onSupportNavigateUp(): Boolean {
       val navController = findNavController(R.id.nav_host_fragment)
       return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
   }*/

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


    companion object{
        fun newInstance(wordID: UUID): WordEditFragment {
            return WordEditFragment()
        }
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

    }
}