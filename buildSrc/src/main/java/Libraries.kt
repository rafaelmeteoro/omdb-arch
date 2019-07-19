import configs.KotlinConfig

// Versions for project parameters and forEachDependency
object Libraries {
    val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${KotlinConfig.version}"

    val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    val coreAndroidx = "androidx.core:core-ktx:${Versions.coreAndroidx}"
    val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"

    val jUnit = "junit:junit:${Versions.junit}"
    val androidTestRunner = "androidx.test:runner:${Versions.androidxTest}"
    val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"

    private object Versions {
        const val appCompat = "1.0.2"
        const val coreAndroidx = "1.0.2"
        const val constraintLayout = "1.1.3"
        const val junit = "4.12"
        const val androidxTest = "1.2.0"
        const val espresso = "3.2.0"
    }
}