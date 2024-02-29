plugins {
    id("com.android.application")
    id("androidx.navigation.safeargs")

}

android {
    namespace = "com.alidemirci.dontbreakthechain"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.alidemirci.dontbreakthechain"
        minSdk = 24
        targetSdk = 34
        versionCode = 4 //burayı google sürüme atarken sorun çıktığından yaptım
        versionName = "4.0"

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
    
    buildFeatures {
        viewBinding = true
    }

}
val nav_version = "2.7.5"
val work_version = "2.9.0"

dependencies {
    implementation ("com.google.guava:guava:29.0-android") //bunu ekledim sonradan class file for com.google.common.util.concurrent.ListenableFuture not found hatasını alınca ve çözüldü

    implementation("androidx.work:work-runtime:$work_version") //work manager

    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")

    implementation("com.github.prolificinteractive:material-calendarview:2.0.1")


    implementation ("com.github.dhaval2404:imagepicker:2.1")
    implementation ("androidx.fragment:fragment-ktx:1.6.2")

    implementation ("com.theartofdev.edmodo:android-image-cropper:2.8.0")
    implementation ("com.squareup.picasso:picasso:2.5.2")

    implementation ("com.google.android.gms:play-services-ads:22.6.0")

    implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.8.0"))

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}