import configs.KotlinConfig

object BuildPlugins {

    object Dependencies {
        const val androidSupport = "com.android.tools.build:gradle:${Versions.agp}"
        const val kotlinSupport = "org.jetbrains.kotlin:kotlin-gradle-plugin:${KotlinConfig.version}"
        const val testLogger = "com.adarshr:gradle-test-logger-plugin:${Versions.testLogger}"
        const val kotlinxSerialization = "org.jetbrains.kotlin:kotlin-serialization:${KotlinConfig.version}"
        const val versions = "com.github.ben-manes:gradle-versions-plugin:${Versions.versions}"
        const val safeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.safeArgs}"
        const val detekt = "io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${Versions.detekt}"
    }

    object Ids {
        const val androidApplication = "com.android.application"
        const val androidLibrary = "com.android.library"
        const val kotlinAndroid = "android"
        const val kotlinExtensions = "android.extensions"
        const val kotlinKapt = "kapt"
        const val kotlinJVM = "kotlin"
        const val kotlinxSerialization = "kotlinx-serialization"
        const val testLogger = "com.adarshr.test-logger"
        const val versions = "com.github.ben-manes.versions"
        const val safeArgs = "androidx.navigation.safeargs"
        const val detekt = "io.gitlab.arturbosch.detekt"

        const val kotlinModule = "kotlin-module"
        const val androidModule = "android-module"
    }

    private object Versions {
        const val agp = "4.1.0-rc01"
        const val testLogger = "2.1.0"
        const val versions = "0.29.0"
        const val safeArgs = "2.3.0"
        const val detekt = "1.11.0"
    }
}