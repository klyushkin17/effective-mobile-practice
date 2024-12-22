plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.example.rxjava_practise"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.rxjava_practise"
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //RecyclerView
    implementation(libs.androidx.recyclerview)
    implementation (libs.constraint.layout)

    //Retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.moshi)
    implementation (libs.okhttp)
    implementation (libs.logging.interceptor)
    implementation (libs.rxjava3.retrofit.adapter)
    implementation (libs.converter.gson)

    //RxJava
    implementation ("io.reactivex.rxjava3:rxandroid:3.0.2")
    implementation ("com.jakewharton.rxbinding4:rxbinding:4.0.0")
    implementation ("com.jakewharton.rxbinding4:rxbinding-appcompat:4.0.0")
    implementation ("com.jakewharton.rxbinding4:rxbinding-core:4.0.0")

    //RxBinding
    implementation ("io.reactivex.rxjava2:rxjava:2.2.19")
    implementation ("io.reactivex.rxjava2:rxandroid:2.1.1")
    implementation ("com.jakewharton.rxbinding2:rxbinding:2.2.0")
    implementation ("com.jakewharton.rxbinding2:rxbinding-kotlin:2.2.0")

    //RxAndroid
    implementation ("io.reactivex.rxjava3:rxjava:3.1.5")


}