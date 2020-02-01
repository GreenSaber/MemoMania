package com.saber.green.memomania.ui.game


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.saber.green.memomania.R
import com.saber.green.memomania.ui.MenuActivity
import com.saber.green.memomania.utils.AnimationUtils
import com.saber.green.memomania.viewmodel.GameViewModel
import kotlinx.android.synthetic.main.fragment_game_info_panel.*
import java.util.*

class InfoPanelFragment : Fragment() {

    private lateinit var viewModel: GameViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view : View =  inflater.inflate(R.layout.fragment_game_info_panel, container, false)
        viewModel =  ViewModelProvider(activity!!).get(GameViewModel::class.java)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLevel()
        initLifeObserver()
        initWrongAnswersObserver()
        initSoundObserver()
        initVibrationObserver()
        onHomeButtonClick()
        onSoundButtonClick()
        onVibrationButtonClick()
        onRestartButtonClick()
    }

    fun initLevel() {
        level_number.text=viewModel.getCurrentLevel()
    }

    fun initLifeObserver() {
        viewModel.getLifes().observe(viewLifecycleOwner, Observer {
            Timer(false).schedule(object : TimerTask() {
                override fun run() {
                    activity?.runOnUiThread {
                        life_number.text = it
                    }
                }
            }, AnimationUtils.DURATION)
        })
    }

    fun initWrongAnswersObserver() {
        viewModel.getWrongAnswers().observe(viewLifecycleOwner, Observer {
            AnimationUtils.incorrectValueInfoPanelAnimation(activity!!, life_card, heart_icon)
        })
    }

    fun initSoundObserver(){
        viewModel.getSoundStatus().observe(viewLifecycleOwner, Observer {
            sound_button_game.icon = if (it)
                resources.getDrawable(R.drawable.ic_sound_on, activity!!.theme)
            else resources.getDrawable(R.drawable.ic_sound_off, activity!!.theme)
        })
    }

    fun initVibrationObserver(){
        viewModel.getVibrationStatus().observe(viewLifecycleOwner, Observer {
            vibration_button_game.icon = if (it)
                resources.getDrawable(R.drawable.ic_vibrate, activity!!.theme)
            else resources.getDrawable(R.drawable.ic_vibrate_off, activity!!.theme)
        })
    }

    fun onSoundButtonClick() {
        sound_button_game.setOnClickListener {
            viewModel.onSoundButtonClick()
        }
    }

    fun onVibrationButtonClick() {
        vibration_button_game.setOnClickListener {
            viewModel.onVibrationButtonClick()
        }
    }

    fun onHomeButtonClick() {
        home_button_game.setOnClickListener {
            val intent = Intent(activity, MenuActivity::class.java)
            startActivity(intent)
            activity?.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right)
        }
    }

    fun onRestartButtonClick() {
        restart_button_game.setOnClickListener {
            viewModel.resetGame()
            val intent = Intent(activity, GameActivity::class.java)
            startActivity(intent)
            activity?.overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left)
        }
    }

}
