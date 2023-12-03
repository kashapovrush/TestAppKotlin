package com.kashapovrush.godrive.domain.mainChat

import android.content.Context
import android.widget.EditText
import android.widget.ImageView
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DatabaseReference
import com.kashapovrush.godrive.domain.models.User
import javax.inject.Inject

class SendTextMessageUseCase @Inject constructor(private val repository: MainRepository) {

    operator fun invoke(
        editText: EditText,
        addChildEventListener: () -> Unit,
        context: Context,
        messageKey: String,
        user: User,
        time: Long
    ) {
        repository.sendTextMessage(
            editText,
            addChildEventListener,
            context,
            messageKey,
            user,
            time
        )
    }
}