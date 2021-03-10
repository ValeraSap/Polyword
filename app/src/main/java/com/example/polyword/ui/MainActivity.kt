package com.example.polyword.ui


import android.content.res.Configuration
import android.os.Bundle
import android.widget.ImageButton
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.polyword.R
import com.example.polyword.model.Word
import com.example.polyword.ui.wordedit.WordEditFragment
import com.example.polyword.ui.wordslist.WordListFragment
import com.example.polyword.ui.wordslist.WordListFragmentDirections
import com.google.android.material.navigation.NavigationView
import java.util.*


class MainActivity: AppCompatActivity(),
WordEditFragment.Callbacks, WordListFragment.Callbacks{


    private lateinit var mNavController: NavController
    private  lateinit var mThemeButton:ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mNavController = Navigation.findNavController(this, R.id.fragment_host)

        val drawer=findViewById<DrawerLayout>(R.id.drawer_layout)                                                                   //справка по toolbar https://temofeev.ru/info/articles/navigatsiya-dlya-android-s-ispolzovaniem-navigation-architecture-component-poshagovoe-rukovodstvo/
        val appBarConfiguration = AppBarConfiguration(mNavController.graph, drawer)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setupWithNavController(mNavController, appBarConfiguration)

        val navView=findViewById<NavigationView>(R.id.nav_view)

       val currentMode=resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        mThemeButton=navView.getHeaderView(0).findViewById(R.id.theme_switch_button)
        setModeImage()
        mThemeButton.setOnClickListener {
            when (currentMode){
                Configuration.UI_MODE_NIGHT_NO -> {
                    AppCompatDelegate.setDefaultNightMode(
                            AppCompatDelegate.MODE_NIGHT_YES)
                }
                Configuration.UI_MODE_NIGHT_YES -> {
                    AppCompatDelegate.setDefaultNightMode(
                            AppCompatDelegate.MODE_NIGHT_NO)
                }
                else -> {
                    AppCompatDelegate.setDefaultNightMode(
                            AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
            setModeImage()
        }

    }

    fun setModeImage(){
        val currentMode=resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        when (currentMode){
            Configuration.UI_MODE_NIGHT_YES ->
                mThemeButton.setImageResource(R.drawable.ic_moon)
            else ->
                mThemeButton.setImageResource(R.drawable.ic_sun)
        }
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