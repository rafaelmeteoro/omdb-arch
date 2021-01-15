import configs.KotlinConfig

// Versions for project parameters and forEachDependency
object Libraries {
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${KotlinConfig.version}"
    const val kotlinSerialization =
        "org.jetbrains.kotlinx:kotlinx-serialization-runtime:${Versions.kotlinSerialization}"

    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
    const val okhttpLogger = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitScalars = "com.squareup.retrofit2:converter-scalars:${Versions.retrofit}"
    const val retrofitKotlinSerialization =
        "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Versions.retrofitKotlinSerialization}"

    const val retrofitGsonConverter =
        "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val retrofitRxAdapter = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"

    const val rxJava = "io.reactivex.rxjava2:rxjava:${Versions.rxJava}"
    const val rxKotlin = "io.reactivex.rxjava2:rxkotlin:${Versions.rxKotlin}"
    const val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid}"

    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val cardView = "androidx.cardview:cardview:${Versions.cardView}"
    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
    const val materialDesign = "com.google.android.material:material:${Versions.materialDesign}"
    const val coreAndroidx = "androidx.core:core-ktx:${Versions.coreAndroidx}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val lifecycleCompiler = "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycle}"
    const val lifecycleCommon = "androidx.lifecycle:lifecycle-common:${Versions.lifecycle}"
    const val lifecycleJava8 = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle}"
    const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
    const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"

    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
    const val coroutinesDebug = "org.jetbrains.kotlinx:kotlinx-coroutines-debug:${Versions.coroutines}"

    const val navigationFragmentKtx = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    const val navigationRuntimeKtx = "androidx.navigation:navigation-runtime-ktx:${Versions.navigation}"
    const val navigationCommontKtx = "androidx.navigation:navigation-common-ktx:${Versions.navigation}"

    const val archCoreCommon = "androidx.arch.core:core-common:${Versions.archCore}"
    const val archCoreRuntime = "androidx.arch.core:core-runtime:${Versions.archCore}"
    const val archCoreTesting = "androidx.arch.core:core-testing:${Versions.archCore}"

    const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
    const val roomRxJava2 = "androidx.room:room-rxjava2:${Versions.room}"
    const val roomTesting = "androidx.room:room-testing:${Versions.room}"

    const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    const val daggerAndroid = "com.google.dagger:dagger-android:${Versions.dagger}"
    const val daggerAndroidSupport = "com.google.dagger:dagger-android-support:${Versions.dagger}"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    const val daggerAndroidProcessor = "com.google.dagger:dagger-android-processor:${Versions.dagger}"

    const val leakCanary = "com.squareup.leakcanary:leakcanary-android:${Versions.leakyCanary}"

    const val picasso = "com.squareup.picasso:picasso:${Versions.picasso}"
    const val coil = "io.coil-kt:coil:${Versions.coil}"
    const val coilBase = "io.coil-kt:coil-base:${Versions.coil}"
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    const val stetho = "com.facebook.stetho:stetho:${Versions.stetho}"
    const val stethoOkHttp = "com.facebook.stetho:stetho-okhttp3:${Versions.stetho}"

    const val jUnit = "junit:junit:${Versions.junit}"
    const val jUnitExt = "androidx.test.ext:junit:${Versions.junitExt}"
    const val jUnitExtKtx = "androidx.test.ext:junit-ktx:${Versions.junitExt}"
    const val assertj = "org.assertj:assertj-core:${Versions.assertj}"
    const val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockitoKotlin}"
    const val mockitoAndroid = "org.mockito:mockito-android:${Versions.mockitoAndroid}"
    const val androidTestCore = "androidx.test:core:${Versions.androidxTest}"
    const val androidTestCoreKtx = "androidx.test:core-ktx:${Versions.androidxTest}"
    const val androidTestRunner = "androidx.test:runner:${Versions.androidxTest}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val mockWebServer = "com.squareup.okhttp3:mockwebserver:${Versions.okHttp}"
    const val roboletric = "org.robolectric:robolectric:${Versions.roboletric}"
    const val barista = "com.schibsted.spain:barista:${Versions.barista}"

    private object Versions {
        const val kotlinSerialization = "0.20.0"
        const val okHttp = "4.9.0"
        const val retrofit = "2.9.0"
        const val retrofitKotlinSerialization = "0.5.0"
        const val rxJava = "2.2.20"
        const val rxKotlin = "2.4.0"
        const val rxAndroid = "2.1.1"
        const val gson = "2.8.6"
        const val appCompat = "1.2.0"
        const val cardView = "1.0.0"
        const val recyclerView = "1.1.0"
        const val materialDesign = "1.2.0"
        const val coreAndroidx = "1.3.2"
        const val archCore = "2.1.0"
        const val constraintLayout = "2.0.1"
        const val lifecycle = "2.2.0"
        const val coroutines = "1.3.9"
        const val navigation = "2.3.2"
        const val room = "2.2.6"
        const val dagger = "2.31"
        const val leakyCanary = "2.5"
        const val picasso = "2.71828"
        const val coil = "0.13.0"
        const val timber = "4.7.1"
        const val stetho = "1.5.1"
        const val junit = "4.13.1"
        const val junitExt = "1.1.2"
        const val assertj = "3.18.1"
        const val mockitoKotlin = "2.2.0"
        const val mockitoAndroid = "3.7.0"
        const val androidxTest = "1.3.0"
        const val espresso = "3.3.0"
        const val roboletric = "4.3"
        const val barista = "3.7.0"
    }
}
