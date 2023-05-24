package com.kashapovrush.godrivekotlin.utilities

import com.kashapovrush.godrivekotlin.activities.MainActivity

class Constants {
    companion object {
        const val KEY_COLLECTION_USERS = "users"
        const val KEY_COLLECTION_USERNAMES = "usernames"
        const val KEY_PREFERENCE_NAME = "message"
        const val KEY_SIGN_IN = 100
        const val KEY_CHILD_ID = "id"
        const val KEY_DATE = "date"
        const val KEY_CHILD_PHONE_NUMBER = "phone"
        const val KEY_CHILD_USERNAME = "username"
        const val KEY_PROFILE_IMAGE = "profile_image"
        const val KEY_FILE_URL = "fileUrl"
        const val KEY_CITY = "city"
        const val KEY_PHOTO_URL = "photoUrl"
        const val KEY_MESSAGE = "message_key"
        const val KEY_TYPE_MESSAGE = "type"
        const val TYPE_TEXT = "text"
        const val TYPE_VOICE = "voice"

        lateinit var mainActivity: MainActivity

        const val BASE_PHOTO_URL =
            "https://firebasestorage.googleapis.com/v0/b/godrivekotlin-27458.appspot.com/o/profile_image%2Fbase_photo2.jpeg?alt=media&token=ac9642a9-aa50-40ab-8054-598ff3a401d2"
    }
}