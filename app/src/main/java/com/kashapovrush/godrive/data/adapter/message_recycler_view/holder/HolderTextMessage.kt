package com.kashapovrush.godrive.data.adapter.message_recycler_view.holder

import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kashapovrush.godrive.databinding.MessageTextItemBinding

class HolderTextMessage(binding: MessageTextItemBinding) : RecyclerView.ViewHolder(binding.root) {


    val textMessage: TextView = binding.textMessage
    val dateMessageText: TextView = binding.timeMessageText
    val imageProfileMessageText: ImageView = binding.imageProfileMessageText


}
