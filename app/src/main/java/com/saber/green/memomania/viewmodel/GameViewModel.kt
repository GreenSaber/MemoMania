package com.saber.green.memomania.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.saber.green.memomania.model.Game
import com.saber.green.memomania.model.Life
import com.saber.green.memomania.model.Tile

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val game: Game = Game()
    private val life: Life = Life()
    private var rightAnswearsCount: Int = 0
    private var wrongAnswearsCount: Int = 0

    fun setGameLevel(gameLevelNumber: Int) {
        game.setLevel(gameLevelNumber)
    }

    fun getGameLevel(): Int {
        return game.getLevel()
    }

    fun getActiveTiles(): ArrayList<Tile> {
        return game.getActiveTiles()!!
    }

    fun getCurrentLifesCount(): Int {
        return life.getLifesCount()
    }

    fun isValueCorrect(value: String): Boolean {
        val isCorrect = getSortedTiles()[rightAnswearsCount].getValue() == value.toInt()
        if (isCorrect) rightAnswearsCount++ else wrongAnswearsCount++
        return isCorrect
    }

    fun isLevelFinished(): Boolean {
        return getActiveTiles().size == rightAnswearsCount
    }

    private fun getSortedTiles(): List<Tile> {
        return getActiveTiles().sortedWith(compareBy { it.getValue() })

    }


}