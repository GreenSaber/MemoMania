package com.saber.green.memomania.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdCallback
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.saber.green.memomania.R
import com.saber.green.memomania.ui.game.GameActivity
import com.saber.green.memomania.utils.AnimationUtils
import com.saber.green.memomania.utils.MotivationTextUtils
import com.saber.green.memomania.viewmodel.NextLevelViewModel
import kotlinx.android.synthetic.main.activity_next_level.*
import java.util.*

class NextLevelActivity : BaseActivity() {

    private val TAG = "NextLevelActivity"
    private val FAILED_TO_LOAD_AD = "Failed to load video ad"
    private lateinit var viewModel: NextLevelViewModel
    private lateinit var rewardedAd: RewardedAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next_level)
        viewModel =  ViewModelProvider(this).get(NextLevelViewModel::class.java)
        setGetLifeButtonStatus(false)
        initMotivationText()
        initLevelCountObservable1()
        initLifeObserver()
        onReadyButtonClick()
        onGetLifeButtonClick()
        playSound()
        MobileAds.initialize(this) {}

        rewardedAd = RewardedAd(this, "ca-app-pub-3940256099942544/5224354917")
        val adLoadCallback = object: RewardedAdLoadCallback() {
            override fun onRewardedAdLoaded() {
                setGetLifeButtonStatus(true)
            }
        }
        rewardedAd.loadAd(AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build(), adLoadCallback)
    }

    fun initMotivationText(){
        next_level_motivation_text.text = MotivationTextUtils.getRandomText()
    }

    fun initLevelCountObservable1() {
        viewModel.getLevelCount().observe(this, Observer {
            val resultText = "${resources.getString(R.string.next_level_is)} $it"
            next_level_number.text = resultText
        })
    }

    fun initLifeObserver() {
        viewModel.getLifeCount().observe(this, Observer {
            Timer(false).schedule(object : TimerTask() {
                override fun run() {
                    runOnUiThread { life_number_next_menu.text = it }
                }
            }, AnimationUtils.DURATION_DEFAULT)
        })
    }

    fun onReadyButtonClick() {
        ready_button.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right)
        }
    }

    fun onGetLifeButtonClick() {
        get_life_button.setOnClickListener {
            if (rewardedAd.isLoaded) {
                val activityContext: Activity = this
                var rewardEarned = false
                val adCallback = object : RewardedAdCallback() {

                    override fun onRewardedAdClosed() {
                        Log.i(TAG, "Ad closed")
                        if (rewardEarned) {
                            Timer(false).schedule(object : TimerTask() {
                                override fun run() {
                                    runOnUiThread {
                                        viewModel.addLife()
                                        AnimationUtils.lifeIncreaseNextLevelAnimation(this@NextLevelActivity, life_card_next_menu, heart_icon_next_menu)
                                        setGetLifeButtonStatus(false)
                                    }
                                }
                            },  1000)
                        }
                    }

                    override fun onUserEarnedReward(@NonNull reward: RewardItem) {
                        Log.i(TAG, "User earned reward")
                        rewardEarned = true
                    }

                    override fun onRewardedAdFailedToShow(errorCode: Int) {
                        Log.i(TAG, "Ad failed to display")
                        Toast.makeText(applicationContext, FAILED_TO_LOAD_AD, Toast.LENGTH_SHORT).show()
                    }
                }
                rewardedAd.show(activityContext, adCallback)
            } else {
                Toast.makeText(applicationContext, FAILED_TO_LOAD_AD, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setGetLifeButtonStatus(status : Boolean){
        if (status){
            get_life_button.isClickable = true
            get_life_button.setBackgroundColor(ContextCompat.getColor(this, R.color.accent_color))
        } else {
            get_life_button.isClickable = false
            get_life_button.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_button_color))
        }
    }

    fun playSound() {
        if (viewModel.getSoundStatus().value!!) {
            Timer(false).schedule(object : TimerTask() {
                override fun run() {
                    runOnUiThread { startPlayer(application.applicationContext, R.raw.next_level) }
                }
            }, 350)
        }
    }

    override fun onPause() {
        super.onPause()
        stopPlayer()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right)
    }

}
