package com.saber.green.memomania.data

import com.saber.green.memomania.model.GameDifficulty
import com.saber.green.memomania.model.Tile
import com.saber.green.memomania.utils.RandomUtils

object GameDataProvider {

    const val LEVELS_COUNT_EASY = 8
    const val LEVELS_COUNT_CLASSIC = 10
    const val LEVELS_COUNT_HARD = 14

    private const val FULL_TILES_COUNT_EASY = 9
    private const val FULL_TILES_COUNT_CLASSIC = 12
    private const val FULL_TILES_COUNT_HARD = 16

    private var tilesArrayForEachLevelEasy: ArrayList<ArrayList<Tile>>? = null
    private var tilesArrayForEachLevelClassic: ArrayList<ArrayList<Tile>>? = null
    private var tilesArrayForEachLevelHard: ArrayList<ArrayList<Tile>>? = null

    init {
        tilesArrayForEachLevelEasy = getEasyDifficultyTilesArray()
        tilesArrayForEachLevelClassic = getClassicDifficultyTilesArray()
        tilesArrayForEachLevelHard = getHardDifficultyTilesArray()
    }

    fun getActiveTilesForLevel(gameDifficulty: GameDifficulty): ArrayList<ArrayList<Tile>>? {
        return when (gameDifficulty) {
            GameDifficulty.EASY -> {
                tilesArrayForEachLevelEasy
            }
            GameDifficulty.CLASSIC -> {
                tilesArrayForEachLevelClassic
            }
            GameDifficulty.HARD -> {
                tilesArrayForEachLevelHard
            }
        }
    }

    fun refreshData() {
        tilesArrayForEachLevelEasy = getEasyDifficultyTilesArray()
        tilesArrayForEachLevelClassic = getClassicDifficultyTilesArray()
        tilesArrayForEachLevelHard = getHardDifficultyTilesArray()
    }

    private fun getEasyDifficultyTilesArray(): ArrayList<ArrayList<Tile>> {
        val tilesArrayForEachLevel = ArrayList<ArrayList<Tile>>()
        for (i in 1..LEVELS_COUNT_EASY) {
            tilesArrayForEachLevel.add(
                when (i) {
                    1 -> getUniqueTilesArray(2, 9, FULL_TILES_COUNT_EASY)
                    2 -> getUniqueTilesArray(3, 9, FULL_TILES_COUNT_EASY)
                    3 -> getUniqueTilesArray(4, 9, FULL_TILES_COUNT_EASY)
                    4 -> getUniqueTilesArray(5, 9, FULL_TILES_COUNT_EASY)
                    5 -> getUniqueTilesArray(6, 10, FULL_TILES_COUNT_EASY)
                    6 -> getUniqueTilesArray(7, 10, FULL_TILES_COUNT_EASY)
                    7 -> getUniqueTilesArray(8, 10, FULL_TILES_COUNT_EASY)
                    8 -> getUniqueTilesArray(9, 10, FULL_TILES_COUNT_EASY)
                    else -> getUniqueTilesArray(0, 0, FULL_TILES_COUNT_EASY)
                }
            )
        }
        return tilesArrayForEachLevel
    }

    private fun getClassicDifficultyTilesArray(): ArrayList<ArrayList<Tile>> {
        val tilesArrayForEachLevel = ArrayList<ArrayList<Tile>>()
        for (i in 1..LEVELS_COUNT_CLASSIC) {
            tilesArrayForEachLevel.add(
                when (i) {
                    1 -> getUniqueTilesArray(3, 9, FULL_TILES_COUNT_CLASSIC)
                    2 -> getUniqueTilesArray(4, 9, FULL_TILES_COUNT_CLASSIC)
                    3 -> getUniqueTilesArray(5, 9, FULL_TILES_COUNT_CLASSIC)
                    4 -> getUniqueTilesArray(6, 9, FULL_TILES_COUNT_CLASSIC)
                    5 -> getUniqueTilesArray(7, 10, FULL_TILES_COUNT_CLASSIC)
                    6 -> getUniqueTilesArray(8, 10, FULL_TILES_COUNT_CLASSIC)
                    7 -> getUniqueTilesArray(9, 10, FULL_TILES_COUNT_CLASSIC)
                    8 -> getUniqueTilesArray(10, 12, FULL_TILES_COUNT_CLASSIC)
                    9 -> getUniqueTilesArray(11, 12, FULL_TILES_COUNT_CLASSIC)
                    10 -> getUniqueTilesArray(12, 12, FULL_TILES_COUNT_CLASSIC)
                    else -> getUniqueTilesArray(0, 0, FULL_TILES_COUNT_CLASSIC)
                }
            )
        }
        return tilesArrayForEachLevel
    }

    private fun getHardDifficultyTilesArray(): ArrayList<ArrayList<Tile>> {
        val tilesArrayForEachLevel = ArrayList<ArrayList<Tile>>()
        for (i in 1..LEVELS_COUNT_HARD) {
            tilesArrayForEachLevel.add(
                when (i) {
                    1 -> getUniqueTilesArray(3, 9, FULL_TILES_COUNT_HARD)
                    2 -> getUniqueTilesArray(4, 9, FULL_TILES_COUNT_HARD)
                    3 -> getUniqueTilesArray(5, 9, FULL_TILES_COUNT_HARD)
                    4 -> getUniqueTilesArray(6, 9, FULL_TILES_COUNT_HARD)
                    5 -> getUniqueTilesArray(7, 10, FULL_TILES_COUNT_HARD)
                    6 -> getUniqueTilesArray(8, 10, FULL_TILES_COUNT_HARD)
                    7 -> getUniqueTilesArray(9, 10, FULL_TILES_COUNT_HARD)
                    8 -> getUniqueTilesArray(10, 12, FULL_TILES_COUNT_HARD)
                    9 -> getUniqueTilesArray(11, 12, FULL_TILES_COUNT_HARD)
                    10 -> getUniqueTilesArray(12, 12, FULL_TILES_COUNT_HARD)
                    11 -> getUniqueTilesArray(13, 13, FULL_TILES_COUNT_HARD)
                    12 -> getUniqueTilesArray(14, 14, FULL_TILES_COUNT_HARD)
                    13 -> getUniqueTilesArray(15, 15, FULL_TILES_COUNT_HARD)
                    14 -> getUniqueTilesArray(16, 16, FULL_TILES_COUNT_HARD)
                    else -> getUniqueTilesArray(0, 0, FULL_TILES_COUNT_HARD)
                }
            )
        }
        return tilesArrayForEachLevel
    }

    private fun getUniqueTilesArray(arraySize: Int, valuesMaxCount: Int, numbersMaxCount: Int): ArrayList<Tile> {
        val tilesList = ArrayList<Tile>()
        val numbers = RandomUtils.getListOfUniqueRandomElements(arraySize, numbersMaxCount)
        val values = RandomUtils.getListOfUniqueRandomElements(arraySize, valuesMaxCount)
        for (i in 0 until arraySize) {
            tilesList.add(Tile(numbers[i], values[i]))
        }
        return tilesList
    }
}