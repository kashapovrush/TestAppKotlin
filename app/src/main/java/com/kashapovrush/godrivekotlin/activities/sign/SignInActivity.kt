package com.kashapovrush.godrivekotlin.activities.sign

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kashapovrush.godrivekotlin.R
import com.kashapovrush.godrivekotlin.activities.MainActivity
import com.kashapovrush.godrivekotlin.databinding.ActivitySignInBinding
import com.kashapovrush.godrivekotlin.utilities.Constants

class SignInActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private lateinit var binding: ActivitySignInBinding
    private lateinit var googleSignInActivity: GoogleSignInActivity
    private lateinit var emailPasswordActivity: EmailPasswordActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        googleSignInActivity = GoogleSignInActivity(this)
        emailPasswordActivity = EmailPasswordActivity(this)
        binding.googleSignIn.setOnClickListener {
            googleSignInActivity.signInWithGoogle()
        }

        binding.emailSignIn.setOnClickListener {
            emailPasswordActivity.signInWithEmail(binding.email.text.toString(), binding.password.text.toString())
        }

        binding.emailCreated.setOnClickListener {
            emailPasswordActivity.createAccount(binding.email.text.toString(), binding.password.text.toString())
        }
        checkAuthState()
    }

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

    private fun checkAuthState() {
        if (auth != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun toastShow (message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}