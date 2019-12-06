package com.saber.green.memomania.ui

import android.content.Context
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.button.MaterialButton
import com.saber.green.memomania.R
import com.saber.green.memomania.model.Tile
import com.saber.green.memomania.utils.AnimationUtils
import com.saber.green.memomania.viewmodel.GameViewModel
import kotlinx.android.synthetic.main.activity_game.*
import java.util.*


class GameActivity : AppCompatActivity() {

    private val activeButtons = arrayListOf<MaterialButton>()
    private lateinit var activeTiles: ArrayList<Tile>
    private lateinit var gameViewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        gameViewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)

        setCurrentLevel()
        setCurrentLifes()

        initActiveButtons()
        showActiveButtons(this)
        hideActiveButtons(this)
        onActiveButtonClick()

        onLifeButtonClicked()
        onHeartIconClicked()
    }

    fun setCurrentLevel() {
        level_number.text = gameViewModel.getGameLevel().toString()
    }

    fun setCurrentLifes() {
        life_number.text = gameViewModel.getCurrentLifesCount().toString()
    }

    fun initActiveButtons() {
        activeTiles = gameViewModel.getActiveTiles()
        activeTiles.forEach {
            activeButtons.add(findViewById(resources.getIdentifier("materialButton${it.getNumber()}", "id", packageName)))
        }
        activeButtons.forEach { it.setBackgroundColor(ContextCompat.getColor(this, R.color.accent_color)) }
        activeButtons.forEach { it.isEnabled = false }
    }

    fun showActiveButtons(context: Context) {
        val t = Timer(false)
        t.schedule(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    activeButtons.forEach { it.setBackgroundColor(ContextCompat.getColor(context, R.color.dark_button_color)) }
                    activeButtons.forEach { it.text = getButtonValue(it) }
                    activeButtons.forEach { it.isEnabled = false }
                }
            }
        }, 2000)
    }

    fun hideActiveButtons(context: Context) {
        val t = Timer(false)
        t.schedule(object : TimerTask() {
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

                if (gameViewModel.isValueCorrect(buttonValue)) {
                    AnimationUtils.viewColorAnimation1(this, button, R.color.accent_color, R.color.green, R.color.dark_button_color, 1000)
                    AnimationUtils.scaleAnimation(button, 1.07f, 500)
                    button.text = buttonValue
                    button.isClickable = false
                } else {
                    AnimationUtils.viewColorAnimation(this, button, R.color.accent_color, R.color.red, 500, 1)
                    AnimationUtils.layoutColorAnimation(this, life_card.background as GradientDrawable, R.color.accent_color, R.color.red, 500)
                    AnimationUtils.scaleAnimation(heart_icon, 1.5f, 500)
                    AnimationUtils.scaleAnimation(button, 1.07f, 500)
                    gameViewModel.reduceLifeCounter()
                }

                if (gameViewModel.isLevelPassed()){
                    val intent = Intent(this, NextLevelActivity::class.java)
                    startActivity(intent)
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

    private fun onLifeButtonClicked() {
        level_card.setOnClickListener {
            val intent = Intent(this, NextLevelActivity::class.java)
            startActivity(intent)
        }
    }

    private fun onHeartIconClicked() {
        heart_icon.setOnClickListener {
            AnimationUtils.layoutColorAnimation(this, life_card.background as GradientDrawable, R.color.accent_color, R.color.red, 500)
            AnimationUtils.scaleAnimation(heart_icon, 1.5f, 500)
        }
    }
}
