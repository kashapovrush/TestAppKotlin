package com.kashapovrush.godrivekotlin.adapter.message_recycler_view.view

interface MessageView {
    val username: String
    val message: String
    val fileUrl: String
    val messageKey: String

    companion object {
        val MESSAGE_TEXT: Int
            get() = 0
        val MESSAGE_VOICE: Int
            get() = 1
    }

    fun getTypeView(): Int
}