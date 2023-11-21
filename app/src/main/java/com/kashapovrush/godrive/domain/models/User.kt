package com.kashapovrush.godrive.domain.models

import javax.inject.Inject

data class User @Inject constructor(
    var username: String = "",
    var message: String = "",
    var type: String = "",
    var photoUrl: String = "",
    var fileUrl: String = "",
    var messageKey: String = "",
    var city: String = "",
    var date: Any = ""
)
