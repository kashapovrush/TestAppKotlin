package com.kashapovrush.godrive.presentation.mainChat

import android.content.Context
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import com.kashapovrush.godrive.data.repository.MainRepositoryImpl
import com.kashapovrush.godrive.domain.mainChat.GetAuthUseCase
import com.kashapovrush.godrive.domain.mainChat.GetMessageKeyUseCase
import com.kashapovrush.godrive.domain.mainChat.InitUserDataUseCase
import com.kashapovrush.godrive.domain.mainChat.LoggedInUserCounterUseCase
import com.kashapovrush.godrive.domain.mainChat.RemoveMessageAfterTimeUseCase
import com.kashapovrush.godrive.domain.mainChat.SendTextMessageUseCase
import com.kashapovrush.godrive.domain.mainChat.SetNotificationUseCase
import com.kashapovrush.godrive.domain.mainChat.ShowButtonVoiceUseCase
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
    private val sendTextMessageUseCase: SendTextMessageUseCase
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

    fun initUserData(view: ImageView, text: TextView, city: String, state: Boolean,
                     getUser: (snapshot: DataSnapshot) -> Unit) {
        initDataUserUseCase(view, text, city, state, getUser)
    }

    fun setNotification(
        key: String,
        context: Context,
        cityValue: String,
        textBody: String
    ): ChildEventListener {
        return setNotificationUseCase(key, context, cityValue, textBody)
    }

    fun removeMessageAfterTime(cityValue: String, time: Long) {
        removeMessageAfterTimeUseCase(cityValue, time)
    }

    fun loggedInUserCounter(cityValue: String, randomNumber: Long) {
        loggedInUserCounterUseCase(cityValue, randomNumber)
    }

    fun showButtonVoice(editText: EditText, afterTextChanged: () -> Unit) {
        showButtonVoiceUseCase(editText, afterTextChanged)
    }

    fun sendTextMessage(
        isEmptyEditText: Boolean,
        editText: EditText,
        cityValue: String,
        addChildEventListener: () -> Unit,
        context: Context,
        messageKey: String,
        user: User,
        time: Long
    ) {
        sendTextMessageUseCase(
            isEmptyEditText,
            editText,
            cityValue,
            addChildEventListener,
            context,
            messageKey,
            user,
            time
        )
    }

    fun getMessageKey(): String = getMessageKeyUseCase()
}