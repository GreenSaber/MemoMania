package com.saber.green.memomania.ui

import android.content.Context
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.button.MaterialButton
import com.saber.green.memomania.R
import com.saber.green.memomania.model.GameLifecycle
import com.saber.green.memomania.utils.AnimationUtils
import com.saber.green.memomania.utils.VibrationUtils
import com.saber.green.memomania.viewmodel.GameFieldClassicViewModel
import kotlinx.android.synthetic.main.activity_game.*
import java.util.*
import kotlin.collections.ArrayList


class GameFieldClassicFragment : Fragment() {

    companion object {
        fun newInstance() =
            GameFieldClassicFragment()
    }

    private lateinit var viewModel: GameFieldClassicViewModel
    private val TAG = "GameActivity"
    private val INITIAL_SHOW_DELAY : Long = 2000
    private lateinit var vibrationUtils: VibrationUtils
    private lateinit var listener: GameFieldClassicFragmentListener
    private val activeButtons = ArrayList<MaterialButton>()

    interface GameFieldClassicFragmentListener {
        fun onIncorrect()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.game_field_classic_fragment, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(GameFieldClassicViewModel::class.java)
        vibrationUtils = VibrationUtils(activity as AppCompatActivity)

        initActiveButtons()
        highlightActiveButtons(activity!!)
        showValueOfActiveButtons(activity!!)
        hideValueOfActiveButtons(activity!!)
        onActiveButtonClick()
    }

    fun initActiveButtons() {
        val activeTiles = viewModel.getActiveTiles()
        activeTiles.forEach { Log.i(TAG, "Tile [number : ${it.getNumber()}; value : ${it.getValue()}]") }
        activeTiles.forEach {
            activeButtons.add(activity!!.findViewById(resources.getIdentifier("gameTileButton${it.getNumber()}", "id", activity!!.packageName)))
        }
    }

    fun highlightActiveButtons(context: Context) {
        activeButtons.forEach { it.setBackgroundColor(ContextCompat.getColor(context, R.color.accent_color)) }
        activeButtons.forEach { it.isEnabled = false }
    }

    fun showValueOfActiveButtons(context: Context) {
        Timer(false).schedule(object : TimerTask() {
            override fun run() {
                activity?.runOnUiThread {
                    activeButtons.forEach { it.setBackgroundColor(ContextCompat.getColor(context, R.color.dark_button_color)) }
                    activeButtons.forEach { it.text = getButtonValue(it) }
                    activeButtons.forEach { it.isEnabled = false }
                }
            }
        }, INITIAL_SHOW_DELAY)
    }

    fun hideValueOfActiveButtons(context: Context) {
        Timer(false).schedule(object : TimerTask() {
            override fun run() {
                activity?.runOnUiThread {
                    activeButtons.forEach { it.setBackgroundColor(ContextCompat.getColor(context, R.color.accent_color)) }
                    activeButtons.forEach { it.text = "" }
                    activeButtons.forEach { it.isEnabled = true }
                }
            }
        }, INITIAL_SHOW_DELAY + viewModel.getShowTiming())
    }

    fun onActiveButtonClick() {
        activeButtons.forEach { it ->
            it.setOnClickListener {
                val button: MaterialButton = activity?.findViewById(it.id)!!
                val buttonValue = getButtonValue(button)

                when (viewModel.getGameLifecycle(buttonValue)) {

                    GameLifecycle.CORRECT_VALUE -> {
                        vibrationUtils.correctValueVibration()
                        AnimationUtils.viewThreeColorAnimation(activity as AppCompatActivity, button, R.color.accent_color, R.color.green, R.color.dark_button_color, 2 * AnimationUtils.DURATION)
                        AnimationUtils.scaleAnimation(button, 1.07f, AnimationUtils.DURATION)
                        button.text = buttonValue
                        button.isClickable = false
                    }

                    GameLifecycle.INCORRECT_VALUE -> {
                        vibrationUtils.inCorrectValueVibration()
                        AnimationUtils.viewTwoColorAnimation(activity as AppCompatActivity, button, R.color.accent_color, R.color.red, AnimationUtils.INCORRECT_DURATION, 1)
//                        AnimationUtils.layoutColorAnimation(activity as AppCompatActivity, life_card.background as GradientDrawable, R.color.accent_color, R.color.red, AnimationUtils.INCORRECT_DURATION)
//                        AnimationUtils.scaleAnimation(heart_icon, 1.5f, AnimationUtils.INCORRECT_DURATION)

                        listener.onIncorrect()
                        AnimationUtils.scaleAnimation(button, 1.07f, AnimationUtils.INCORRECT_DURATION)
                    }

                    GameLifecycle.NEXT_LEVEL -> {
                        vibrationUtils.correctValueVibration()
                        AnimationUtils.viewThreeColorAnimation(activity as AppCompatActivity, button, R.color.accent_color, R.color.green, R.color.dark_button_color, 2 * AnimationUtils.DURATION)
                        AnimationUtils.scaleAnimation(button, 1.07f, AnimationUtils.DURATION)
                        button.text = buttonValue
                        button.isClickable = false

                        val intent = Intent(activity, NextLevelActivity::class.java)
                        Timer(false).schedule(object : TimerTask() {
                            override fun run() {
                                activity?.runOnUiThread {
                                    startActivity(intent)
                                    activity?.overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left)
                                }
                            }
                        }, AnimationUtils.DURATION)
                    }

                    GameLifecycle.WIN -> {
                        vibrationUtils.correctValueVibration()
                        AnimationUtils.viewThreeColorAnimation(activity as AppCompatActivity, button, R.color.accent_color, R.color.green, R.color.dark_button_color, 2 * AnimationUtils.DURATION)
                        AnimationUtils.scaleAnimation(button, 1.07f, AnimationUtils.DURATION)
                        button.text = buttonValue
                        button.isClickable = false
                        val intent = Intent(activity, WinActivity::class.java)
                        Timer(false).schedule(object : TimerTask() {
                            override fun run() {
                                activity?.runOnUiThread { startActivity(intent) }
                            }
                        }, AnimationUtils.DURATION)
                    }

                    GameLifecycle.GAME_OVER -> {
                        vibrationUtils.inCorrectValueVibration()
                        AnimationUtils.viewTwoColorAnimation(activity as AppCompatActivity, button, R.color.accent_color, R.color.red, 2 * AnimationUtils.DURATION, 1)
                        AnimationUtils.layoutColorAnimation(activity as AppCompatActivity, life_card.background as GradientDrawable, R.color.accent_color, R.color.red, 2 * AnimationUtils.DURATION)
                        AnimationUtils.scaleAnimation(heart_icon, 1.5f, AnimationUtils.INCORRECT_DURATION)
                        AnimationUtils.scaleAnimation(button, 1.07f, AnimationUtils.INCORRECT_DURATION)

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
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = if (context is GameFieldClassicFragmentListener) {
            context
        } else {
            throw RuntimeException(context.toString() + " must implement GameFieldClassicFragmentListener")
        }
    }

    private fun getButtonValue(materialButton: MaterialButton): String {
        val buttonNumber = materialButton.resources.getResourceName(materialButton.id)
            .replace("${activity?.packageName}:id/gameTileButton", "").toInt()
        val tile = viewModel.getActiveTiles().find { it.getNumber() == buttonNumber }
        return tile?.getValue().toString()
    }


}
