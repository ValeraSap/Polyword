package com.example.polyword

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.polyword.model.Word
import com.example.polyword.ui.wordedit.WordEditFragment
import com.example.polyword.ui.wordslist.WordListFragment
import com.example.polyword.ui.wordslist.WordListFragmentDirections
import java.util.*

class MainActivity: AppCompatActivity(),
WordEditFragment.Callbacks, WordListFragment.Callbacks{

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var mNavController: NavController
    //должна заниматься только подстановкой фрагментов

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mNavController = Navigation.findNavController(this,R.id.fragment_host)

        val drawer=findViewById<DrawerLayout>(R.id.drawer_layout)                                                                   //справка по toolbar https://temofeev.ru/info/articles/navigatsiya-dlya-android-s-ispolzovaniem-navigation-architecture-component-poshagovoe-rukovodstvo/
        val appBarConfiguration = AppBarConfiguration(mNavController.graph, drawer)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setupWithNavController(mNavController,appBarConfiguration)

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