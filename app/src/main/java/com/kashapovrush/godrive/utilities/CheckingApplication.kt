package com.kashapovrush.godrive.utilities

class CheckingApplication {

    companion object {
        fun isCheckedTextMessage(message: String): Boolean {
            if (message.length >= 100) return false
            return true
        }
    }
}