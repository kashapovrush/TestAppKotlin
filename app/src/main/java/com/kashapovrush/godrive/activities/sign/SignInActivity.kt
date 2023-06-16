package com.kashapovrush.godrive.activities.sign

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kashapovrush.godrive.activities.MainActivity
import com.kashapovrush.godrive.databinding.ActivitySignInBinding
import com.kashapovrush.godrive.utilities.PreferenceManager
import java.util.concurrent.TimeUnit

class SignInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignInBinding
    private var id: String = ""
    private lateinit var preferenceManager: PreferenceManager
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        preferenceManager = PreferenceManager(applicationContext)
//        auth.firebaseAuthSettings.setAppVerificationDisabledForTesting(true)

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                auth.signInWithCredential(credential).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this@SignInActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        toastShow("Ошибка, попробуйте позже :(")
                        stopProgressBar()
                    }
                }
            }

            override fun onVerificationFailed(error: FirebaseException) {
                toastShow("Ошибка, попробуйте позже :(")
                stopProgressBar()
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                id = verificationId
                preferenceManager.putString("SignInId", id)
                val intent = Intent(this@SignInActivity, SignInEnterCodeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        binding.freeSignIn.setOnClickListener {
            val intent = Intent(this@SignInActivity, FreeChatActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.buttonSignIn.setOnClickListener {
            val numberPhone = "+7" + binding.number.text.toString()
            if (binding.number.text.toString().isEmpty()) {
                toastShow("Ведите номер телефона")
            } else {
                runProgressBar()
                startPhoneNumberVerification(numberPhone)
            }
        }
        checkAuthState()
    }

    private fun startPhoneNumberVerification(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this@SignInActivity)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }


//
//    @Deprecated("Deprecated in Java")
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == Constants.KEY_SIGN_IN) {
//            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//            try {
//                val account = task.getResult(ApiException::class.java)
//                if (account != null) {
//                    googleSignInActivity.firebaseAuthWithGoogle(account.idToken!!)
//                }
//            } catch (e: ApiException) {
//                Log.d("myLog", "Api exception")
//            }
//        }
//    }

    private fun toastShow(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }

    private fun checkAuthState() {
        if (auth.currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    fun runProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    fun stopProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }
}