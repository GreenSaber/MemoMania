package com.saber.green.memomania.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.saber.green.memomania.R
import com.saber.green.memomania.viewmodel.NextLevelViewModel
import kotlinx.android.synthetic.main.activity_next_level.*

class NextLevelActivity : AppCompatActivity() {

    private lateinit var nextLevelViewModel: NextLevelViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next_level)
        nextLevelViewModel = ViewModelProviders.of(this).get(NextLevelViewModel::class.java)
        setGameLevel()
        onReadyButtonClick()
    }

    fun setGameLevel() {
        val currentGameLevel = nextLevelViewModel.getGameLevel().toString()
        next_level_number.text = currentGameLevel
    }

    fun onReadyButtonClick() {
        ready_button.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }
    }


}
