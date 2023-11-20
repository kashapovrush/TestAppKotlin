package com.kashapovrush.godrive.presentation.sign

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kashapovrush.godrive.databinding.ActivitySignInBinding
import com.kashapovrush.godrive.presentation.Application
import com.kashapovrush.godrive.presentation.ViewModelFactory
import com.kashapovrush.godrive.presentation.freeChat.FreeChatActivity
import com.kashapovrush.godrive.presentation.mainChat.MainActivity
import com.kashapovrush.godrive.utilities.PreferenceManager
import javax.inject.Inject

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private var id: String = ""
    private lateinit var preferenceManager: PreferenceManager
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var viewModel: SignIngViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (application as Application).component
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, viewModelFactory)[SignIngViewModel::class.java]
        preferenceManager = PreferenceManager(applicationContext)

        setCallback()
        binding.freeSignIn.setOnClickListener {
            startActivity(FreeChatActivity.newIntent(this@SignInActivity))
            finish()
        }

        binding.buttonSignIn.setOnClickListener {
            val numberPhone = "+7" + binding.number.text.toString()
            validatePhoneNumber(numberPhone)
        }
        checkAuthState()
    }

    private fun validatePhoneNumber(numberPhone: String) {
        if (numberPhone.isEmpty()) {
            toastShow("Ведите номер телефона")
        } else {
            runProgressBar()
            viewModel.phoneNumberVerification(numberPhone, this@SignInActivity, callbacks)
        }
    }

    private fun toastShow(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }

    private fun checkAuthState() {
        viewModel.checkAutoState(this@SignInActivity, MainActivity::class.java)
    }

    private fun runProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun stopProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun setCallback() {
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                viewModel.signInWithCredentialUC(credential).addOnCompleteListener {
                    if (it.isSuccessful) {
                        startActivity(MainActivity.newIntent(this@SignInActivity))
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
                preferenceManager.putString(EXTRA_SIGN_IN_ID, id)
                startActivity(SignInEnterCodeActivity.newIntent(this@SignInActivity))
                finish()
            }
        }
    }

    companion object {
        const val EXTRA_SIGN_IN_ID = "SignInId"
    }
}