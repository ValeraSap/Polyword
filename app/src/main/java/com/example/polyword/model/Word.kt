package com.example.polyword.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Word(@PrimaryKey val id: UUID = UUID.randomUUID(),
                var spelling: String = "",
                var meaning: String = "")