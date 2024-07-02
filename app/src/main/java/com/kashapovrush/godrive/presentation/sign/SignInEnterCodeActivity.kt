package com.kashapovrush.godrive.presentation.sign

//import android.content.Context
//import android.content.Intent
//import android.os.Bundle
//import android.text.Editable
//import android.text.TextWatcher
//import android.view.View
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.lifecycle.ViewModelProvider
//import com.google.firebase.auth.PhoneAuthProvider
//import com.google.firebase.database.*
//import com.kashapovrush.godrive.presentation.mainChat.MainActivity
//import com.kashapovrush.godrive.databinding.ActivitySignInEnterCodeBinding
//import com.kashapovrush.godrive.presentation.Application
//import com.kashapovrush.godrive.presentation.ViewModelFactory
//import com.kashapovrush.utils.constants.Constants
//import com.kashapovrush.utils.constants.Constants.Companion.KEY_COLLECTION_USERS
//import com.kashapovrush.utils.preferences.PreferenceManagerImpl
//import javax.inject.Inject
//
//class SignInEnterCodeActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivitySignInEnterCodeBinding
//    private lateinit var preferenceManager: PreferenceManagerImpl
//    private lateinit var callback: TextWatcher
//    private lateinit var viewModel: SignInEnterCodeViewModel
//
//    @Inject
//    lateinit var viewModelFactory: ViewModelFactory
//
//    private val component by lazy {
//        (application as Application).component
//    }
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        component.inject(this)
//        super.onCreate(savedInstanceState)
//        binding = ActivitySignInEnterCodeBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        viewModel = ViewModelProvider(this, viewModelFactory)[SignInEnterCodeViewModel::class.java]
//
//        preferenceManager =PreferenceManagerImpl(applicationContext)
//
//        setCallbackTextWatcher()
//        binding.password.addTextChangedListener(callback)
//    }
//
//    private fun toastShow(message: String) {
//        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
//    }
//
//    private fun setCallbackTextWatcher() {
//        callback = object : TextWatcher {
//
//            override fun afterTextChanged(s: Editable?) {
//                if (binding.password.text.toString().length == 6) {
//                    runProgressBar()
//                    val id = preferenceManager.getString(SignInActivity.EXTRA_SIGN_IN_ID)
//                    val credential =
//                        PhoneAuthProvider.getCredential(
//                            id.toString(),
//                            binding.password.text.toString()
//                        )
//                    viewModel.signInWithCredentialUC(credential).addOnCompleteListener { task ->
//                        if (task.isSuccessful) {
//                            val uid = viewModel.getUid()
//                            val dataMap = mutableMapOf<String, Any>()
//                            dataMap[Constants.KEY_CHILD_ID] = uid
//                            val db = viewModel.getDatabaseReference()
//                            db.child(KEY_COLLECTION_USERS).child(uid)
//                                .addListenerForSingleValueEvent(setEventListener(dataMap, uid, db))
//                        }
//                    }
//                        .addOnFailureListener {
//                            toastShow("Вы ввели неверный код")
//                            stopProgressBar()
//                        }
//                }
//            }
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//            }
//        }
//    }
//
//    private fun setEventListener(
//        dataMap: MutableMap<String, Any>,
//        uid: String,
//        db: DatabaseReference
//    ) = object : ValueEventListener {
//        override fun onDataChange(snapshot: DataSnapshot) {
//            if (!snapshot.hasChild(Constants.KEY_CHILD_USERNAME)) {
//                dataMap[Constants.KEY_CHILD_USERNAME] = uid
//            }
//            db.child(KEY_COLLECTION_USERS).child(uid)
//                .updateChildren(dataMap)
//                .addOnCompleteListener {
//                    if (it.isSuccessful) {
//                        startActivity(MainActivity.newIntent(this@SignInEnterCodeActivity))
//                        finish()
//                    } else {
//                        toastShow("Ошибка, попробуйте позже :(")
//                        stopProgressBar()
//                    }
//                }
//        }
//
//        override fun onCancelled(error: DatabaseError) {
//        }
//    }
//
//
//    override fun onBackPressed() {
//
//    }
//
//    fun runProgressBar() {
//        binding.progressBarEnterCode.visibility = View.VISIBLE
//    }
//
//    fun stopProgressBar() {
//        binding.progressBarEnterCode.visibility = View.INVISIBLE
//    }
//
//    companion object {
//
//        fun newIntent(context: Context): Intent {
//            return Intent(context, SignInEnterCodeActivity::class.java)
//        }
//    }
//}