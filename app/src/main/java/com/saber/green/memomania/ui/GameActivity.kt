package com.saber.green.memomania.ui

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.saber.green.memomania.R
import com.saber.green.memomania.utils.AnimationUtils
import kotlinx.android.synthetic.main.activity_game.*


class GameActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        init()
    }

    fun init() {
        onLifeButtonClicked()
        onHeartIconClicked()
        onButtonClicked()
    }

    fun onLifeButtonClicked() {
        level_card.setOnClickListener {
            val intent = Intent(this, LevelActivity::class.java)
            startActivity(intent)
        }
    }

    fun onHeartIconClicked() {
        heart_icon.setOnClickListener {
            AnimationUtils.colorAnimation(this, life_card.background as GradientDrawable, R.color.accent_color, R.color.red, 500)
            AnimationUtils.scaleAnimation(heart_icon, 1.5f, 500)
        }
    }

    fun onButtonClicked() {
        materialButton4.setOnClickListener {
            AnimationUtils.scaleAnimation(it, 1.07f, 200)

        }
    }
}
