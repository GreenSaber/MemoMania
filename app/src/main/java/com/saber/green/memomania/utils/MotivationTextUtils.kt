package com.saber.green.memomania.utils

class MotivationTextUtils {

    companion object {

        private val wishesList = arrayListOf(
            "Super!",
            "Good work!",
            "Great!",
            "Not bad!",
            "Nice going!",
            "Wow!",
            "Terrific!",
            "Nothing can stop you now!",
            "Sensational!",
            "Excellent!",
            "That's the best ever!",
            "Perfect!",
            "That's better than ever!",
            "Much better!",
            "Wonderful!",
            "Fine!",
            "Nice going!",
            "Outstanding!",
            "Fantastic!",
            "That's great!",
            "Right on!",
            "You're really improving!",
            "You're doing beautifully!",
            "Superb!",
            "Congratulations!",
            "Marvelous!",
            "Nice job!"
        )

        fun getRandomText(): String {
            return wishesList.random()
        }

    }
}