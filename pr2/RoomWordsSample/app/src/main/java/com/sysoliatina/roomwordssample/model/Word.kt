package com.sysoliatina.roomwordssample.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
class Word(val word: String, val imageStrUri: String?) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}