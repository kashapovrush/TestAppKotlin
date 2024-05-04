plugins {
//    id("com.android.application")
    alias(libs.plugins.androidApplication)
//    id("org.jetbrains.kotlin.android")
    alias(libs.plugins.jetbrainsKotlinAndroid)
//    id("com.google.gms.google-services")
//    id("kotlin-kapt")
    alias(libs.plugins.ksp)
    alias(libs.plugins.google.services)
}

android {
    signingConfigs {
//        release {
//            storeFile file("C:\\Users\\Rushan\\AndroidStudioProjects\\keystore\\goDriveKey.jks")
//            storePassword "8008674385"
//            keyAlias "goDriveApp"
//            keyPassword "8008674385"
//        }
    }
    namespace = "com.kashapovrush.godrive"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.kashapovrush.godrive"
        minSdk = 26
        targetSdk = 34
        versionCode = 9
        versionName = "1.0.7"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//        signingConfig signingConfigs.release
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles (getDefaultProguardFile ("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.google.service.auth)
//    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation(libs.browser)
    implementation(libs.google.service.safetynet)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //firebase
    implementation (platform (libs.firebase.bom))
    implementation(libs.firebase.storage.ktx)
    implementation(libs.firebase.database.ktx)
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.appcheck.playintegrity)
    implementation(libs.firebase.messaging.ktx)

    //Scalable Size Unit
    implementation(libs.android.sdp)
    implementation(libs.android.ssp)

    //Image
    implementation(libs.roundedimageview)
    api(libs.android.image.cropper)
    implementation(libs.picasso)

    //MultiDex
    implementation(libs.androidx.multidex)

    //Retrofit
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.gson)
    implementation(libs.volley.core)

    //Coroutines
    implementation(libs.coroutine.android)
    implementation(libs.coroutine.core)
    implementation(libs.lifecycle.viewmodel.ktx)

    //Dagger2
    implementation(libs.dagger.core)
    ksp(libs.dagger.compiler)


}