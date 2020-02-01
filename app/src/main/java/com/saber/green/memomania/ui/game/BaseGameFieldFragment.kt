package com.saber.green.memomania.ui.game

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton
import com.saber.green.memomania.R
import com.saber.green.memomania.model.GameLifecycle
import com.saber.green.memomania.ui.GameOverActivity
import com.saber.green.memomania.ui.NextLevelActivity
import com.saber.green.memomania.ui.WinActivity
import com.saber.green.memomania.utils.AnimationUtils
import com.saber.green.memomania.utils.SoundPool
import com.saber.green.memomania.utils.VibrationUtils
import com.saber.green.memomania.viewmodel.GameViewModel
import java.util.*
import kotlin.collections.ArrayList

open class BaseGameFieldFragment : Fragment(){
    private lateinit var viewModel: GameViewModel
    private val TAG = "GameActivity"
    private val INITIAL_SHOW_DELAY : Long = 2000
    private var correctSound : Int? = null
    private val activeButtons = ArrayList<MaterialButton>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(activity!!).get(GameViewModel::class.java)
        initActiveButtons()
        initSounds()
        highlightActiveButtons(activity!!)
        showValueOfActiveButtons(activity!!)
        hideValueOfActiveButtons(activity!!)
        onActiveButtonClick(activity!!)
    }

    fun initActiveButtons() {
        val activeTiles = viewModel.getActiveTiles()
        activeTiles.forEach { Log.i(TAG, "Tile [number : ${it.getNumber()}; value : ${it.getValue()}]") }
        activeTiles.forEach {
            activeButtons.add(activity!!.findViewById(resources.getIdentifier("gameTileButton${it.getNumber()}", "id", activity!!.packageName)))
        }
    }

    fun initSounds(){
        correctSound = SoundPool.getInstance()!!.load(activity!!.applicationContext, R.raw.laser, 1)
    }

    fun highlightActiveButtons(context: Context) {
        activeButtons.forEach {setTileParams(context, it, "", R.color.accent_color, false)}
    }

    fun showValueOfActiveButtons(context: Context) {
        Timer(false).schedule(object : TimerTask() {
            override fun run() {
                activity?.runOnUiThread {
                    activeButtons.forEach {setTileParams(context, it, getButtonValue(it), R.color.dark_button_color, false)}
                }
            }
        }, INITIAL_SHOW_DELAY)
    }

    fun hideValueOfActiveButtons(context: Context) {
        Timer(false).schedule(object : TimerTask() {
            override fun run() {
                activity?.runOnUiThread {
                    activeButtons.forEach {setTileParams(context, it, "", R.color.accent_color, true)}
                }
            }
        }, INITIAL_SHOW_DELAY + viewModel.getShowTiming())
    }

    fun onActiveButtonClick(context: Context) {
        activeButtons.forEach { it ->
            it.setOnClickListener {
                val button: MaterialButton = activity?.findViewById(it.id)!!
                val buttonValue = getButtonValue(button)

                when (viewModel.getGameLifecycle(buttonValue)) {
                    GameLifecycle.CORRECT_VALUE -> {
                        playCorrectSoundIfOn()
                        vibrateIfOn(VibrationUtils.PATTERN_CORRECT_VALUE)
                        AnimationUtils.correctValueTileAnimation(activity!!, button)
                        setTileParams(context, button, buttonValue, R.color.dark_button_color,false)
                    }
                    GameLifecycle.INCORRECT_VALUE -> {
                        vibrateIfOn(VibrationUtils.PATTERN_INCORRECT_VALUE)
                        AnimationUtils.incorrectValueTileAnimation(activity!!, button)
                    }
                    GameLifecycle.NEXT_LEVEL -> {
                        playCorrectSoundIfOn()
                        vibrateIfOn(VibrationUtils.PATTERN_CORRECT_VALUE)
                        AnimationUtils.correctValueTileAnimation(activity!!, button)
                        setTileParams(context, button, buttonValue, R.color.dark_button_color,false)
                        navigateToNextLevelActivity()
                    }
                    GameLifecycle.WIN -> {
                        playCorrectSoundIfOn()
                        vibrateIfOn(VibrationUtils.PATTERN_CORRECT_VALUE)
                        AnimationUtils.correctValueTileAnimation(activity!!, button)
                        setTileParams(context, button, buttonValue, R.color.dark_button_color,false)
                        navigateToWinActivity()
                    }
                    GameLifecycle.GAME_OVER -> {
                        vibrateIfOn(VibrationUtils.PATTERN_INCORRECT_VALUE)
                        AnimationUtils.incorrectValueTileAnimation(activity!!, button)
                        navigateToGameOverActivity()
                    }
                }
            }
        }
    }

    private fun playCorrectSoundIfOn(){
        if (viewModel.getSoundStatus().value!!) {
            SoundPool.getInstance()!!.play(correctSound!!, 1F, 1F, 0, 0, 1F)
        }
    }

    private fun vibrateIfOn(pattern : LongArray){
        if (viewModel.getVibrationStatus().value!!) {
            VibrationUtils.vibrate(activity as AppCompatActivity, pattern)
        }
    }

    private fun navigateToWinActivity(){
        val intent = Intent(activity, WinActivity::class.java)
        Timer(false).schedule(object : TimerTask() {
            override fun run() {
                activity?.runOnUiThread {
                    startActivity(intent)
                    activity?.overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left)
                }
            }
        }, AnimationUtils.DURATION)
    }

    private fun navigateToNextLevelActivity(){
        val intent = Intent(activity, NextLevelActivity::class.java)
        Timer(false).schedule(object : TimerTask() {
            override fun run() {
                activity?.runOnUiThread {
                    startActivity(intent)
                    activity?.overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left)
                }
            }
        }, AnimationUtils.DURATION * 2)
    }

    private fun navigateToGameOverActivity(){
        val intent = Intent(activity, GameOverActivity::class.java)
        Timer(false).schedule(object : TimerTask() {
            override fun run() {
                activity?.runOnUiThread {
                    startActivity(intent)
                    activity?.overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left)
                }
            }
        }, AnimationUtils.INCORRECT_DURATION)
    }

    private fun setTileParams(context: Context, button: MaterialButton, buttonValue: String, color: Int, isAvailable : Boolean) {
        button.setBackgroundColor(ContextCompat.getColor(context, color))
        button.text = buttonValue
        button.isClickable = isAvailable
        button.isEnabled = isAvailable
    }

    private fun getButtonValue(materialButton: MaterialButton): String {
        val buttonNumber = materialButton.resources.getResourceName(materialButton.id)
            .replace("${activity?.packageName}:id/gameTileButton", "").toInt()
        val tile = viewModel.getActiveTiles().find { it.getNumber() == buttonNumber }
        return tile?.getValue().toString()
    }
}