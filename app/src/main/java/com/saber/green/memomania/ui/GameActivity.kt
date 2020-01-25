package com.saber.green.memomania.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.saber.green.memomania.R
import com.saber.green.memomania.ui.gamefields.HardGameFieldFragment


class GameActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        supportFragmentManager.beginTransaction().replace(R.id.grid_layout_container, HardGameFieldFragment()).commit()
        supportFragmentManager.beginTransaction().replace(R.id.info_panel_container, InfoPanelFragment()).commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right)
    }
}
