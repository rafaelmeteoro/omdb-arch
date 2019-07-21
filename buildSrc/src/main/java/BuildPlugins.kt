import configs.KotlinConfig

object BuildPlugins {

    object Dependencies {
        const val androidSupport = "com.android.tools.build:gradle:${Versions.agp}"
        const val kotlinSupport = "org.jetbrains.kotlin:kotlin-gradle-plugin:${KotlinConfig.version}"
        const val ktlint = "org.jlleitschuh.gradle:ktlint-gradle:${Versions.ktlint}"
        const val cobertura = "net.saliman:gradle-cobertura-plugin:${Versions.cobertura}"
        const val coveralls = "gradle.plugin.com.github.kt3k.coveralls:coveralls-gradle-plugin:${Versions.coveralls}"
        const val testLogger = "com.adarshr:gradle-test-logger-plugin:${Versions.testLogger}"
        const val kotlinxSerialization = "org.jetbrains.kotlin:kotlin-serialization:${KotlinConfig.version}"
        const val jacocoUnified = "com.vanniktech:gradle-android-junit-jacoco-plugin:${Versions.jacocoUnified}"
        const val sonarCloud = "org.sonarsource.scanner.gradle:sonarqube-gradle-plugin:${Versions.sonarCloud}"
        const val detekt = "io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${Versions.detekt}"
    }

    object Ids {
        const val androidApplication = "com.android.application"
        const val kotlinAndroid = "android"
        const val kotlinExtensions = "android.extensions"
        const val kotlinKapt = "kapt"
        const val kotlinJVM = "kotlin"
        const val cobertura = "net.saliman.cobertura"
        const val coveralls = "com.github.kt3k.coveralls"
        const val testLogger = "com.adarshr.test-logger"
        const val coberturaVersion = "2.6.1"
        const val coverallsVersion = "2.8.4"
        const val jacocoUnified = "com.vanniktech.android.junit.jacoco"
        const val sonarCloud = "org.sonarqube"
        const val detekt = "io.gitlab.arturbosch.detekt"
    }

    private object Versions {
        const val agp = "3.4.2"
        const val ktlint = "8.1.0"
        const val cobertura = "2.6.1"
        const val coveralls = "2.8.4"
        const val testLogger = "1.7.0"
        const val jacocoUnified = "0.15.0"
        const val sonarCloud = "2.7.1"
        const val detekt = "1.0.0-RC16"
    }
}