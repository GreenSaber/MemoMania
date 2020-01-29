package com.saber.green.memomania.ui

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.saber.green.memomania.R
import com.saber.green.memomania.ui.game.GameActivity
import com.saber.green.memomania.viewmodel.WinViewModel
import kotlinx.android.synthetic.main.activity_win.*


class WinActivity : AppCompatActivity() {

    private lateinit var viewModel: WinViewModel
    private var player : MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_win)
        viewModel = ViewModelProvider(this).get(WinViewModel::class.java)
        playSound()
        onHomeButtonPressed()
        onRestartButtonPressed()
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

    fun playSound() {
        if (viewModel.getSoundStatus().value!!) {
            if (player == null) {
                player = MediaPlayer.create(this, R.raw.win_sound)
                player?.setOnCompletionListener {
                    stopPlayer()
                }
            }
            player?.start()
        }
    }

    private fun stopPlayer() {
        if (player != null) {
            player!!.release()
            player = null
        }
    }

    override fun onPause() {
        super.onPause()
        stopPlayer()
    }
}
