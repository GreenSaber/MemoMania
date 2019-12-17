package com.saber.green.memomania.ui

import android.content.Context
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.button.MaterialButton
import com.saber.green.memomania.R
import com.saber.green.memomania.model.GameLifecycle
import com.saber.green.memomania.model.Tile
import com.saber.green.memomania.utils.AnimationUtils
import com.saber.green.memomania.viewmodel.GameViewModel
import kotlinx.android.synthetic.main.activity_game.*
import java.util.*


class GameActivity : AppCompatActivity() {

    private lateinit var gameViewModel: GameViewModel
    private lateinit var activeTiles: ArrayList<Tile>
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
                    runOnUiThread { life_number.text = gameViewModel.getLifeCount().value }
                }
            }, AnimationUtils.DURATION)
        })
    }

    fun initActiveButtons() {
        activeTiles = gameViewModel.getActiveTiles()
        activeTiles.forEach {
            activeButtons.add(findViewById(resources.getIdentifier("materialButton${it.getNumber()}", "id", packageName)))
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
                        val intent = Intent(this, GameOverActivity::class.java)
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
                                runOnUiThread { startActivity(intent) }
                            }
                        }, AnimationUtils.DURATION)
                    }
                }
            }
        }
    }

    private fun getButtonValue(materialButton: MaterialButton): String {
        val buttonNumber = materialButton.resources.getResourceName(materialButton.id)
            .replace("${packageName}:id/materialButton", "").toInt()
        val tile = activeTiles.find { it.getNumber() == buttonNumber }
        return tile?.getValue().toString()
    }
}
