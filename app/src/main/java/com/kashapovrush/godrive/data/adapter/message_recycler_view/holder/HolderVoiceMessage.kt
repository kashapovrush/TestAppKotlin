package com.kashapovrush.godrive.data.adapter.message_recycler_view.holder

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kashapovrush.godrive.data.adapter.message_recycler_view.view.MessageView
import com.kashapovrush.godrive.databinding.MessageVoiceItemBinding
import com.kashapovrush.godrive.utilities.VoicePlayer

class HolderVoiceMessage(binding: MessageVoiceItemBinding, private val context: Context) : RecyclerView.ViewHolder(binding.root) {

    val dateMessageVoice: TextView = binding.timeMessageVoice
    val buttonPlayVoice: ImageView = binding.imagePlayVoice
    val buttonStopVoice: ImageView = binding.imageStopVoice
    val imageProfileMessageVoice: ImageView = binding.imageProfileMessageVoice

    private val voicePlayer = VoicePlayer(context)

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