package com.saber.green.memomania.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.saber.green.memomania.R


class GameActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        supportFragmentManager.beginTransaction().replace(R.id.grid_layout_container, GameFieldClassicFragment()).commit()
        supportFragmentManager.beginTransaction().replace(R.id.info_panel_container, GameStatisticsFragment()).commit()
    }


}
