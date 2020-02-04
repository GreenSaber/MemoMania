package com.saber.green.memomania.utils

import android.animation.*
import android.app.Activity
import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.vectordrawable.graphics.drawable.ArgbEvaluator
import com.saber.green.memomania.R


class AnimationUtils {

    companion object{

        const val DURATION_DEFAULT : Long = 250
        const val DURATION_DOUBLE : Long = 500

        private const val TILE_SCALE_SIZE = 1.1f
        private const val HEART_SCALE_SIZE = 1.5f

        fun incorrectValueTileAnimation(activity: Activity, view: View){
            viewTwoColorAnimation(activity as AppCompatActivity, view, R.color.accent_color, R.color.red, DURATION_DOUBLE, 1)
            scaleAnimation(view, TILE_SCALE_SIZE, DURATION_DOUBLE)
        }

        fun correctValueTileAnimation(activity: Activity, view: View){
            viewThreeColorAnimation(activity as AppCompatActivity, view, R.color.accent_color, R.color.green, R.color.dark_button_color, 2 * DURATION_DEFAULT)
            scaleAnimation(view, TILE_SCALE_SIZE, DURATION_DEFAULT)
        }

        fun incorrectValueInfoPanelAnimation(activity: Activity, viewToColorAnimation: View, viewToScale: View){
            layoutColorAnimation(activity as AppCompatActivity, viewToColorAnimation.background as GradientDrawable, R.color.accent_color, R.color.red, DURATION_DOUBLE)
            scaleAnimation(viewToScale, HEART_SCALE_SIZE, DURATION_DOUBLE)
        }

        fun lifeIncreaseNextLevelAnimation(activity: Activity, viewToColorAnimation: View, viewToScale: View){
            layoutColorAnimation(activity as AppCompatActivity, viewToColorAnimation.background as GradientDrawable, R.color.accent_color, R.color.green, DURATION_DOUBLE)
            scaleAnimation(viewToScale, HEART_SCALE_SIZE, DURATION_DOUBLE)
        }

        private fun scaleAnimation(view: View, scaleSize: Float, animationDuration: Long) {
            val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, scaleSize)
            val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, scaleSize)
            val animator = ObjectAnimator.ofPropertyValuesHolder(view, scaleX, scaleY)
            disableViewDuringAnimation(view, animator)
            animator.duration = animationDuration
            animator.repeatCount = 1
            animator.repeatMode = ObjectAnimator.REVERSE
            animator.start()
        }

        private fun layoutColorAnimation(appCompatActivity: AppCompatActivity, background : GradientDrawable, colorFrom: Int, colorTo: Int, animationDuration : Long) {
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

        private fun viewTwoColorAnimation(appCompatActivity: AppCompatActivity, view: View, colorFrom: Int, colorTo: Int, animationDuration : Long, repeatCount : Int = 0) {
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

        private fun viewThreeColorAnimation(appCompatActivity: AppCompatActivity, view: View, colorFrom: Int, colorTo: Int, color3: Int, animationDuration : Long, repeatCount : Int = 0) {
            val colorFromValue: Int = ResourcesCompat.getColor(appCompatActivity.resources, colorFrom, null)
            val colorToValue: Int = ResourcesCompat.getColor(appCompatActivity.resources, colorTo, null)
            val colorValue3: Int = ResourcesCompat.getColor(appCompatActivity.resources, color3, null)
            val animator : ValueAnimator = ValueAnimator.ofObject(ArgbEvaluator(), colorFromValue, colorToValue, colorValue3)
            animator.addUpdateListener { animator ->
                view.setBackgroundColor((animator.animatedValue as Int))
            }
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