package com.saber.green.memomania.ui

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.saber.green.memomania.R
import com.saber.green.memomania.databinding.ActivityNextLevelBindingImpl
import com.saber.green.memomania.utils.AnimationUtils
import com.saber.green.memomania.viewmodel.NextLevelViewModel
import kotlinx.android.synthetic.main.activity_next_level.*

class NextLevelActivity : AppCompatActivity() {

    private lateinit var nextLevelViewModel: NextLevelViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next_level)
        nextLevelViewModel = ViewModelProviders.of(this).get(NextLevelViewModel::class.java)
        val binding: ActivityNextLevelBindingImpl = DataBindingUtil.setContentView(this, R.layout.activity_next_level)
        binding.lifecycleOwner = this
        binding.viewmodel = nextLevelViewModel
        initLevelCountObservable()
        initLifeObserver()
        onReadyButtonClick()
    }

    fun initLevelCountObservable() {
        nextLevelViewModel.getLevelCount().observe(this, Observer {
            next_level_number.text = nextLevelViewModel.getLevelCount().value
        })
    }

    fun initLifeObserver() {
        nextLevelViewModel.getLifeCount().observe(this, Observer {
            AnimationUtils.layoutColorAnimation(this, life_card_next_menu.background as GradientDrawable, R.color.accent_color, R.color.red, AnimationUtils.DURATION)
            AnimationUtils.scaleAnimation(heart_icon_next_menu, 1.5f, AnimationUtils.DURATION)
//            Timer(false).schedule(object : TimerTask() {
//                override fun run() {
//                    runOnUiThread { life_number.text = it }
//                }
//            }, AnimationUtils.DURATION)
        })
    }

    fun onReadyButtonClick() {
        ready_button.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right)
        }
    }


}
