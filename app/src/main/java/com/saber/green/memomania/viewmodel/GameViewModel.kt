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
        if ((getSortedTiles()[rightAnswearsCount].getValue() == value.toInt()) && Life.getLifesCount() > 0) {
            rightAnswearsCount++
            if ((getActiveTiles().size == rightAnswearsCount) && (Life.getLifesCount() > 0) && (Game.getLevel() < 10)) {
                val level = Game.getLevel() + 1
                Game.setLevel(level)
                return GameLifecycle.NEXT_LEVEL
            } else {
                return GameLifecycle.CORRECT_VALUE
            }
        }
        if ((getSortedTiles()[rightAnswearsCount].getValue() != value.toInt()) && Life.getLifesCount() > 0) {
            wrongAnswearsCount++
            val currentLifesCount = Life.getLifesCount() - 1
            Life.setLifesCount(currentLifesCount)
            lifeCount.value = Life.getLifesCount().toString()
            return GameLifecycle.INCORRECT_VALUE
        }

        if ((getActiveTiles().size == rightAnswearsCount) && (Life.getLifesCount() > 0) && (Game.getLevel() >= 10)) {
            return GameLifecycle.WIN
        } else {
            return GameLifecycle.GAME_OVER
        }
    }

    private fun getSortedTiles(): List<Tile> {
        return getActiveTiles().sortedWith(compareBy { it.getValue() })

    }

}