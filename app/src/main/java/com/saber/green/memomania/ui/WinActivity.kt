package com.saber.green.memomania.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.saber.green.memomania.R
import com.saber.green.memomania.viewmodel.WinViewModel
import kotlinx.android.synthetic.main.activity_win.*

class WinActivity : AppCompatActivity() {

    private lateinit var winViewModel: WinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_win)
        winViewModel =  ViewModelProvider(this).get(WinViewModel::class.java)

        onHomeButtonPressed()
        onRestartButtonPressed()
    }

    fun onHomeButtonPressed() {
        win_home_button.setOnClickListener {
            winViewModel.resetGame()
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left)
        }
    }

    fun onRestartButtonPressed() {
        win_restart_button.setOnClickListener {
            winViewModel.resetGame()
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        winViewModel.resetGame()
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left)
    }
}
