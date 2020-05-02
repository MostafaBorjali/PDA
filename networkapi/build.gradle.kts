plugins {
    id(PluginObject.ANDROID_LIBRARY)
    id(PluginObject.KOTLIN_ANDROID)
    id(PluginObject.KAPT)
    id(PluginObject.EXTENTION)

}

android {

    compileSdkVersion(AndroidConfig.COMPILE_SDK_VERSION)
    defaultConfig {
        minSdkVersion(AndroidConfig.MIN_SDK_VERSION)
        targetSdkVersion(AndroidConfig.TARGET_SDK_VERSION)
        versionCode = AndroidConfig.VERSION_CODE
        versionName = AndroidConfig.VERSION_NAME


    }
    buildTypes {
        getByName(BuildType.RELEASE) {
            isMinifyEnabled = BuildTypeRelease.isMinifyEnabled
            proguardFiles("proguard-android.txt", "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        val options = this as? org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
        options?.jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

}

dependencies {
    implementation(LibraryDependency.KOTLIN)
    implementation(LibraryDependency.CORE_KTX)
    implementation(LibraryDependency.KOIN)
    implementation(LibraryDependency.KOIN_SCOPE)
    implementation(LibraryDependency.KOIN_VIEWMODEL)
    implementation(LibraryDependency.RETROFIT)
    implementation(LibraryDependency.RETROFIT_GSON)
    implementation(LibraryDependency.LOGGING_RETROFIT)
    implementation(LibraryDependency.Stetho)
    implementation(LibraryDependency.StethOkhttp)


}