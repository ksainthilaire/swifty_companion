package com.ksainthi.swifty.presentation.adapter

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class BindingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val binding: ViewDataBinding = DataBindingUtil.bind(itemView)!!
}