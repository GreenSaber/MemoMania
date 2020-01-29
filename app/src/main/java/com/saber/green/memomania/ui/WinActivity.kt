package com.saber.green.memomania.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.saber.green.memomania.R
import com.saber.green.memomania.ui.game.GameActivity
import com.saber.green.memomania.utils.SoundPool
import com.saber.green.memomania.viewmodel.WinViewModel
import kotlinx.android.synthetic.main.activity_win.*
import java.util.*

class WinActivity : AppCompatActivity() {

    private lateinit var viewModel: WinViewModel
    private var winSound : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_win)
        viewModel =  ViewModelProvider(this).get(WinViewModel::class.java)
        initSounds()
        playSound()
        onHomeButtonPressed()
        onRestartButtonPressed()
    }

    fun initSounds(){
        winSound = SoundPool.getInstance()!!.load(applicationContext, R.raw.win_sound, 1)
    }

    fun playSound(){
        if (viewModel.getSoundStatus().value!!) {
            Timer(false).schedule(object : TimerTask() {
                override fun run() {
                    runOnUiThread { SoundPool.getInstance()!!.play(winSound!!, 1F, 1F, 0, 0, 1F) }
                }
            }, 500)
        }
    }

    fun onHomeButtonPressed() {
        win_home_button.setOnClickListener {
            viewModel.resetGame()
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left)
        }
    }

    fun onRestartButtonPressed() {
        win_restart_button.setOnClickListener {
            viewModel.resetGame()
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        viewModel.resetGame()
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left)
    }
}
