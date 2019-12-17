package com.saber.green.memomania.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.saber.green.memomania.model.Game
import com.saber.green.memomania.model.GameLifecycle
import com.saber.green.memomania.model.Life
import com.saber.green.memomania.model.Tile

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private var rightAnswearsCount: Int = 0
    private var wrongAnswearsCount: Int = 0
    private val lifeCount = MutableLiveData<String>()
    private val levelCount = MutableLiveData<String>()

    init {
        lifeCount.value = Life.getLifesCount().toString()
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
                if (Game.getLevel() < 10) {
                    return GameLifecycle.NEXT_LEVEL
                } else {
                    return GameLifecycle.WIN
                }
            } else {
                return GameLifecycle.CORRECT_VALUE
            }
        } else {
            wrongAnswearsCount++
            val currentLifesCount = Life.getLifesCount() - 1
            Life.setLifesCount(currentLifesCount)
            lifeCount.value = Life.getLifesCount().toString()
            if (Life.getLifesCount() > 0) {
                return GameLifecycle.INCORRECT_VALUE
            } else {
                return GameLifecycle.GAME_OVER
            }
        }
    }

    private fun getSortedTiles(): List<Tile> {
        return getActiveTiles().sortedWith(compareBy { it.getValue() })

    }

}