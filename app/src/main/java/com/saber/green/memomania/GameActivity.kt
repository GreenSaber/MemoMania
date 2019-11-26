package com.saber.green.memomania

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.vectordrawable.graphics.drawable.ArgbEvaluator
import kotlinx.android.synthetic.main.activity_game.*


class GameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        onLifeButtonClicked()
        onHeartIconClicked()
        onButtonClicked()
    }

    fun onLifeButtonClicked() {
        life_card.setOnClickListener {
            val intent = Intent(this, LevelActivity::class.java)
            startActivity(intent)
        }
    }

    fun onHeartIconClicked() {
        heart_icon.setOnClickListener {
            colorAnimation()
            scaleAnimation()
        }
    }

    fun onButtonClicked() {
        materialButton4.setOnClickListener {
            scaleButtonAnimation(it, 1.07f, 200)
        }
    }

    private fun scaleAnimation() {
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1.5f)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.5f)
        val animator = ObjectAnimator.ofPropertyValuesHolder(heart_icon, scaleX, scaleY)
        animator.duration = 500
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.start()
    }


    private fun scaleButtonAnimation(view: View, scale : Float, animationDuration : Long) {
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, scale)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, scale)
        val animator = ObjectAnimator.ofPropertyValuesHolder(view, scaleX, scaleY)
        animator.duration = animationDuration
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.start()
    }

    private fun colorAnimation() {
        val colorFrom = ResourcesCompat.getColor(resources, R.color.accent_color, null)
        val colorTo = ResourcesCompat.getColor(resources, R.color.red, null)
        val animator = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo)
        val background = life_card.background as GradientDrawable
        animator.addUpdateListener(ValueAnimator.AnimatorUpdateListener { animator ->
            background.setColor((animator.animatedValue as Int))
        })
        animator.duration = 500
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.start()
    }
}
