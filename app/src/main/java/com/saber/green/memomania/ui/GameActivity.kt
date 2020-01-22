package com.saber.green.memomania.ui

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.saber.green.memomania.R
import com.saber.green.memomania.utils.AnimationUtils
import com.saber.green.memomania.utils.VibrationUtils
import com.saber.green.memomania.viewmodel.GameViewModel
import kotlinx.android.synthetic.main.activity_game.*
import java.util.*


class GameActivity : AppCompatActivity(),
    GameFieldClassicFragment.GameFieldClassicFragmentListener {

    private lateinit var gameViewModel: GameViewModel
    private lateinit var vibrationUtils: VibrationUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        gameViewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)
        val fragment: GameFieldClassicFragment = GameFieldClassicFragment.newInstance()
        supportFragmentManager.beginTransaction().replace(R.id.grid_layout_container, fragment)
            .commit()

        initLevelObserver()
        initLifeObserver()
        onHomeButtonClick()
        onRestartButtonClick()
    }

    fun initLevelObserver() {
        gameViewModel.getLevelCount().observe(this, Observer {
            level_number.text = gameViewModel.getLevelCount().value
        })
    }

    fun initLifeObserver() {
        gameViewModel.getLifeCount().observe(this, Observer {
            Timer(false).schedule(object : TimerTask() {
                override fun run() {
                    runOnUiThread {
                        life_number.text = gameViewModel.getLifeCount().value
                    }
                }
            }, AnimationUtils.DURATION)
        })
    }


    fun onHomeButtonClick() {
        home_button.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right)
        }
    }

    fun onRestartButtonClick() {
        restart_button.setOnClickListener {
            gameViewModel.resetGame()
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.anim_slide_in_top, R.anim.anim_slide_out_top)
        }
    }

    override fun onIncorrect() {
        AnimationUtils.layoutColorAnimation(this as AppCompatActivity, life_card.background as GradientDrawable, R.color.accent_color, R.color.red, AnimationUtils.INCORRECT_DURATION)
        AnimationUtils.scaleAnimation(heart_icon, 1.5f, AnimationUtils.INCORRECT_DURATION)
    }
}
