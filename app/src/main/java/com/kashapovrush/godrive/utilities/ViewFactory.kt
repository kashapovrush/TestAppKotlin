package com.kashapovrush.godrive.utilities

import com.kashapovrush.godrive.presentation.adapter.message_recycler_view.view.MessageView
import com.kashapovrush.godrive.presentation.adapter.message_recycler_view.view.ViewTextMessage
import com.kashapovrush.godrive.presentation.adapter.message_recycler_view.view.ViewVoiceMessage
import com.kashapovrush.godrive.domain.models.User
import com.kashapovrush.godrive.utilities.Constants.Companion.TYPE_VOICE

class ViewFactory {
    companion object {
        fun getType(message: User): MessageView {
            return when (message.type) {
                TYPE_VOICE -> ViewVoiceMessage(
                    message.username,
                    message.message,
                    message.fileUrl,
                    message.messageKey,
                    message.date.toString(),
                    message.photoUrl
                )
                else -> ViewTextMessage(
                    message.username,
                    message.message,
                    message.fileUrl,
                    message.messageKey,
                    message.date.toString(),
                    message.photoUrl
                )

            }
        }
    }
}