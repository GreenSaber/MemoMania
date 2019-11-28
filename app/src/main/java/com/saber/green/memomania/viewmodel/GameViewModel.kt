package com.saber.green.memomania.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.saber.green.memomania.model.Game
import com.saber.green.memomania.model.Tile

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private var gameLevel: Int = 1
    private var counter: Int = 0
    private var activeTiles: ArrayList<Tile> = Game(getGameLevel()).getActiveTiles()!!
    private val sortedTiles: List<Tile> = activeTiles.sortedWith(compareBy { it.getValue() })

    private lateinit var game: Game


    fun setGameLevel(gameLevelNumber: Int) {
        gameLevel = gameLevelNumber
    }

    fun getGameLevel(): Int {
        return gameLevel
    }

    fun getActiveTiles(): ArrayList<Tile> {
        return activeTiles
    }

    fun isValueCorrect(value: String): Boolean {
        val isCorrect = sortedTiles[counter].getValue() == value.toInt()
        counter++
        return isCorrect
    }

    private fun getSortedValues() {

    }


}