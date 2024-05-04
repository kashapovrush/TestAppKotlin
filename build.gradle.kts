// Top-level build file where you can add configuration options common to all sub-projects/modules.

//buildscript {
//    repositories{
//        google()
//        mavenCentral()
//    }
//    dependencies {
//        classpath ("com.google.gms:google-services:4.3.15")
//    }
//}

plugins {
    alias (libs.plugins.androidLibrary) apply false
    alias (libs.plugins.androidApplication) apply false
    alias (libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.google.services) apply false
}

//task clean(type: Delete) {
//    delete rootProject.buildDir
//}