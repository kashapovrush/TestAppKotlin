package com.kashapovrush.godrive.presentation.mainChat

import android.app.Activity
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import com.kashapovrush.godrive.domain.mainChat.ChangePhotoUserUseCase
import com.kashapovrush.godrive.domain.mainChat.InitDataUserUseCase
import com.kashapovrush.godrive.domain.sign.GetDatabaseReferenceUseCase
import com.kashapovrush.godrive.domain.sign.GetStorageReferenceUseCase
import com.kashapovrush.godrive.domain.sign.GetUidUseCase
import javax.inject.Inject

class UserDataViewModel @Inject constructor(
    private val getUidUseCase: GetUidUseCase,
    private val getDatabaseReferenceUseCase: GetDatabaseReferenceUseCase,
    private val getStorageReferenceUseCase: GetStorageReferenceUseCase,
    private val changePhotoUserUseCase: ChangePhotoUserUseCase,
    private val initDataUserUseCase: InitDataUserUseCase
): ViewModel() {

    fun getUid(): String {
        return getUidUseCase()
    }

    fun getDatabaseReference(): DatabaseReference {
        return getDatabaseReferenceUseCase()
    }

    fun getStorageReference(): StorageReference {
        return getStorageReferenceUseCase()
    }

    fun changePhotoUser(activity: Activity) {
        changePhotoUserUseCase(activity)
    }

    fun initUserData(view: ImageView, text: TextView) {
        initDataUserUseCase(view, text)
    }

}