package com.saber.green.memomania.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saber.green.memomania.model.Game
import com.saber.green.memomania.model.GameLifecycle
import com.saber.green.memomania.model.Tile

class GameFieldClassicViewModel : ViewModel() {

    private var rightAnswersCount: Int = 0
    private var wrongAnswersCount: Int = 0
    private val lifeCount = MutableLiveData<String>()
    private val levelCount = MutableLiveData<String>()

    init {
        lifeCount.value = Game.getLifesCount().toString()
        levelCount.value = Game.getLevel().toString()
    }

    fun getActiveTiles(): ArrayList<Tile> {
        return Game.getActiveTiles()!!
    }

    fun getShowTiming(): Long {
        return Game.getShowTiming()!!
    }

    private fun getSortedTiles(): List<Tile> {
        return getActiveTiles().sortedWith(compareBy { it.getValue() })

    }


    fun getGameLifecycle(value: String): GameLifecycle {
        if (getSortedTiles()[rightAnswersCount].getValue() == value.toInt()) {
            rightAnswersCount++
            if (rightAnswersCount == getActiveTiles().size) {
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
            wrongAnswersCount++
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

}
