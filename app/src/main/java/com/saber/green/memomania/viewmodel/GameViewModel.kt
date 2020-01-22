package com.saber.green.memomania.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.saber.green.memomania.model.Game
import com.saber.green.memomania.model.GameLifecycle
import com.saber.green.memomania.model.Tile

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private var rightAnswersCount: Int = 0
    private var wrongAnswersCount: Int = 0
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

    fun getShowTiming(): Long {
        return Game.getShowTiming()!!
    }

    fun getGameLifecycle(value: String): GameLifecycle {
        if (getSortedTiles()[rightAnswersCount].getValue() == value.toInt()) {
            rightAnswersCount++
            if (rightAnswersCount == getActiveTiles().size) {
                val level = Game.getLevel() + 1
                Game.setLevel(level)
                levelCount.value = Game.getLevel().toString()
                if (Game.getLevel() < 11) {
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

    fun resetGame(){
        Game.resetGame()
    }

    private fun getSortedTiles(): List<Tile> {
        return getActiveTiles().sortedWith(compareBy { it.getValue() })

    }

}