package com.example.polyword

import android.app.Application
import com.example.polyword.WordRepository

class WordIntentApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        WordRepository.initialize(this)
    }
}