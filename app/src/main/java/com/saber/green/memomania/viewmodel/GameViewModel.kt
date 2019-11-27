package com.saber.green.memomania.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.saber.green.memomania.model.Game
import com.saber.green.memomania.model.Tile

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var game: Game

    fun getActiveTiles(): ArrayList<Tile> {
        return Game(1).getActiveTiles()!!
    }
}