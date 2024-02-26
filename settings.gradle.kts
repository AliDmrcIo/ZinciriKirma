pluginManagement {

    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        jcenter(){
            content {
                includeModule("com.theartofdev.edmodo","android-image-cropper")
            }
        }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS) //burayı FAIL_ON_PROJECT_REPOS 'den RepositoriesMode.PREFER_SETTINGS'e çevirdim gradle project'e all project google'ı ekledikten sonra hata aldığım için
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")

        jcenter(){
            content {
                includeModule("com.theartofdev.edmodo","android-image-cropper")
            }
        }

    }
}

rootProject.name = "DontBreakTheChain"
include(":app")
 