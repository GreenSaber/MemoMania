package com.saber.green.memomania.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.saber.green.memomania.model.Game
import com.saber.green.memomania.model.GameDifficulty

class MenuViewModel(application: Application) : AndroidViewModel(application) {

    private val difficulty = MutableLiveData<GameDifficulty>()
    private val soundStatus = MutableLiveData<Boolean>()
    private val vibrationStatus = MutableLiveData<Boolean>()
    private val isGameInProgress = MutableLiveData<Boolean>()

    init {
        difficulty.value = Game.getDifficulty()
        soundStatus.value = Game.getSoundStatus()
        vibrationStatus.value = Game.getVibrationStatus()
        isGameInProgress.value = Game.getGameInProgressStatus()
    }

    fun getDifficulty(): LiveData<GameDifficulty> {
        return difficulty
    }

    fun getSoundStatus(): LiveData<Boolean> {
        return soundStatus
    }

    fun getVibrationStatus(): LiveData<Boolean> {
        return vibrationStatus
    }

    fun getGameInProgressStatus(): LiveData<Boolean> {
        return isGameInProgress
    }

    fun onSoundButtonClick() {
        val statusToSet: Boolean = !Game.getSoundStatus()
        Game.setSoundStatus(statusToSet)
        soundStatus.value = Game.getSoundStatus()
    }

    fun onVibrationButtonClick() {
        val statusToSet: Boolean = !Game.getVibrationStatus()
        Game.setVibrationStatus(statusToSet)
        vibrationStatus.value = Game.getVibrationStatus()
    }

    fun onRightArrowButtonClick() {
        when (difficulty.value!!) {
            GameDifficulty.EASY -> {
                Game.setDifficulty(GameDifficulty.CLASSIC)
            }
            GameDifficulty.CLASSIC -> {
                Game.setDifficulty(GameDifficulty.HARD)
            }
            GameDifficulty.HARD -> {
                Game.setDifficulty(GameDifficulty.EASY)
            }
        }
        Game.resetGame()
        difficulty.value = Game.getDifficulty()
        isGameInProgress.value = Game.getGameInProgressStatus()
    }

    fun onLeftArrowButtonClick() {
        when (difficulty.value!!) {
            GameDifficulty.EASY -> {
                Game.setDifficulty(GameDifficulty.HARD)
            }
            GameDifficulty.CLASSIC -> {
                Game.setDifficulty(GameDifficulty.EASY)
            }
            GameDifficulty.HARD -> {
                Game.setDifficulty(GameDifficulty.CLASSIC)
            }
        }
        Game.resetGame()
        difficulty.value = Game.getDifficulty()
        isGameInProgress.value = Game.getGameInProgressStatus()
    }

    fun onPlayButtonClick() {
        Game.refreshData()
        Game.setGameInProgressStatus(true)
    }
}