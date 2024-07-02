package com.kashapovrush.profile_impl


import com.kashapovrush.database.AppDatabase.database
import com.kashapovrush.database.AppDatabase.uid
import com.kashapovrush.profile_api.ProfileRepository
import com.kashapovrush.utils.constants.Constants
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(): ProfileRepository {
    override fun selectedCity(city: String) {
        database.child(Constants.KEY_COLLECTION_USERS).child(uid).child(Constants.KEY_CITY)
            .setValue(city).addOnCompleteListener {
            }
    }

}