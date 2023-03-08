package com.kashapovrush.godrivekotlin.utilities

import com.kashapovrush.godrivekotlin.adapter.message_recycler_view.view.MessageView
import com.kashapovrush.godrivekotlin.adapter.message_recycler_view.view.ViewTextMessage
import com.kashapovrush.godrivekotlin.adapter.message_recycler_view.view.ViewVoiceMessage
import com.kashapovrush.godrivekotlin.models.User
import com.kashapovrush.godrivekotlin.utilities.Constants.Companion.TYPE_TEXT
import com.kashapovrush.godrivekotlin.utilities.Constants.Companion.TYPE_VOICE

class ViewFactory {
    companion object {
        fun getType(message: User) : MessageView {
            return when(message.type) {
                TYPE_VOICE -> ViewVoiceMessage(message.username, message.message, message.fileUrl, message.messageKey)
                else -> ViewTextMessage(message.username, message.message, message.fileUrl, message.messageKey)

            }
        }
    }
}