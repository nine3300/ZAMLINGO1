plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.zamlingo"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.zamlingo"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // Firebase libraries
    implementation(platform(libs.firebase.bom))  // Firebase Bill of Materials
    implementation(libs.firebase.database)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore) {
        exclude(group = "com.google.firebase", module = "firebase-common")
    }

    // ML Kit and Translation dependencies
    implementation(libs.mlkit.common)
    implementation(libs.mlkit.translate)

    // Vision-related library (only `play-services-vision` is required)
    implementation("com.google.android.gms:play-services-vision:20.0.0")  // Correct version

    // OkHttp, JSON parsing, and Core KTX for TTS
    implementation(libs.okhttp)
    implementation(libs.json)
    implementation(libs.coreKtx)

    // Gson for JSON handling
    implementation(libs.gson)

    // Retrofit dependencies
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Testing libraries
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}