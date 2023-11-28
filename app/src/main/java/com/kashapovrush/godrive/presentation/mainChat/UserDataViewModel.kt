package com.kashapovrush.godrive.presentation.mainChat

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import com.kashapovrush.godrive.domain.mainChat.ChangePhotoUserUseCase
import com.kashapovrush.godrive.domain.mainChat.InitDataUserUseCase
import com.kashapovrush.godrive.domain.mainChat.SelectUseCase
import com.kashapovrush.godrive.domain.mainChat.SetImageUserUseCase
import com.kashapovrush.godrive.domain.sign.GetDatabaseReferenceUseCase
import com.kashapovrush.godrive.domain.sign.GetStorageReferenceUseCase
import com.kashapovrush.godrive.domain.sign.GetUidUseCase
import javax.inject.Inject

class UserDataViewModel @Inject constructor(
    private val getUidUseCase: GetUidUseCase,
    private val getDatabaseReferenceUseCase: GetDatabaseReferenceUseCase,
    private val getStorageReferenceUseCase: GetStorageReferenceUseCase,
    private val changePhotoUserUseCase: ChangePhotoUserUseCase,
    private val initDataUserUseCase: InitDataUserUseCase,
    private val selectUseCase: SelectUseCase,
    private val setImageUserUseCase: SetImageUserUseCase
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

    fun changePhotoUser(activity: Activity) {
        changePhotoUserUseCase(activity)
    }

    fun initUserData(text: TextView, view: ImageView, state: Boolean) {
        initDataUserUseCase(view, text, state)
    }

    fun selectedCity(
        listCity: Array<String>,
        cityValue: String,
        state: Boolean,
        putCityValue: (position: Int) -> Unit
    ): AdapterView.OnItemSelectedListener {
        return selectUseCase(listCity, cityValue, state, putCityValue)
    }

    fun setImageUser(requestCode: Int, resultCode: Int, data: Intent?, view: ImageView, context: Context) {
        setImageUserUseCase(requestCode, resultCode, data, view, context)
    }
}