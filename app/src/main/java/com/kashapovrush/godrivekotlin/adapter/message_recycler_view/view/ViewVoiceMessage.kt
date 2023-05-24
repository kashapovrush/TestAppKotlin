package com.kashapovrush.godrivekotlin.adapter.message_recycler_view.view

data class ViewVoiceMessage(
    override val username: String,
    override val message: String = "",
    override val fileUrl: String,
    override val messageKey: String,
    override val date: String,
    override val photoUrl: String


) : MessageView {
    override fun getTypeView(): Int {
        return MessageView.MESSAGE_VOICE
    }
}