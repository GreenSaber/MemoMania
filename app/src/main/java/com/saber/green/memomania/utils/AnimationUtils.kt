package com.saber.green.memomania.utils

import android.animation.*
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.vectordrawable.graphics.drawable.ArgbEvaluator


class AnimationUtils {

    companion object{

        val DURATION : Long = 500

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

        fun layoutColorAnimation(appCompatActivity: AppCompatActivity, background : GradientDrawable, colorFrom: Int, colorTo: Int, animationDuration : Long) {
            val colorFromValue: Int = ResourcesCompat.getColor(appCompatActivity.resources, colorFrom, null)
            val colorToValue: Int = ResourcesCompat.getColor(appCompatActivity.resources, colorTo, null)
            val animator: ValueAnimator = ValueAnimator.ofObject(ArgbEvaluator(), colorFromValue, colorToValue)
            val backgroundDrawable = background
            animator.addUpdateListener { animator ->
                backgroundDrawable.setColor((animator.animatedValue as Int))
            }
            animator.duration = animationDuration
            animator.repeatCount = 1
            animator.repeatMode = ObjectAnimator.REVERSE
            animator.start()
        }


        fun viewColorAnimation(appCompatActivity: AppCompatActivity, view: View, colorFrom: Int, colorTo: Int, animationDuration : Long, repeatCount : Int = 0) {
            val colorFromValue: Int = ResourcesCompat.getColor(appCompatActivity.resources, colorFrom, null)
            val colorToValue: Int = ResourcesCompat.getColor(appCompatActivity.resources, colorTo, null)
            val animator : ValueAnimator = ValueAnimator.ofObject(ArgbEvaluator(), colorFromValue, colorToValue)
            animator.addUpdateListener { animator ->
                view.setBackgroundColor((animator.animatedValue as Int))
            }
            animator.duration = animationDuration
            animator.repeatCount = repeatCount
            animator.repeatMode = ObjectAnimator.REVERSE
            animator.start()
        }


        fun viewColorAnimation1(appCompatActivity: AppCompatActivity, view: View, colorFrom: Int, colorTo: Int,color3: Int, animationDuration : Long, repeatCount : Int = 0) {
            val colorFromValue: Int = ResourcesCompat.getColor(appCompatActivity.resources, colorFrom, null)
            val colorToValue: Int = ResourcesCompat.getColor(appCompatActivity.resources, colorTo, null)
            val colorValue3: Int = ResourcesCompat.getColor(appCompatActivity.resources, color3, null)
            val animator : ValueAnimator = ValueAnimator.ofObject(ArgbEvaluator(), colorFromValue, colorToValue, colorValue3)
            animator.addUpdateListener(AnimatorUpdateListener { animator ->
                view.setBackgroundColor((animator.animatedValue as Int))
            })
            animator.duration = animationDuration
            animator.repeatCount = repeatCount
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