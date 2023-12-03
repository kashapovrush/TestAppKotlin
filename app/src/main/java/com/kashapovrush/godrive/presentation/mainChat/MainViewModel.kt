package com.kashapovrush.godrive.presentation.mainChat

import android.content.Context
import android.view.MotionEvent
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query
import com.google.firebase.storage.StorageReference
import com.kashapovrush.godrive.data.repository.MainRepositoryImpl
import com.kashapovrush.godrive.domain.mainChat.GetAuthUseCase
import com.kashapovrush.godrive.domain.mainChat.GetMessageKeyUseCase
import com.kashapovrush.godrive.domain.mainChat.GetNotificationUseCase
import com.kashapovrush.godrive.domain.mainChat.GetReferenceNotificationUseCase
import com.kashapovrush.godrive.domain.mainChat.GetUserUseCase
import com.kashapovrush.godrive.domain.mainChat.InitRCViewUseCase
import com.kashapovrush.godrive.domain.mainChat.InitUserDataUseCase
import com.kashapovrush.godrive.domain.mainChat.LoggedInUserCounterUseCase
import com.kashapovrush.godrive.domain.mainChat.RemoveMessageAfterTimeUseCase
import com.kashapovrush.godrive.domain.mainChat.SendTextMessageUseCase
import com.kashapovrush.godrive.domain.mainChat.SendVoiceMessageUseCase
import com.kashapovrush.godrive.domain.mainChat.SetNotificationUseCase
import com.kashapovrush.godrive.domain.mainChat.ShowButtonVoiceUseCase
import com.kashapovrush.godrive.domain.models.Notification
import com.kashapovrush.godrive.domain.models.User
import com.kashapovrush.godrive.domain.sign.GetDatabaseReferenceUseCase
import com.kashapovrush.godrive.domain.sign.GetStorageReferenceUseCase
import com.kashapovrush.godrive.domain.sign.GetUidUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getUidUseCase: GetUidUseCase,
    private val getDatabaseReferenceUseCase: GetDatabaseReferenceUseCase,
    private val getStorageReferenceUseCase: GetStorageReferenceUseCase,
    private val getAuthUseCase: GetAuthUseCase,
    private val initDataUserUseCase: InitUserDataUseCase,
    private val setNotificationUseCase: SetNotificationUseCase,
    private val removeMessageAfterTimeUseCase: RemoveMessageAfterTimeUseCase,
    private val loggedInUserCounterUseCase: LoggedInUserCounterUseCase,
    private val showButtonVoiceUseCase: ShowButtonVoiceUseCase,
    private val getMessageKeyUseCase: GetMessageKeyUseCase,
    private val sendTextMessageUseCase: SendTextMessageUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val sendVoiceMessageUseCase: SendVoiceMessageUseCase,
    private val getNotificationUseCase: GetNotificationUseCase,
    private val getReferenceNotificationUseCase: GetReferenceNotificationUseCase,
    private val initRCViewUseCase: InitRCViewUseCase
) : ViewModel() {

    fun getUid(): String {
        return getUidUseCase()
    }

    fun getDatabaseReference(): DatabaseReference {
        return getDatabaseReferenceUseCase()
    }

    fun getStorageReference(): StorageReference {
        return getStorageReferenceUseCase()
    }

    fun getAuth(): FirebaseAuth {
        return getAuthUseCase()
    }

    fun initUserData(view: ImageView, text: TextView, getUser: (snapshot: DataSnapshot) -> Unit) {
        initDataUserUseCase(view, text, getUser)
    }

    fun setNotification(
        key: String,
        context: Context,
        textBody: String
    ): ChildEventListener {
        return setNotificationUseCase(key, context, textBody)
    }

    fun removeMessageAfterTime(time: Long) {
        removeMessageAfterTimeUseCase(time)
    }

    fun loggedInUserCounter(randomNumber: Long) {
        loggedInUserCounterUseCase(randomNumber)
    }

    fun showButtonVoice(editText: EditText, afterTextChanged: () -> Unit) {
        showButtonVoiceUseCase(editText, afterTextChanged)
    }

    fun sendTextMessage(
        editText: EditText,
        addChildEventListener: () -> Unit,
        context: Context,
        messageKey: String,
        user: User,
        time: Long
    ) {
        sendTextMessageUseCase(
            editText,
            addChildEventListener,
            context,
            messageKey,
            user,
            time
        )
    }

    fun sendVoiceMessage(
        isCheckPermission: Boolean,
        messageKey: String,
        event: MotionEvent,
        editText: EditText,
        user: User,
        context: Context,
        addChildEventListener: () -> Unit
    ) {
        sendVoiceMessageUseCase(
            isCheckPermission,
            messageKey,
            event,
            editText,
            user,
            context,
            addChildEventListener
        )
    }

    fun getMessageKey(): String = getMessageKeyUseCase()

    fun getUser() = getUserUseCase()

    fun getNotification(): Notification {
        return getNotificationUseCase()
    }

    fun getReferenceNotification(): DatabaseReference {
        return getReferenceNotificationUseCase()
    }

    fun initRCView(rv: RecyclerView, llm: LinearLayoutManager) {
        initRCViewUseCase(rv, llm)
    }
}