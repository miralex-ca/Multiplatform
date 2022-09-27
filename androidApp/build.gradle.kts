plugins {
    id("com.android.application")
    id("kotlin-parcelize")
    kotlin("android")
}

android {
    namespace = "com.muralex.multiplatform.android"
    compileSdk = 33
    defaultConfig {
        applicationId = "com.muralex.multiplatform.android"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.0"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    implementation(project(":shared"))
    implementation("androidx.compose.ui:ui:1.2.1")
    implementation("androidx.compose.ui:ui-tooling:1.2.1")
    implementation("androidx.compose.ui:ui-tooling-preview:1.2.1")
    implementation("androidx.compose.foundation:foundation:1.2.1")
    implementation("androidx.activity:activity-compose:1.6.0")

    implementation ("androidx.window:window:1.0.0")
    implementation("androidx.lifecycle:lifecycle-process:2.5.1")
    implementation("androidx.lifecycle:lifecycle-common-java8:2.5.1")
    implementation ("androidx.compose.material:material-icons-extended:1.2.1")


    implementation("androidx.compose.material:material:1.3.0-beta03")

    implementation ("androidx.compose.material3:material3:1.0.0-beta03")
    implementation("androidx.compose.material3:material3-window-size-class:1.0.0-beta03")

    implementation ("com.google.accompanist:accompanist-coil:0.12.0")
    implementation("io.coil-kt:coil-compose:2.1.0")
    implementation("io.coil-kt:coil:2.1.0")

    implementation ("com.google.accompanist:accompanist-webview:0.26.3-beta")
    implementation ("androidx.core:core-splashscreen:1.0.0")


}