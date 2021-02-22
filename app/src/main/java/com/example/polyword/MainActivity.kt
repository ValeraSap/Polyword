package com.example.polyword

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.polyword.model.Word
import com.example.polyword.ui.wordedit.WordEditFragment
import com.example.polyword.ui.wordedit.WordEditFragmentArgs
import com.example.polyword.ui.wordslist.WordListFragment
import com.example.polyword.ui.wordslist.WordListFragmentDirections
import com.google.android.material.navigation.NavigationView
import java.util.*

class MainActivity: AppCompatActivity(),
WordEditFragment.Callbacks, WordListFragment.Callbacks{

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var mNavController: NavController
    //должна заниматься только подстановкой фрагментов

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)

        mNavController = Navigation.findNavController(this,R.id.fragment_host)

    }

    @LayoutRes
    protected fun getLayoutResId(): Int {
        //todo twopane
        return R.layout.activity_masterdetail
    }

    override fun onWordUpdated(word: Word) {
        TODO("Not yet implemented")
    }

    override fun onWordSelected(wordId: UUID) {

        val action = WordListFragmentDirections.actionNavListToNavEdit(wordId)
        mNavController.navigate(action)

    }
}