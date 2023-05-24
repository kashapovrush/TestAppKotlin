package com.kashapovrush.godrivekotlin.adapter.message_recycler_view.holder

import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kashapovrush.godrivekotlin.databinding.MessageTextItemBinding

class HolderTextMessage(binding: MessageTextItemBinding) : RecyclerView.ViewHolder(binding.root) {


    val textMessage: TextView = binding.textMessage
    val dateMessageText: TextView = binding.timeMessageText
    val imageProfileMessageText: ImageView = binding.imageProfileMessageText


}
