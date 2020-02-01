package com.saber.green.memomania.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.saber.green.memomania.R
import com.saber.green.memomania.ui.game.GameActivity
import com.saber.green.memomania.viewmodel.GameOverViewModel
import kotlinx.android.synthetic.main.activity_game_over.*

class GameOverActivity : AppCompatActivity() {

    private lateinit var gameOverViewModel: GameOverViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)
        gameOverViewModel =  ViewModelProvider(this).get(GameOverViewModel::class.java)
        initLevelObserver()
        onHomeButtonPressed()
        onRestartButtonPressed()
    }

    fun initLevelObserver() {
        gameOverViewModel.getLevelCount().observe(this, Observer {
            val resultText = "${resources.getString(R.string.you_finished_on_level)} $it"
            game_over_level_text.text = resultText
        })
    }

    fun onHomeButtonPressed() {
        game_over_home_button.setOnClickListener {
            gameOverViewModel.resetGame()
            val intent = Intent(this, MenuActivity::class.  java)
            startActivity(intent)
            overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right)
        }
    }

    fun onRestartButtonPressed() {
        game_over_restart_button.setOnClickListener {
            gameOverViewModel.resetGame()
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        gameOverViewModel.resetGame()
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right)
    }

}
