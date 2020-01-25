package com.saber.green.memomania.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.saber.green.memomania.R
import com.saber.green.memomania.model.GameDifficulty
import com.saber.green.memomania.ui.gamefields.BaseGameFieldFragment
import com.saber.green.memomania.ui.gamefields.ClassicGameFieldFragment
import com.saber.green.memomania.ui.gamefields.EasyGameFieldFragment
import com.saber.green.memomania.ui.gamefields.HardGameFieldFragment
import com.saber.green.memomania.viewmodel.GameViewModel


class GameActivity : AppCompatActivity(){

    private lateinit var viewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        viewModel =  ViewModelProvider(this).get(GameViewModel::class.java)
        supportFragmentManager.beginTransaction().replace(R.id.grid_layout_container, getGameFieldFragment()).commit()
        supportFragmentManager.beginTransaction().replace(R.id.info_panel_container, InfoPanelFragment()).commit()
    }

    private fun getGameFieldFragment() : BaseGameFieldFragment{
        return when (viewModel.getDifficulty()){
            GameDifficulty.EASY -> {
                EasyGameFieldFragment()
            }
            GameDifficulty.CLASSIC -> {
                ClassicGameFieldFragment()
            }
            GameDifficulty.HARD -> {
                HardGameFieldFragment()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right)
    }
}
