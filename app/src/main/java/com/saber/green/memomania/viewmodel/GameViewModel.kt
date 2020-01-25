package com.saber.green.memomania.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.saber.green.memomania.model.Game
import com.saber.green.memomania.model.GameDifficulty
import com.saber.green.memomania.model.GameLifecycle
import com.saber.green.memomania.model.Tile

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private var rightAnswersCount: Int = 0
    private var wrongAnswersCount: Int = 0
    private val lifes = MutableLiveData<String>()
    private val levels = MutableLiveData<String>()
    private val wrongAnswers = MutableLiveData<Int>()

    init {
        lifes.value = Game.getLifesCount().toString()
        levels.value = Game.getLevel().toString()
    }

    fun getLifes(): LiveData<String> = lifes

    fun getLevels(): LiveData<String> = levels

    fun getWrongAnswers(): LiveData<Int> = wrongAnswers

    fun getActiveTiles(): ArrayList<Tile> {
        return Game.getActiveTiles()!!
    }

    fun getShowTiming(): Long {
        return Game.getShowTiming()!!
    }

    fun getDifficulty(): GameDifficulty {
        return Game.getDifficulty()
    }

    fun getGameLifecycle(value: String): GameLifecycle {
        if (getSortedTiles()[rightAnswersCount].getValue() == value.toInt()) {
            rightAnswersCount++
            if (rightAnswersCount == getActiveTiles().size) {
                val level = Game.getLevel() + 1
                Game.setLevel(level)
                levels.value = Game.getLevel().toString()
                if (Game.getLevel() <= Game.getLevelsCount()) {
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
            wrongAnswers.value = wrongAnswersCount
            lifes.value = Game.getLifesCount().toString()
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