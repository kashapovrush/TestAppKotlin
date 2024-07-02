package com.kashapovrush.godrive.presentation.mainChat

//class UserDataActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityUserDataBinding
//    private lateinit var storage: StorageReference
//    private lateinit var user: User
//    private lateinit var preferenceManager: PreferenceManagerImpl
//    private lateinit var uid: String
//    private lateinit var database: DatabaseReference
//    private lateinit var viewModel: UserDataViewModel
//
//    @Inject
//    lateinit var viewModelFactory: ViewModelFactory
//    val listCity = arrayOf(
//        "Выберите город",
//        "Уфа",
//        "Трасса М5",
//        "Октябрьский",
//        "Туймазы",
//        "Чишмы",
//        "Кандры",
//        "Буздяк",
//        "Шаран",
//        "Языково"
//    )
//
//    private val component by lazy {
//        (application as Application).component
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        component.inject(this)
//        super.onCreate(savedInstanceState)
//        binding = ActivityUserDataBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        user = User()
//        viewModel = ViewModelProvider(this, viewModelFactory)[UserDataViewModel::class.java]
//        uid = viewModel.getUid()
//        database = viewModel.getDatabaseReference()
//        storage = viewModel.getStorageReference()
//        preferenceManager =
//            com.kashapovrush.utils.preferencesManager.PreferenceManagerImpl(applicationContext)
//
//    }
//
//    override fun onResume() {
//        super.onResume()
//        viewModel.initUserData(
//            binding.choiseCity,
//            binding.imageProfile,
//            preferenceManager.getString(KEY_PREFERENCE_NAME) != null
//        )
//
//        var arrayAdapter = ArrayAdapter(
//            this,
//            android.R.layout.simple_spinner_dropdown_item,
//            listCity
//        )
//
//        binding.selectCity.adapter = arrayAdapter
//        binding.selectCity.onItemSelectedListener = viewModel.selectedCity(
//            listCity,
//            preferenceManager.getString(KEY_PREFERENCE_NAME).toString(),
//            preferenceManager.getBoolean(KEY_NOTIFICATION_STATE),
//            putCityValue = {
//                preferenceManager.putString(KEY_PREFERENCE_NAME, listCity[it])
//            }
//        )
//
//        binding.buttonBack.setOnClickListener {
//            startActivity(MainActivity.newIntent(this))
//            finish()
//        }
//
//        binding.layoutImage.setOnClickListener {
//            viewModel.changePhotoUser(this)
//        }
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        viewModel.setImageUser(requestCode, resultCode, data, binding.imageProfile, this)
//
//    }
//
//    @SuppressLint("MissingSuperCall")
//    override fun onBackPressed() {
//        startActivity(MainActivity.newIntent(this))
//        finish()
//    }
//
//    companion object {
//
//        fun newIntent(context: Context): Intent {
//            return Intent(context, UserDataActivity::class.java)
//        }
//    }
//}