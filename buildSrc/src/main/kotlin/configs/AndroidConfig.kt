package configs

object AndroidConfig {
    const val applicationId = "com.meteoro.omdbarch"

    const val compileSdk = 29
    const val minSdk = 24
    const val targetSdk = compileSdk

    const val buildToolsVersion = "30.0.2"

    const val instrumentationTestRunner = "androidx.test.runner.AndroidJUnitRunner"

    val noGeneratedDesities = emptySet<String>().toTypedArray()
}
