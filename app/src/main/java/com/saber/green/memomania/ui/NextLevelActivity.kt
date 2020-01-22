package com.saber.green.memomania.ui

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.saber.green.memomania.R
import com.saber.green.memomania.utils.AnimationUtils
import com.saber.green.memomania.utils.MotivationTextUtils
import com.saber.green.memomania.viewmodel.NextLevelViewModel
import kotlinx.android.synthetic.main.activity_next_level.*
import java.util.*

class NextLevelActivity : AppCompatActivity() {

    private val TAG = "NextLevelActivity"
    private lateinit var nextLevelViewModel: NextLevelViewModel
    private lateinit var rewardedAd: RewardedAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next_level)
        nextLevelViewModel = ViewModelProviders.of(this).get(NextLevelViewModel::class.java)
        initMotivationText()
        initLevelCountObservable1()
        initLifeObserver()
        onReadyButtonClick()
        onGetLifeButtonClick()

        MobileAds.initialize(this) {}

        rewardedAd = RewardedAd(this, "ca-app-pub-3940256099942544/5224354917")
        val adLoadCallback = object: RewardedAdLoadCallback() {
            override fun onRewardedAdLoaded() {
                Log.i(TAG, "rewarded Ad loaded")
            }
            override fun onRewardedAdFailedToLoad(errorCode: Int) {
                Log.i(TAG, "rewarded Ad failed to load")
            }
        }
        rewardedAd.loadAd(AdRequest.Builder().build(), adLoadCallback)
    }

    fun initMotivationText(){
        next_level_motivation_text.text = MotivationTextUtils.getRandomText()
    }

    fun initLevelCountObservable1() {
        nextLevelViewModel.getLevelCount().observe(this, Observer {
            val value = nextLevelViewModel.getLevelCount().value.toString()
            val resultText = "${resources.getString(R.string.next_level_is)} $value"
            next_level_number.text = resultText
        })
    }

    fun initLifeObserver() {
        nextLevelViewModel.getLifeCount().observe(this, Observer {
            Timer(false).schedule(object : TimerTask() {
                override fun run() {
                    runOnUiThread {
                        life_number_next_menu.text = nextLevelViewModel.getLifeCount().value                    }
                }
            }, AnimationUtils.DURATION)
        })
    }

    fun onReadyButtonClick() {
        ready_button.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right)
        }
    }

    fun onGetLifeButtonClick(){
        get_life_button.setOnClickListener {
            AnimationUtils.layoutColorAnimation(this, life_card_next_menu.background as GradientDrawable, R.color.accent_color, R.color.green, AnimationUtils.DURATION)
            AnimationUtils.scaleAnimation(heart_icon_next_menu, 1.5f, AnimationUtils.DURATION)
            nextLevelViewModel.addLife()
            it.isClickable = false
            it.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_button_color))
        }

//        get_life_button.setOnClickListener {
//            if (rewardedAd.isLoaded) {
//                val activityContext: Activity = this
//                val adCallback = object: RewardedAdCallback() {
//                    override fun onRewardedAdOpened() {
//                        Log.i(TAG, "Ad opened")
//                    }
//                    override fun onRewardedAdClosed() {
//                        Log.i(TAG, "Ad closed")
//                    }
//                    override fun onUserEarnedReward(@NonNull reward: RewardItem) {
//                        Log.i(TAG, "User earned reward")
//                    }
//                    override fun onRewardedAdFailedToShow(errorCode: Int) {
//                        Log.i(TAG, "Ad failed to display")
//                    }
//                }
//                rewardedAd.show(activityContext, adCallback)
//            }
//            else {
//                Log.d(TAG, "The rewarded ad wasn't loaded yet")
//            }
//        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right)
    }

}
