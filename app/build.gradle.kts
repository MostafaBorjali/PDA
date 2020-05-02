import org.gradle.internal.impldep.aQute.bnd.osgi.Constants.options

plugins {
    id(PluginObject.ANDROID)
    id(PluginObject.KOTLIN_ANDROID)
    id(PluginObject.KAPT)
    id(PluginObject.EXTENTION)
    id(PluginObject.OBJECTBOX)
}

android {
    compileSdkVersion(AndroidConfig.COMPILE_SDK_VERSION)
    buildToolsVersion(AndroidConfig.BUILD_TOOLS_VERSION)
    defaultConfig {
        applicationId = AndroidConfig.ID
        minSdkVersion(AndroidConfig.MIN_SDK_VERSION)
        targetSdkVersion(AndroidConfig.TARGET_SDK_VERSION)
        multiDexEnabled = AndroidConfig.MULTIDEXENABLED
        versionCode = AndroidConfig.VERSION_CODE
        versionName = AndroidConfig.VERSION_NAME
        testInstrumentationRunner = AndroidConfig.TEST_INSTRUMENTATION_RUNNER
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

    dataBinding {
        isEnabled = true
    }

    lintOptions {
        // By default lint does not check test sources, but setting this option means that lint will not even parse them
        isIgnoreTestSources = true
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(LibraryDependency.KOTLIN)
    implementation(LibraryDependency.APP_COMPACT)
    implementation(LibraryDependency.CORE_KTX)
    implementation(LibraryDependency.MATERIAL)
    implementation(LibraryDependency.SDP)
    implementation(LibraryDependency.VECTOR_DRAWABLE)
    implementation(LibraryDependency.SUPPORT_CONSTRAINT_LAYOUT)
    implementation(LibraryDependency.NAVIGATION_FRAGMENT)
    implementation(LibraryDependency.NAVIGATION_FRAGMENT_KTX)
    implementation(LibraryDependency.NAVIGATION_UI)
    implementation(LibraryDependency.NAVIGATION_UI_KTX)
    implementation(LibraryDependency.LIFECYCLE_EXTENSIONS)
    implementation(LibraryDependency.LAGACY)
    implementation(LibraryDependency.KOIN)
    implementation(LibraryDependency.KOIN_SCOPE)
    implementation(LibraryDependency.KOIN_VIEWMODEL)
    implementation(LibraryDependency.Stetho)
    implementation(LibraryDependency.StethOkhttp)
    implementation(LibraryDependency.Picasso)
    implementation(LibraryDependency.zxing)
    implementation(project(path = LibraryDependency.NETWORK))
    api(LibraryDependency.PLAY_SERVICE)

}
