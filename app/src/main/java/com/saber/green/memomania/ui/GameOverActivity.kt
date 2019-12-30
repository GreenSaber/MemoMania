package com.saber.green.memomania.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.saber.green.memomania.R
import com.saber.green.memomania.viewmodel.GameOverViewModel
import kotlinx.android.synthetic.main.activity_game_over.*

class GameOverActivity : AppCompatActivity() {

    private lateinit var gameOverViewModel: GameOverViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)
        gameOverViewModel = ViewModelProviders.of(this).get(GameOverViewModel::class.java)
        initLevelObserver()
        onHomeButtonPressed()
    }

    fun initLevelObserver() {
        gameOverViewModel.getLevelCount().observe(this, Observer {
            finished_level_number.text = gameOverViewModel.getLevelCount().value
        })
    }

    fun onHomeButtonPressed() {
        game_over_home_button.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right)
    }

}
