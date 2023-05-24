package com.kashapovrush.godrivekotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.kashapovrush.godrivekotlin.adapter.message_recycler_view.holder.HolderTextMessage
import com.kashapovrush.godrivekotlin.adapter.message_recycler_view.holder.HolderVoiceMessage
import com.kashapovrush.godrivekotlin.adapter.message_recycler_view.view.MessageView
import com.kashapovrush.godrivekotlin.databinding.MessageTextItemBinding
import com.kashapovrush.godrivekotlin.databinding.MessageVoiceItemBinding
import com.kashapovrush.godrivekotlin.utilities.Constants.Companion.BASE_PHOTO_URL
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*


class ChatAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listMessage = mutableListOf<MessageView>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            MessageView.MESSAGE_VOICE -> {
                return HolderVoiceMessage(
                    MessageVoiceItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                return HolderTextMessage(
                    MessageTextItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return listMessage.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HolderTextMessage -> drawTextMessage(holder, position)
            is HolderVoiceMessage -> drawVoiceMessage(holder, position)
            else -> {

            }
        }
    }

    private fun drawTextMessage(holder: HolderTextMessage, position: Int) {
        holder.textMessage.text = listMessage[position].message
        holder.dateMessageText.text = listMessage[position].date.asTime()
        holder.imageProfileMessageText.setImage(listMessage[position].photoUrl)
    }

    private fun drawVoiceMessage(holder: HolderVoiceMessage, position: Int) {
        holder.dateMessageVoice.text = listMessage[position].date.asTime()
        holder.imageProfileMessageVoice.setImage(listMessage[position].photoUrl)
    }

    override fun getItemViewType(position: Int): Int {
        return listMessage[position].getTypeView()
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        when (holder) {
            is HolderVoiceMessage -> holder.onAttach(listMessage[holder.adapterPosition])
        }
        super.onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        when (holder) {
            is HolderVoiceMessage -> holder.onDetach()
        }
        super.onViewDetachedFromWindow(holder)
    }


    fun setList(list: MessageView, onSuccess: () -> Unit) {
        if (!listMessage.contains(list)) {
            listMessage.add(list)
            notifyItemInserted(listMessage.size)
        }
        onSuccess()
    }

    private fun String.asTime(): String {
        val time = Date(this.toLong())
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        return timeFormat.format(time)
    }

}

private fun ImageView.setImage(url: String) {
    if (url.isEmpty()) {
        Picasso.get()
            .load(BASE_PHOTO_URL)
            .fit()
            .into(this)
//        setImageResource(R.drawable.ic_base_photo_camera)
    } else {
        Picasso.get()
            .load(url)
            .fit()
            .into(this)
    }
}


