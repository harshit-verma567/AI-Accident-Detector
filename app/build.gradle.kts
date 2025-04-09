plugins {
    alias(libs.plugins.android.application)
   // ✅ Add this for Glide
}

android {
    namespace = "com.example.instarescue"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.instarescue"
        minSdk = 25
        targetSdk = 35
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
    // ✅ Google Maps
    implementation(libs.play.services.maps)

// ✅ Location services
    implementation(libs.play.services.location)

// ✅ Retrofit for API calls
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

// ✅ Glide (optional - for image loading)


    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}