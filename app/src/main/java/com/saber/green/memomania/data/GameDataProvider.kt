package com.saber.green.memomania.data

import com.saber.green.memomania.model.Tile
import com.saber.green.memomania.utils.RandomUtils

class GameDataProvider {

    companion object {
        private var instance: GameDataProvider? = null

        @Synchronized
        fun getInstance(): GameDataProvider? {
            if (instance == null) {
                instance = GameDataProvider()
            }
            return instance
        }
    }

    private var tilesArrayForEachLevel: ArrayList<ArrayList<Tile>>? = null

    init {
        tilesArrayForEachLevel = getTilesArrayForEachLevel()
    }

    @Synchronized
    fun getActiveTilesForLevel(): ArrayList<ArrayList<Tile>>? {
        return tilesArrayForEachLevel
    }

    @Synchronized
    fun refreshData() {
        tilesArrayForEachLevel = getTilesArrayForEachLevel()
    }

    private fun getTilesArrayForEachLevel(): ArrayList<ArrayList<Tile>> {
        val tilesArrayForEachLevel = ArrayList<ArrayList<Tile>>()
        for (i in 1..10) {
            tilesArrayForEachLevel.add(
                when (i) {
                    1 -> getUniqueTilesArray(3, 9)
                    2 -> getUniqueTilesArray(4, 9)
                    3 -> getUniqueTilesArray(5, 9)
                    4 -> getUniqueTilesArray(6, 9)
                    5 -> getUniqueTilesArray(7, 10)
                    6 -> getUniqueTilesArray(8, 10)
                    7 -> getUniqueTilesArray(9, 10)
                    8 -> getUniqueTilesArray(10, 12)
                    9 -> getUniqueTilesArray(11, 12)
                    10 -> getUniqueTilesArray(12, 12)
                    else -> getUniqueTilesArray(0, 0)
                }
            )
        }
        return tilesArrayForEachLevel
    }

    private fun getUniqueTilesArray(arraySize: Int, valuesMaxCount: Int, numbersMaxCount: Int = 12): ArrayList<Tile> {
        val tilesList = ArrayList<Tile>()
        val numbers = RandomUtils.getListOfUniqueRandomElements(arraySize, numbersMaxCount)
        val values = RandomUtils.getListOfUniqueRandomElements(arraySize, valuesMaxCount)
        for (i in 0 until arraySize) {
            tilesList.add(Tile(numbers[i], values[i]))
        }
        return tilesList
    }
}