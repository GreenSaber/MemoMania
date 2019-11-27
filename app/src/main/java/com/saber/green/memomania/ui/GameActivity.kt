package com.saber.green.memomania.ui

import android.content.Context
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.saber.green.memomania.R
import com.saber.green.memomania.model.Game
import com.saber.green.memomania.model.Tile
import com.saber.green.memomania.utils.AnimationUtils
import kotlinx.android.synthetic.main.activity_game.*
import java.util.*


class GameActivity : AppCompatActivity() {

    private val activeButtons = arrayListOf<MaterialButton>()
    private lateinit var activeTiles : ArrayList<Tile>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        initActiveButtons()
        showActiveButtons(this)
        hideActiveButtons(this)
        onActiveButtonClick()

        onLifeButtonClicked()
        onHeartIconClicked()
        onButtonClicked()
    }

    fun initActiveButtons(){
        activeTiles= Game(1).getActiveTiles()!!
        activeTiles.forEach {
            activeButtons.add(findViewById(resources.getIdentifier("materialButton${it.getNumber()}", "id", packageName)))
        }
        activeButtons.forEach { it.setBackgroundColor(ContextCompat.getColor(this, R.color.accent_color)) }
        activeButtons.forEach { it.isEnabled = false }
    }

    fun showActiveButtons(context: Context){
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

    fun hideActiveButtons(context: Context){
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
            it.setOnClickListener{
                val button: MaterialButton = findViewById(it.id)
                button.text = getButtonValue(button)
                AnimationUtils.scaleAnimation(button, 1.07f, 200)
                button.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_button_color))
            }
        }
    }


    private fun getButtonValue(materialButton: MaterialButton) : String {
        val buttonNumber = materialButton.resources.getResourceName(materialButton.id).replace("${packageName}:id/materialButton", "").toInt()
        val tile = activeTiles.find { it.getNumber() == buttonNumber }
        return tile?.getValue().toString()
    }


    private fun onLifeButtonClicked() {
        level_card.setOnClickListener {
            val intent = Intent(this, LevelActivity::class.java)
            startActivity(intent)
        }
    }

    private fun onHeartIconClicked() {
        heart_icon.setOnClickListener {
            AnimationUtils.colorAnimation(this, life_card.background as GradientDrawable, R.color.accent_color, R.color.red, 500)
            AnimationUtils.scaleAnimation(heart_icon, 1.5f, 500)
        }
    }

    private fun onButtonClicked() {
        materialButton4.setOnClickListener {
            AnimationUtils.scaleAnimation(it, 1.07f, 200)

        }
    }
}
