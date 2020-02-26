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
        const val versions = "com.github.ben-manes:gradle-versions-plugin:${Versions.versions}"
        const val safeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.safeArgs}"
        const val realm = "io.realm:realm-gradle-plugin:${Versions.realm}"
        const val moduleGraph =
            "https://raw.githubusercontent.com/JakeWharton/SdkSearch/master/gradle/projectDependencyGraph.gradle"
    }

    object Ids {
        const val androidApplication = "com.android.application"
        const val androidLibrary = "com.android.library"
        const val kotlinAndroid = "android"
        const val kotlinExtensions = "android.extensions"
        const val kotlinKapt = "kapt"
        const val kotlinJVM = "kotlin"
        const val kotlinxSerialization = "kotlinx-serialization"
        const val cobertura = "net.saliman.cobertura"
        const val coveralls = "com.github.kt3k.coveralls"
        const val testLogger = "com.adarshr.test-logger"
        const val coberturaVersion = "2.6.1"
        const val coverallsVersion = "2.8.4"
        const val jacocoUnified = "com.vanniktech.android.junit.jacoco"
        const val sonarCloud = "org.sonarqube"
        const val detekt = "io.gitlab.arturbosch.detekt"
        const val versions = "com.github.ben-manes.versions"
        const val safeArgs = "androidx.navigation.safeargs"
        const val realmAndroid = "realm-android"
    }

    private object Versions {
        const val agp = "3.6.0"
        const val ktlint = "8.2.0"
        const val cobertura = "3.0.0"
        const val coveralls = "2.8.4"
        const val testLogger = "2.0.0"
        const val jacocoUnified = "0.15.0"
        const val sonarCloud = "2.8"
        const val detekt = "1.5.1"
        const val versions = "0.28.0"
        const val safeArgs = "2.2.1"
        const val realm = "6.1.0"
    }
}