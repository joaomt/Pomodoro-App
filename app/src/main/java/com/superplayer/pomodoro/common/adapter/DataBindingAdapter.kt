package com.superplayer.pomodoro.common.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

abstract class DataBindingAdapter<T>(
    diffCallback: DiffUtil.ItemCallback<T>,
    private val onItemListClicked: ((view: View?, item: T) -> Unit)? = null
) : ListAdapter<T, DataBindingViewHolder<T>>(diffCallback){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder<T> {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, viewType, parent, false)
        return DataBindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder<T>, position: Int) {
        val item = getItem(position)
        return holder.bind(View.OnClickListener {
            onItemListClicked?.let { _fun ->
                _fun(it,item)
            }
        }, item)
    }


    abstract override fun getItemViewType(position: Int): Int

}

