package com.saber.green.memomania.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.saber.green.memomania.R
import com.saber.green.memomania.model.GameDifficulty
import com.saber.green.memomania.ui.game.GameActivity
import com.saber.green.memomania.viewmodel.MenuViewModel
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() {

    private lateinit var viewModel: MenuViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        viewModel = ViewModelProvider(this).get(MenuViewModel::class.java)
        onPlayButtonClicked()
        onRateMeButtonClick()
        onRightArrowButtonClick()
        onLeftArrowButtonClick()
        onSoundButtonClick()
        initDifficultyObserver()
        initSoundObserver()
    }

    fun onPlayButtonClicked() {
       play_button.setOnClickListener {
           viewModel.refreshData()
           val intent = Intent(this, GameActivity::class.java)
           startActivity(intent)
           overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left)
       }
    }

    fun onRateMeButtonClick() {
        rate_button.setOnClickListener {
            try {
                val uri = Uri.parse("market://details?id=$packageName")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                startActivity(
                    Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=$packageName"))
                )
            }
        }
    }

    fun initDifficultyObserver() {
        viewModel.getDifficulty().observe(this, Observer {
            when (viewModel.getDifficulty().value!!) {
                GameDifficulty.EASY -> {
                    difficulty_title.text = resources.getString(R.string.easy)
                    game_title.text = resources.getString(R.string.app_name_easy)
                }
                GameDifficulty.CLASSIC -> {
                    difficulty_title.text = resources.getString(R.string.classic)
                    game_title.text = resources.getString(R.string.app_name)
                }
                GameDifficulty.HARD -> {
                    difficulty_title.text = resources.getString(R.string.hard)
                    game_title.text = resources.getString(R.string.app_name_hard)
                }
            }
        })
    }

    fun initSoundObserver(){
        viewModel.getSoundStatus().observe(this, Observer {
            if (viewModel.getSoundStatus().value == true){
                sound_button_main.text = resources.getString(R.string.sound_on)
                sound_button_main.icon = resources.getDrawable(R.drawable.ic_sound_on, theme)
            } else {
                sound_button_main.text = resources.getString(R.string.sound_off)
                sound_button_main.icon = resources.getDrawable(R.drawable.ic_sound_off, theme)
            }
        })
    }

    fun onSoundButtonClick(){
        sound_button_main.setOnClickListener{
            viewModel.onSoundButtonClick()
        }
    }

    fun onRightArrowButtonClick() {
        arrow_right.setOnClickListener {
            YoYo.with(Techniques.Landing).duration(300).playOn(game_title)
            viewModel.onRightArrowButtonClick()
        }
    }

    fun onLeftArrowButtonClick() {
        arrow_left.setOnClickListener {
            YoYo.with(Techniques.Landing).duration(300).playOn(game_title)
            viewModel.onLeftArrowButtonClick()
        }
    }

    override fun onBackPressed() {
        finishAffinity()
    }
}
