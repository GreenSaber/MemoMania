package com.saber.green.memomania.model

import com.saber.green.memomania.data.GameDataProvider

class Game {

    companion object {
        private var lifesCount: Int = 5
        private var levelNumber: Int = 1
        private var difficulty: GameDifficulty = GameDifficulty.CLASSIC

        fun getDifficulty(): GameDifficulty {
            return difficulty
        }

        fun setDifficulty(difficulty : GameDifficulty){
            this.difficulty = difficulty
        }

        fun getLevel(): Int {
            return levelNumber
        }

        fun setLevel(number: Int) {
            levelNumber = number
        }

        fun getLifesCount(): Int {
            return lifesCount
        }

        fun getLevelsCount(): Int {
            return when (difficulty) {
                GameDifficulty.EASY -> {
                    GameDataProvider.getInstance()!!.LEVELS_COUNT_EASY
                }
                GameDifficulty.CLASSIC -> {
                    GameDataProvider.getInstance()!!.LEVELS_COUNT_CLASSIC
                }
                GameDifficulty.HARD -> {
                    GameDataProvider.getInstance()!!.LEVELS_COUNT_HARD
                }
            }
        }

        fun setLifesCount(count: Int) {
            lifesCount = count
        }

        fun getActiveTiles(): ArrayList<Tile>? {
            return GameDataProvider.getInstance()?.getActiveTilesForLevel(difficulty)?.get(levelNumber-1)
        }

        fun getShowTiming(): Long? {
            return GameDataProvider.getInstance()?.getShowTimingValueForLevel()?.get(levelNumber-1)
        }

        fun refreshData() {
            GameDataProvider.getInstance()?.refreshData()
        }

        fun resetGame(){
            GameDataProvider.getInstance()?.refreshData()
            lifesCount = 5
            levelNumber = 1
        }
    }

}