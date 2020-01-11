package com.saber.green.memomania.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.saber.green.memomania.model.Game
import com.saber.green.memomania.model.GameLifecycle
import com.saber.green.memomania.model.Tile

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private var rightAnswearsCount: Int = 0
    private var wrongAnswearsCount: Int = 0
    private val lifeCount = MutableLiveData<String>()
    private val levelCount = MutableLiveData<String>()

    init {
        lifeCount.value = Game.getLifesCount().toString()
        levelCount.value = Game.getLevel().toString()
    }

    fun getLifeCount(): LiveData<String> = lifeCount

    fun getLevelCount(): LiveData<String> = levelCount

    fun getActiveTiles(): ArrayList<Tile> {
        return Game.getActiveTiles()!!
    }

    fun getGameLifecycle(value: String): GameLifecycle {
        if (getSortedTiles()[rightAnswearsCount].getValue() == value.toInt()) {
            rightAnswearsCount++
            if (rightAnswearsCount == getActiveTiles().size) {
                val level = Game.getLevel() + 1
                Game.setLevel(level)
                levelCount.value = Game.getLevel().toString()
                //TODO to change on 10 value below
                if (Game.getLevel() < 10) {
                    return GameLifecycle.NEXT_LEVEL
                } else {
                    Game.resetGame()
                    return GameLifecycle.WIN
                }
            } else {
                return GameLifecycle.CORRECT_VALUE
            }
        } else {
            wrongAnswearsCount++
            val currentLifesCount = Game.getLifesCount() - 1
            Game.setLifesCount(currentLifesCount)
            lifeCount.value = Game.getLifesCount().toString()
            if (Game.getLifesCount() > 0) {
                return GameLifecycle.INCORRECT_VALUE
            } else {
                return GameLifecycle.GAME_OVER
            }
        }
    }

    fun resetGame(){
        Game.resetGame()
    }

    private fun getSortedTiles(): List<Tile> {
        return getActiveTiles().sortedWith(compareBy { it.getValue() })

    }

}