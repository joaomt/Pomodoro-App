package com.superplayer.pomodoro.common.adapter

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView

class DataBindingViewHolder<T>(private val binding : ViewDataBinding):
    RecyclerView.ViewHolder(binding.root){

    fun bind(onClickListener: View.OnClickListener, item : T){
        binding.setVariable(BR.item, item)
        binding.setVariable(BR.clickListener, onClickListener)
        binding.executePendingBindings()
    }
}