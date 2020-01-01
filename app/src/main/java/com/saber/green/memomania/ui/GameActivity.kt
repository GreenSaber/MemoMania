package com.saber.green.memomania.ui

import android.content.Context
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.button.MaterialButton
import com.saber.green.memomania.R
import com.saber.green.memomania.model.GameLifecycle
import com.saber.green.memomania.utils.AnimationUtils
import com.saber.green.memomania.viewmodel.GameViewModel
import kotlinx.android.synthetic.main.activity_game.*
import java.util.*
import kotlin.collections.ArrayList


class GameActivity : AppCompatActivity() {

    private val TAG = "GameActivity"
    private lateinit var gameViewModel: GameViewModel
    private val activeButtons = ArrayList<MaterialButton>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        gameViewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)

        initActiveButtons()
        highlightActiveButtons(this)
        showValueOfActiveButtons(this)
        hideValueOfActiveButtons(this)

        initLevelObserver()
        initLifeObserver()

        onActiveButtonClick()
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

    fun initActiveButtons() {
        val activeTiles = gameViewModel.getActiveTiles()
        Log.i(TAG, "Tiles size : ${activeTiles.size}")
        activeTiles.forEach { Log.i(TAG, "Tile [number : ${it.getNumber()}; value : ${it.getValue()}]") }
        activeTiles.forEach {
            activeButtons.add(findViewById(resources.getIdentifier("gameTileButton${it.getNumber()}", "id", packageName)))
        }
    }

    fun highlightActiveButtons(context: Context) {
        activeButtons.forEach { it.setBackgroundColor(ContextCompat.getColor(context, R.color.accent_color)) }
        activeButtons.forEach { it.isEnabled = false }
    }

    fun showValueOfActiveButtons(context: Context) {
        Timer(false).schedule(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    activeButtons.forEach { it.setBackgroundColor(ContextCompat.getColor(context, R.color.dark_button_color)) }
                    activeButtons.forEach { it.text = getButtonValue(it) }
                    activeButtons.forEach { it.isEnabled = false }
                }
            }
        }, 2000)
    }

    fun hideValueOfActiveButtons(context: Context) {
        Timer(false).schedule(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    activeButtons.forEach { it.setBackgroundColor(ContextCompat.getColor(context, R.color.accent_color)) }
                    activeButtons.forEach { it.text = "" }
                    activeButtons.forEach { it.isEnabled = true }
                }
            }
        }, 5500)
    }

    fun onActiveButtonClick() {
        activeButtons.forEach { it ->
            it.setOnClickListener {
                val button: MaterialButton = findViewById(it.id)
                val buttonValue = getButtonValue(button)

                when (gameViewModel.getGameLifecycle(buttonValue)) {

                    GameLifecycle.CORRECT_VALUE -> {
                        AnimationUtils.viewColorAnimation1(this, button, R.color.accent_color, R.color.green, R.color.dark_button_color, 2 * AnimationUtils.DURATION)
                        AnimationUtils.scaleAnimation(button, 1.07f, AnimationUtils.DURATION)
                        button.text = buttonValue
                        button.isClickable = false
                    }

                    GameLifecycle.INCORRECT_VALUE -> {
                        AnimationUtils.viewColorAnimation(this, button, R.color.accent_color, R.color.red, AnimationUtils.DURATION, 1)
                        AnimationUtils.layoutColorAnimation(this, life_card.background as GradientDrawable, R.color.accent_color, R.color.red, AnimationUtils.DURATION)
                        AnimationUtils.scaleAnimation(heart_icon, 1.5f, AnimationUtils.DURATION)
                        AnimationUtils.scaleAnimation(button, 1.07f, AnimationUtils.DURATION)
                    }

                    GameLifecycle.NEXT_LEVEL -> {

                        AnimationUtils.viewColorAnimation1(this, button, R.color.accent_color, R.color.green, R.color.dark_button_color, 2 * AnimationUtils.DURATION)
                        AnimationUtils.scaleAnimation(button, 1.07f, AnimationUtils.DURATION)
                        button.text = buttonValue
                        button.isClickable = false

                        val intent = Intent(this, NextLevelActivity::class.java)
                        Timer(false).schedule(object : TimerTask() {
                            override fun run() {
                                runOnUiThread {
                                    startActivity(intent)
                                    overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left)
                                }
                            }
                        }, AnimationUtils.DURATION)
                    }

                    GameLifecycle.WIN -> {
                        val intent = Intent(this, WinActivity::class.java)
                        Timer(false).schedule(object : TimerTask() {
                            override fun run() {
                                runOnUiThread { startActivity(intent) }
                            }
                        }, AnimationUtils.DURATION)
                    }

                    GameLifecycle.GAME_OVER -> {
                        AnimationUtils.viewColorAnimation(this, button, R.color.accent_color, R.color.red, AnimationUtils.DURATION, 1)
                        AnimationUtils.layoutColorAnimation(this, life_card.background as GradientDrawable, R.color.accent_color, R.color.red, AnimationUtils.DURATION)
                        AnimationUtils.scaleAnimation(heart_icon, 1.5f, AnimationUtils.DURATION)
                        AnimationUtils.scaleAnimation(button, 1.07f, AnimationUtils.DURATION)

                        val intent = Intent(this, GameOverActivity::class.java)
                        Timer(false).schedule(object : TimerTask() {
                            override fun run() {
                                runOnUiThread {
                                    startActivity(intent)
                                    overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left)
                                }
                            }
                        }, AnimationUtils.DURATION)
                    }
                }
            }
        }
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

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right)
    }

    private fun getButtonValue(materialButton: MaterialButton): String {
        val buttonNumber = materialButton.resources.getResourceName(materialButton.id)
            .replace("${packageName}:id/gameTileButton", "").toInt()
        val tile = gameViewModel.getActiveTiles().find { it.getNumber() == buttonNumber }
        return tile?.getValue().toString()
    }
}
