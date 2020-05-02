object LibraryDependency {
    const val KOTLIN = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${CoreVersions.KOTLIN}"
    // Required by Android dynamic feature modules and SafeArgs
   // const val KOTLIN_REFLECT = "org.jetbrains.kotlin:kotlin-reflect:${CoreVersions.KOTLIN}"
    const val SUPPORT_CONSTRAINT_LAYOUT =
        "androidx.constraintlayout:constraintlayout:${CoreVersions.CONSTRAINT_LAYOUT}"
    const val APP_COMPACT = "androidx.appcompat:appcompat:${CoreVersions.APP_COMPACT}"
    const val PLAY_SERVICE = "com.google.android.gms:play-services-vision:${CoreVersions.PLAY_SERVICE}"
    const val MATERIAL = "com.google.android.material:material:${CoreVersions.MATERIAL}"
    const val CORE_KTX = "androidx.core:core-ktx:${CoreVersions.CORE_KTX}"
    const val SDP = "com.intuit.sdp:sdp-android:${CoreVersions.SDP}"
    const val VECTOR_DRAWABLE = "androidx.vectordrawable:vectordrawable:${CoreVersions.VECTOR_DRAWABLE}"
    const val LIFECYCLE_EXTENSIONS = "android.arch.lifecycle:extensions:${CoreVersions.LIFECYCLE_EXT}"
//    const val LIFECYCLE_VIEW_MODEL_KTX =
//        "androidx.lifecycle:lifecycle-viewmodel-ktx:${CoreVersions.LIFECYCLE_VIEW_MODEL_KTX}"
    const val NAVIGATION_FRAGMENT_KTX = "androidx.navigation:navigation-fragment-ktx:${CoreVersions.NAVIGATION}"
    const val NAVIGATION_FRAGMENT = "androidx.navigation:navigation-fragment:${CoreVersions.NAVIGATION}"
    const val NAVIGATION_UI = "androidx.navigation:navigation-ui:${CoreVersions.NAVIGATION}"
    const val NAVIGATION_UI_KTX = "androidx.navigation:navigation-ui-ktx:${CoreVersions.NAVIGATION}"
    const val LAGACY = "androidx.legacy:legacy-support-v4:${CoreVersions.LEGACY}"
    const val KOIN = "org.koin:koin-android:${CoreVersions.KOIN}"
    const val KOIN_SCOPE = "org.koin:koin-androidx-scope:${CoreVersions.KOIN}"
    const val KOIN_VIEWMODEL = "org.koin:koin-androidx-viewmodel:${CoreVersions.KOIN}"
    const val RETROFIT = "com.squareup.retrofit2:retrofit:${CoreVersions.RETROFIT}"
    const val RETROFIT_GSON = "com.squareup.retrofit2:converter-gson:${CoreVersions.RETROFIT_GSON}"
    const val LOGGING_RETROFIT = "com.squareup.okhttp3:logging-interceptor:${CoreVersions.LOGGING}"
    const val StethOkhttp = "com.facebook.stetho:stetho-okhttp3:${CoreVersions.STETHO}"
    const val Stetho = "com.facebook.stetho:stetho:${CoreVersions.STETHO}"
    const val Picasso = "com.squareup.picasso:picasso:2.71828"
    const val zxing = "me.dm7.barcodescanner:zxing:1.9.13"
    const val NETWORK = ":networkapi"


}


