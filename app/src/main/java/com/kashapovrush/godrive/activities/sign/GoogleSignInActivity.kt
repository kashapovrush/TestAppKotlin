package com.kashapovrush.godrive.activities.sign

import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kashapovrush.godrive.R
import com.kashapovrush.godrive.activities.MainActivity
import com.kashapovrush.godrive.utilities.Constants

class GoogleSignInActivity(private val signInActivity: SignInActivity) {

    private var auth: FirebaseAuth = Firebase.auth

    fun signInWithGoogle() {
        val signInClient = getClient().signInIntent
        signInActivity.startActivityForResult(signInClient, Constants.KEY_SIGN_IN)
    }

    private fun getClient() : GoogleSignInClient {
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(signInActivity.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(signInActivity, gso)
    }

    fun firebaseAuthWithGoogle(idToken : String) {
        val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(firebaseCredential).addOnCompleteListener{
            if (it.isSuccessful) {
                Log.d("myLog", "Google sign in done")
                if (auth != null) {
                    val intent = Intent(signInActivity, MainActivity::class.java)
                    signInActivity.startActivity(intent)
                }
            } else {
                Log.d("myLog", "Google sign in error")
            }
        }
    }

}

