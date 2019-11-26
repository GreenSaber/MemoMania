package com.saber.green.memomania.utils

import android.animation.*
import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.vectordrawable.graphics.drawable.ArgbEvaluator

class AnimationUtils {

    companion object{

        fun scaleAnimation(view: View, scaleSize: Float, animationDuration: Long) {
            val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, scaleSize)
            val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, scaleSize)
            val animator = ObjectAnimator.ofPropertyValuesHolder(view, scaleX, scaleY)
            disableViewDuringAnimation(view, animator)
            animator.duration = animationDuration
            animator.repeatCount = 1
            animator.repeatMode = ObjectAnimator.REVERSE
            animator.start()
        }

        fun colorAnimation(appCompatActivity: AppCompatActivity, background : GradientDrawable, colorFrom: Int, colorTo: Int, animationDuration : Long) {
            val colorFromValue = ResourcesCompat.getColor(appCompatActivity.resources, colorFrom, null)
            val colorToValue = ResourcesCompat.getColor(appCompatActivity.resources, colorTo, null)
            val animator = ValueAnimator.ofObject(ArgbEvaluator(), colorFromValue, colorToValue)
            val backgroundDrawable = background
            animator.addUpdateListener(ValueAnimator.AnimatorUpdateListener { animator ->
                backgroundDrawable.setColor((animator.animatedValue as Int))
            })
            animator.duration = animationDuration
            animator.repeatCount = 1
            animator.repeatMode = ObjectAnimator.REVERSE
            animator.start()
        }

        private fun disableViewDuringAnimation(view: View, animator: ObjectAnimator) {
            animator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    view.isEnabled = false
                }

                override fun onAnimationEnd(animation: Animator?) {
                    view.isEnabled = true
                }
            })
        }
    }
}