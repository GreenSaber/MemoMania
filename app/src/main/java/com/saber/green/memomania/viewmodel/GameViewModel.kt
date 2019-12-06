package com.saber.green.memomania.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.saber.green.memomania.model.Game
import com.saber.green.memomania.model.Life
import com.saber.green.memomania.model.Tile

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val life: Life = Life()
    private var rightAnswearsCount: Int = 0
    private var wrongAnswearsCount: Int = 0

    fun setGameLevel(gameLevelNumber: Int) {
        Game.setLevel(gameLevelNumber)
    }

    fun getGameLevel(): Int {
        return Game.getLevel()
    }

    fun getActiveTiles(): ArrayList<Tile> {
        return Game.getActiveTiles()!!
    }

    fun getCurrentLifesCount(): Int {
        return life.getLifesCount()
    }

    fun isValueCorrect(value: String): Boolean {
        val isCorrect = getSortedTiles()[rightAnswearsCount].getValue() == value.toInt()
        if (isCorrect) rightAnswearsCount++ else wrongAnswearsCount++
        return isCorrect
    }

    fun reduceLifeCounter(){
        //TODO
    }

    fun isLevelPassed(): Boolean {
        val isLevelPassed = getActiveTiles().size == rightAnswearsCount
        if (isLevelPassed) {
            val currentLevel = getGameLevel()
            val newLevel = currentLevel+1
            setGameLevel(newLevel)
        }
        return isLevelPassed
    }

    private fun getSortedTiles(): List<Tile> {
        return getActiveTiles().sortedWith(compareBy { it.getValue() })

    }


}