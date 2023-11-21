package com.kashapovrush.godrive.domain.models

import javax.inject.Inject

data class Notification @Inject constructor(
    val token: String = ""
)
