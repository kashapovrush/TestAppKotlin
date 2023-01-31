package com.kashapovrush.godrivekotlin.activities.sign

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kashapovrush.godrivekotlin.activities.MainActivity
import com.kashapovrush.godrivekotlin.databinding.ActivitySignInBinding
import com.kashapovrush.godrivekotlin.utilities.Constants

class SignInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignInBinding
    private lateinit var googleSignInActivity: GoogleSignInActivity
    private lateinit var phoneAuthActivity: PhoneAuthActivity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        googleSignInActivity = GoogleSignInActivity(this)
        phoneAuthActivity = PhoneAuthActivity(this, binding.password)
        binding.googleSignIn.setOnClickListener {
            googleSignInActivity.signInWithGoogle()
        }
//        emailPasswordActivity = EmailPasswordActivity(this)
        binding.emailSignIn.setOnClickListener {
            if (binding.number.text.toString().isEmpty()) {
                toastShow("Enter number")
            } else {
                phoneAuthActivity.startPhoneNumberVerification(binding.number.text.toString())
            }
        }
        checkAuthState()
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.KEY_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    googleSignInActivity.firebaseAuthWithGoogle(account.idToken!!)
                }
            } catch (e: ApiException) {
                Log.d("myLog", "Api exception")
            }
        }
    }

    private fun toastShow(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    private fun checkAuthState() {
        if (auth.currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}