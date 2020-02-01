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
    private val wrongAnswers = MutableLiveData<Int>()
    private val soundStatus = MutableLiveData<Boolean>()
    private val vibrationStatus = MutableLiveData<Boolean>()

    init {
        lifes.value = Game.getLifesCount().toString()
        soundStatus.value = Game.getSoundStatus()
        vibrationStatus.value = Game.getVibrationStatus()
    }

    fun getLifes(): LiveData<String> = lifes

    fun getCurrentLevel(): String = Game.getLevel().toString()

    fun getWrongAnswers(): LiveData<Int> = wrongAnswers

    fun getSoundStatus(): LiveData<Boolean> = soundStatus

    fun getVibrationStatus(): LiveData<Boolean> = vibrationStatus

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

    fun onSoundButtonClick() {
        val statusToSet: Boolean = !Game.getSoundStatus()
        Game.setSoundStatus(statusToSet)
        soundStatus.value = statusToSet
    }

    fun onVibrationButtonClick() {
        val statusToSet: Boolean = !Game.getVibrationStatus()
        Game.setVibrationStatus(statusToSet)
        vibrationStatus.value = statusToSet
    }

    fun resetGame(){
        Game.resetGame()
    }

    private fun getSortedTiles(): List<Tile> {
        return getActiveTiles().sortedWith(compareBy { it.getValue() })

    }

}