plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.google.devtools.ksp)
}

android {
    namespace = "com.filippoengidashet.pokdexapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.filippoengidashet.pokdexapp"
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    //Compose Navigation
    implementation(libs.compose.navigation)

    //Hilt - Dependency injection
    implementation(libs.hilt)
    ksp(libs.dagger.hilt.compiler)
    //ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    //Retrofit - Rest API integration
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson.converter)
    implementation(libs.retrofit.okhttp.interceptor)

    //LazyList pagination on scroll
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)

    //Image Loading and Caching
    implementation(libs.coil.compose)

    //Data Persistence (Room)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    //Testing
    testImplementation("androidx.paging:paging-common:3.3.6")
    testImplementation("androidx.paging:paging-testing:3.3.6")

    //Basic mocking libraries
    testImplementation(libs.mockito)
    testImplementation(libs.mockk.android)

    androidTestImplementation(libs.mockito)
    androidTestImplementation(libs.mockito.kotlin)
    androidTestImplementation(libs.mockito.inline)

//    androidTestImplementation(libs.mockk.android)

//    androidTestImplementation("org.mockito:mockito-core:5.2.0")
//    androidTestImplementation("org.mockito.kotlin:mockito-kotlin:5.1.0")

    //Jetpack compose UI testing
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    testImplementation(libs.junit)
    testImplementation(libs.test.coroutines)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
