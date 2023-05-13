package com.kashapovrush.godrivekotlin.models

data class User(
    var username: String = "",
    var message: String = "",
    var type: String = "",
    var photoUrl: String = "",
    var fileUrl: String = "",
    var messageKey: String = "",
    var city: String = ""
)
