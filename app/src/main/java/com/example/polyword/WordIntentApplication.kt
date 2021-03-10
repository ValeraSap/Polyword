package com.example.polyword

import android.app.Application
import com.example.polyword.repositories.WordRepository
import com.example.polyword.repositories.WordbookRepository


class WordIntentApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        WordRepository.initialize(this)
        WordbookRepository.initialize(this)
    }
}