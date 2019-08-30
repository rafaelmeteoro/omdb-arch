import configs.KotlinConfig

// Versions for project parameters and forEachDependency
object Libraries {
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${KotlinConfig.version}"
    const val kotlinSerialization = "org.jetbrains.kotlinx:kotlinx-serialization-runtime:${Versions.kotlinSerialization}"

    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
    const val okhttpLogger = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitScalars = "com.squareup.retrofit2:converter-scalars:${Versions.retrofitScalars}"
    const val retrofitKotlinSerialization =
        "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Versions.retrofitKotlinSerialization}"

    const val retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofitGsonConverter}"
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val retrofitRxAdapter = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofitRxAdapter}"

    const val rxJava = "io.reactivex.rxjava2:rxjava:${Versions.rxJava}"
    const val rxKotlin = "io.reactivex.rxjava2:rxkotlin:${Versions.rxKotlin}"
    const val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid}"

    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val cardView = "androidx.cardview:cardview:${Versions.cardView}"
    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
    const val materialDesign = "com.google.android.material:material:${Versions.materialDesign}"
    const val coreAndroidx = "androidx.core:core-ktx:${Versions.coreAndroidx}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val lifecycleCommon = "androidx.lifecycle:lifecycle-common:${Versions.lifecycle}"
    const val lifecycleJava8 = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle}"
    const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
    const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"

    const val navigationFragmentKtx = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    const val navigationRuntimeKtx = "androidx.navigation:navigation-runtime-ktx:${Versions.navigation}"
    const val navigationCommontKtx = "androidx.navigation:navigation-common-ktx:${Versions.navigation}"

    const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    const val daggerAndroid = "com.google.dagger:dagger-android:${Versions.dagger}"
    const val daggerAndroidSupport = "com.google.dagger:dagger-android-support:${Versions.dagger}"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    const val daggerAndroidProcessor = "com.google.dagger:dagger-android-processor:${Versions.dagger}"

    const val leakCanary = "com.squareup.leakcanary:leakcanary-android:${Versions.leakyCanary}"

    val picasso = "com.squareup.picasso:picasso:${Versions.picasso}"

    const val jUnit = "junit:junit:${Versions.junit}"
    const val assertj = "org.assertj:assertj-core:${Versions.assertj}"
    const val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockitoKotlin}"
    const val androidTestRunner = "androidx.test:runner:${Versions.androidxTest}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val mockWebServer = "com.squareup.okhttp3:mockwebserver:${Versions.okHttp}"
    const val roboletric = "org.robolectric:robolectric:${Versions.roboletric}"

    private object Versions {
        const val kotlinSerialization = "0.11.0"
        const val okHttp = "4.1.0"
        const val retrofit = "2.6.1"
        const val retrofitScalars = "2.6.1"
        const val retrofitKotlinSerialization = "0.4.0"
        const val retrofitGsonConverter = "2.6.1"
        const val retrofitRxAdapter = "2.6.1"
        const val rxJava = "2.2.12"
        const val rxKotlin = "2.4.0"
        const val rxAndroid = "2.1.1"
        const val gson = "2.8.5"
        const val appCompat = "1.0.2"
        const val cardView = "1.0.0"
        const val recyclerView = "1.0.0"
        const val materialDesign = "1.0.0"
        const val coreAndroidx = "1.0.2"
        const val constraintLayout = "1.1.3"
        const val lifecycle = "2.2.0-alpha03"
        const val navigation = "2.2.0-alpha01"
        const val dagger = "2.24"
        const val leakyCanary = "2.0-beta-3"
        const val picasso = "2.71828"
        const val junit = "4.12"
        const val assertj = "3.12.2"
        const val mockitoKotlin = "2.1.0"
        const val androidxTest = "1.2.0"
        const val espresso = "3.2.0"
        const val roboletric = "4.3"
    }
}