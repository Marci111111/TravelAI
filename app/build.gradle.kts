import java.util.Properties
import java.io.FileInputStream
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
}



// Carica la chiave da secrets.properties
val secretsFile = rootProject.file("secrets.properties")
val secrets = Properties()
if (secretsFile.exists()) {
    secrets.load(FileInputStream(secretsFile))
}

android {
    namespace = "it.uniparthenope.marcelomirra.travelai"
    compileSdk = 34

    defaultConfig {
        applicationId = "it.uniparthenope.marcelomirra.travelai"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        // Inserisce OPENAI_API_KEY nella BuildConfig
        buildConfigField("String", "OPENAI_API_KEY", "\"${secrets.getProperty("openaikey")}\"")
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.10.0")
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation("androidx.compose.ui:ui:1.4.3")
    implementation("androidx.compose.material:material:1.4.3")
    implementation("androidx.compose.ui:ui-tooling-preview:1.4.3")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
    implementation("io.ktor:ktor-client-okhttp:2.3.9")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")
    debugImplementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

    implementation("com.aallam.openai:openai-client:3.4.0")

    debugImplementation("androidx.compose.ui:ui-tooling:1.4.3")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.4.3")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
