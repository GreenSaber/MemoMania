package com.saber.green.memomania.model

import com.saber.green.memomania.data.GameDataProvider

class Game {

    companion object {
        private var lifesCount: Int = 5
        private var levelNumber: Int = 1


        fun getLevel(): Int {
            return levelNumber
        }

        fun setLevel(number: Int) {
            levelNumber = number
        }

        fun getLifesCount(): Int {
            return lifesCount
        }

        fun setLifesCount(count: Int) {
            lifesCount = count
        }

        fun getActiveTiles(): ArrayList<Tile>? {
            return GameDataProvider.getInstance()?.getActiveTilesForLevel()?.get(levelNumber-1)
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