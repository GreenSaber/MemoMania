package com.saber.green.memomania.ui


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.saber.green.memomania.R
import com.saber.green.memomania.utils.AnimationUtils
import com.saber.green.memomania.viewmodel.GameViewModel
import kotlinx.android.synthetic.main.fragment_game_statistics.*
import java.util.*

class GameStatisticsFragment : Fragment() {

    private lateinit var viewModel: GameViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view : View =  inflater.inflate(R.layout.fragment_game_statistics, container, false)
        viewModel = ViewModelProviders.of(activity!!).get(GameViewModel::class.java)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLevelObserver()
        initLifeObserver()
        onHomeButtonClick()
        onRestartButtonClick()
    }


    fun initLevelObserver() {
        viewModel.getLevelCount().observe(activity!!, Observer {
            level_number.text = viewModel.getLevelCount().value
        })
    }

    fun initLifeObserver() {
        viewModel.getLifeCount().observe(this, Observer {
            Timer(false).schedule(object : TimerTask() {
                override fun run() {
                    activity?.runOnUiThread {
                        life_number.text = viewModel.getLifeCount().value
                    }
                }
            }, AnimationUtils.DURATION)
        })
    }


    fun onHomeButtonClick() {
        home_button.setOnClickListener {
            val intent = Intent(activity, MenuActivity::class.java)
            startActivity(intent)
            activity?.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right)
        }
    }

    fun onRestartButtonClick() {
        restart_button.setOnClickListener {
            viewModel.resetGame()
            val intent = Intent(activity, GameActivity::class.java)
            startActivity(intent)
            activity?.overridePendingTransition(R.anim.anim_slide_in_top, R.anim.anim_slide_out_top)
        }
    }

}
