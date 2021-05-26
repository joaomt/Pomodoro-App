package com.superplayer.pomodoro.common.adapter

import android.annotation.SuppressLint
import android.view.View
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import com.superplayer.pomodoro.R
import kotlin.Throws

class AdapterGeneric<T>(
    onItemListClicked : ((view : View?, param : T) -> Unit)?=null) : DataBindingAdapter<T>(
    DiffCallback(),onItemListClicked){

    class DiffCallback<T> : DiffUtil.ItemCallback<T>(){
        override fun areItemsTheSame(
            oldItem: T,
            newItem: T
        ): Boolean = oldItem == newItem

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: T,
            newItem: T
        ): Boolean = oldItem == newItem
    }

    override fun getItemViewType(position: Int): Int {
        if(getItem(position) is ViewHolderFactory){
            val viewHolderFactory : ViewHolderFactory = getItem(position) as ViewHolderFactory

            return viewHolderFactory.getLayoutItemType()
        }else {
            throw IllegalStateException("data source must be ViewHolderFactory")
        }
    }
}