import configs.KotlinConfig

// Versions for project parameters and forEachDependency
object Libraries {
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${KotlinConfig.version}"
    const val kotlinSerialization =
        "org.jetbrains.kotlinx:kotlinx-serialization-runtime:${Versions.kotlinSerialization}"

    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
    const val okhttpLogger = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitScalars = "com.squareup.retrofit2:converter-scalars:${Versions.retrofitScalars}"
    const val retrofitKotlinSerialization =
        "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Versions.retrofitKotlinSerialization}"

    const val retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofitGsonConverter}"
    const val gson = "com.google.code.gson:gson:${Versions.gson}"

    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val coreAndroidx = "androidx.core:core-ktx:${Versions.coreAndroidx}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"

    const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"

    const val jUnit = "junit:junit:${Versions.junit}"
    const val assertj = "org.assertj:assertj-core:${Versions.assertj}"
    const val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockitoKotlin}"
    const val androidTestRunner = "androidx.test:runner:${Versions.androidxTest}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"

    private object Versions {
        const val kotlinSerialization = "0.11.0"
        const val okHttp = "4.0.1"
        const val retrofit = "2.6.0"
        const val retrofitScalars = "2.0.2"
        const val retrofitKotlinSerialization = "0.4.0"
        const val retrofitGsonConverter = "2.5.0"
        const val gson = "2.8.5"
        const val appCompat = "1.0.2"
        const val coreAndroidx = "1.0.2"
        const val constraintLayout = "1.1.3"
        const val dagger = "2.22.1"
        const val junit = "4.12"
        const val assertj = "3.12.2"
        const val mockitoKotlin = "2.1.0"
        const val androidxTest = "1.2.0"
        const val espresso = "3.2.0"
    }
}