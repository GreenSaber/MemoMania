package com.saber.green.memomania.ui.game


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.saber.green.memomania.R
import com.saber.green.memomania.ui.MenuActivity
import com.saber.green.memomania.utils.AnimationUtils
import com.saber.green.memomania.viewmodel.GameViewModel
import kotlinx.android.synthetic.main.fragment_game_info_panel.*
import java.util.*

class InfoPanelFragment : Fragment() {

    private lateinit var viewModel: GameViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view : View =  inflater.inflate(R.layout.fragment_game_info_panel, container, false)
        viewModel =  ViewModelProvider(activity!!).get(GameViewModel::class.java)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLevelObserver()
        initLifeObserver()
        initWrongAnswersObserver()
        onHomeButtonClick()
        onRestartButtonClick()
    }

    fun initLevelObserver() {
        viewModel.getLevels().observe(activity!!, Observer {
            level_number.text = viewModel.getLevels().value
        })
    }

    fun initLifeObserver() {
        viewModel.getLifes().observe(viewLifecycleOwner, Observer {
            Timer(false).schedule(object : TimerTask() {
                override fun run() {
                    activity?.runOnUiThread {
                        life_number.text = it
                    }
                }
            }, AnimationUtils.DURATION)
        })
    }

    fun initWrongAnswersObserver() {
        viewModel.getWrongAnswers().observe(this, Observer {
            AnimationUtils.incorrectValueInfoPanelAnimation(activity!!, life_card, heart_icon)
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
