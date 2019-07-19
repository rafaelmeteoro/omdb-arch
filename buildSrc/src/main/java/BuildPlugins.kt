import configs.KotlinConfig

object BuildPlugins {

    object Dependencies {
        const val androidSupport = "com.android.tools.build:gradle:${Versions.agp}"
        const val kotlinSupport = "org.jetbrains.kotlin:kotlin-gradle-plugin:${KotlinConfig.version}"
        const val ktlint = "org.jlleitschuh.gradle:ktlint-gradle:${Versions.ktlint}"
        const val cobertura = "net.saliman:gradle-cobertura-plugin:${Versions.cobertura}"
        const val coveralls = "gradle.plugin.com.github.kt3k.coveralls:coveralls-gradle-plugin:${Versions.coveralls}"
    }

    object Ids {
        const val androidApplication = "com.android.application"
        const val kotlinAndroid = "android"
        const val kotlinExtensions = "android.extensions"
        const val kotlinKapt = "kapt"
        const val cobertura = "net.saliman.cobertura"
        const val coveralls = "com.github.kt3k.coveralls"
        const val coberturaVersion = "2.6.1"
        const val coverallsVersion = "2.8.4"
    }

    private object Versions {
        const val agp = "3.4.2"
        const val ktlint = "8.1.0"
        const val cobertura = "2.6.1"
        const val coveralls = "2.8.4"
    }
}