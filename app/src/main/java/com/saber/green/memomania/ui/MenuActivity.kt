package com.saber.green.memomania.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.saber.green.memomania.R
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        onPlayButtonClicked()

    }

    fun onPlayButtonClicked() {
       play_button.setOnClickListener {
           val intent = Intent(this, GameActivity::class.java)
           startActivity(intent)
       }
    }
}
