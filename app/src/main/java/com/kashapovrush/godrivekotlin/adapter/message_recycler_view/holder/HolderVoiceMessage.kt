package com.kashapovrush.godrivekotlin.adapter.message_recycler_view.holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kashapovrush.godrivekotlin.adapter.message_recycler_view.view.MessageView
import com.kashapovrush.godrivekotlin.databinding.MessageVoiceItemBinding
import com.kashapovrush.godrivekotlin.utilities.VoicePlayer

class HolderVoiceMessage(binding: MessageVoiceItemBinding) : RecyclerView.ViewHolder(binding.root) {

    val userNameVoice: TextView = binding.userNameVoice

    val buttonPlayVoice: ImageView = binding.imagePlayVoice
    val buttonStopVoice: ImageView = binding.imageStopVoice

    private val voicePlayer = VoicePlayer()

    fun onAttach(view: MessageView) {
        voicePlayer.init()
        buttonPlayVoice.setOnClickListener {
            buttonPlayVoice.visibility = View.GONE
            buttonStopVoice.visibility = View.VISIBLE
            buttonStopVoice.setOnClickListener {
                voicePlayer.stop {
                    buttonStopVoice.setOnClickListener(null)
                    buttonPlayVoice.visibility = View.VISIBLE
                    buttonStopVoice.visibility = View.GONE
                }

            }
            voicePlayer.play(view.messageKey, view.fileUrl) {
                buttonPlayVoice.visibility = View.VISIBLE
                buttonStopVoice.visibility = View.GONE
            }

        }
    }

    fun onDetach() {
        buttonPlayVoice.setOnClickListener(null)
        voicePlayer.release()
    }
}