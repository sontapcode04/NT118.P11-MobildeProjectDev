plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.mobileproject"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.mobileproject"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.database)
    testImplementation(libs.junit)
    implementation(libs.android)
    implementation(libs.mapbox.search.android.ui)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.1")
    implementation(libs.firebase.bom)
    implementation(libs.google.firebase.auth)
    implementation(libs.play.services.auth)
    implementation(libs.mpandroidchart)
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    //implementation ("com.mapbox.maps:plugin-annotation:10.15.0")
    implementation("com.mapbox.navigation:ui-maneuver:2.15.2")
    implementation("com.mapbox.navigation:android:2.17.4")
    implementation ("com.mapbox.navigation:ui-base:2.17.4")
    implementation ("com.mapbox.navigation:core:2.17.4")

}