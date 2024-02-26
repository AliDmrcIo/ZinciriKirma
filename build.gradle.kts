buildscript {
    repositories {
        google()
        mavenCentral()

    }

    dependencies {
        val nav_version = "2.7.4"
        //noinspection GradleDependency
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")
    }
}
allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

plugins {
    id("com.android.application") version "8.1.0" apply false
}

