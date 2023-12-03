package com.kashapovrush.godrive.domain.mainChat

import android.content.Context
import android.view.MotionEvent
import android.widget.EditText
import com.kashapovrush.godrive.domain.models.User
import javax.inject.Inject

class SendVoiceMessageUseCase @Inject constructor(private val repository: MainRepository) {

    operator fun invoke(
        isCheckPermission: Boolean,
        messageKey: String,
        event: MotionEvent,
        editText: EditText,
        user: User,
        context: Context,
        addChildEventListener: () -> Unit
    ) {
        repository.sendVoiceMessage(
            isCheckPermission,
            messageKey,
            event,
            editText,
            user,
            context,
            addChildEventListener
        )
    }
}