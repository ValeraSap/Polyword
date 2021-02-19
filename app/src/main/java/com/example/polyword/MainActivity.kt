package com.example.polyword

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.polyword.ui.wordedit.WordEditFragment
import com.example.polyword.ui.wordslist.WordListFragment
import com.google.android.material.navigation.NavigationView
import java.util.*

class MainActivity: AppCompatActivity(),
WordEditFragment.Callbacks, WordListFragment.Callbacks{

    private lateinit var appBarConfiguration: AppBarConfiguration
    //должна заниматься только подстановкой фрагментов

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //toolbar отнести к фрагемнту
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)
/***
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        //Это соотношение элементов выдвижного меню с фрагентами
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        //appBarConfiguration = AppBarConfiguration(setOf(
       //     R.id.nav_word_list, R.id.nav_edit), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
**/


    }
    /* //для чего этот код?
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }*/

    override fun onWordUpdated(wordID: UUID) {
        TODO("Not yet implemented")
    }

    override fun onWordSelected(wordID: UUID) {
        val fragment= WordEditFragment.newInstance(wordID)
        TODO("Not yet implemented")
      /*  supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null) //при нажатии «Назад» транзакция будет обращена
            .commit()*/
    }
}