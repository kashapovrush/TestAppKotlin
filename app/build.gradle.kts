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
        vectorDrawables {
            useSupportLibrary = true
        }
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
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.7"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(project(":features-mobile:sign-up-feature"))
    implementation(project(":features-mobile:profile-feature"))
    implementation(project(":features-mobile:profile-free-feature"))
    implementation(project(":features-mobile:main-chat-feature"))
    implementation(project(":features-mobile:free-chat-feature"))
    implementation(project(":features-mobile:enter-code-feature"))
    implementation(project(":features-mobile:settings-feature"))
    implementation(project(":features-mobile:palette"))
    implementation(project(":core:navigation"))
    implementation(project(":core:settings:settings-impl"))
    implementation(project(":core:settings:settings-api"))
    implementation(project(":core:utils"))
    implementation(project(":core:profile:profile-api"))
    implementation(project(":core:profile:profile-impl"))
    implementation(project(":core:authorization:authorization-api"))
    implementation(project(":core:authorization:authorization-impl"))



    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material3.extended)
    implementation(libs.lifecycle.runtime)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.google.service.auth)
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

    implementation(libs.navigation.ui.compose)

}