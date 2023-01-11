package com.kashapovrush.godrivekotlin.activities.sign

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kashapovrush.godrivekotlin.activities.MainActivity

class EmailPasswordActivity (private val signInActivity: SignInActivity) {

    private var auth: FirebaseAuth = Firebase.auth

     fun signInWithEmail(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val intent = Intent(signInActivity, MainActivity::class.java)
                signInActivity.startActivity(intent)
            } else {
                Log.d("myLog", "Email sign in error")
            }
        }
    }

    fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                toastShow("Account created")
            } else {
                Log.d("myLog", "Email created error")
            }
        }
    }

    private fun toastShow (message: String) {
        Toast.makeText(signInActivity, message, Toast.LENGTH_SHORT).show()
    }

}