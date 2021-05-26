package com.superplayer.pomodoro.design.bindingAdapters

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieAnimationView
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import java.lang.Exception

@BindingAdapter("app:progressBinding")
fun setProgress(progressBar: CircularProgressBar, progress: Int) {
    try {
        progressBar.setProgressWithAnimation(progress.toFloat(),1500)
    }catch (ex : Exception){
        ex.printStackTrace()
    }
}

@BindingAdapter(value = ["app:textBinding"], requireAll = false)
fun setTextButton(button: AppCompatButton, resourceId: Int?) {
    resourceId?.let {
        try{
            button.text = button.context.getText(it)
        }catch (ex : Exception){
            ex.printStackTrace()
        }
    }
}

@BindingAdapter(value = ["app:textColorBinding"], requireAll = false)
fun setTextColor(txtView: TextView, colorRes: Int?) {
    colorRes?.let {
        try {
            txtView.setTextColor(txtView.context.resources.getColor(it))
        }catch (ex : Exception){
            ex.printStackTrace()
        }
    }
}

@BindingAdapter(value = ["app:playBinding"], requireAll = false)
fun setPlayAnimation(lottie : LottieAnimationView, play: Boolean?) {
    if(play == true)lottie.playAnimation() else lottie.cancelAnimation()
}

@BindingAdapter(value = ["app:visibilityBinding"], requireAll = false)
fun setVisibility(view : View, visibility: Int?) {
    try {
        visibility?.let {
            view.visibility = it
        }
    }catch (ex : Exception){
        ex.printStackTrace()
    }
}

