package com.saber.green.memomania.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.saber.green.memomania.R
import com.saber.green.memomania.viewmodel.MenuViewModel
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() {
    private lateinit var menuViewModel: MenuViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        menuViewModel = ViewModelProviders.of(this).get(MenuViewModel::class.java)
        onPlayButtonClicked()
    }

    fun onPlayButtonClicked() {
       play_button.setOnClickListener {
           menuViewModel.refreshData()
           val intent = Intent(this, GameActivity::class.java)
           startActivity(intent)
           overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left)
       }
    }

    override fun onBackPressed() {
        finishAffinity()
    }
}
