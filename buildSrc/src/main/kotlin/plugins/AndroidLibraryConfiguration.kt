package plugins

import Versioning
import com.android.build.gradle.BaseExtension
import configs.ProguardConfig
import configs.AndroidConfig
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.internal.AndroidExtensionsExtension

fun Project.configureAsAndroidLibrary() {
    val android = extensions.findByName("android") as BaseExtension

    android.apply {
        compileSdkVersion(AndroidConfig.compileSdk)
        buildToolsVersion(AndroidConfig.buildToolsVersion)

        defaultConfig {
            minSdkVersion(AndroidConfig.minSdk)
            targetSdkVersion(AndroidConfig.targetSdk)
            versionCode = Versioning.version.code
            versionName = Versioning.version.name
            testInstrumentationRunner = AndroidConfig.instrumentationTestRunner

            vectorDrawables.apply {
                useSupportLibrary = true
                generatedDensities(*(AndroidConfig.noGeneratedDesities))
            }
        }

        buildTypes {
            getByName("debug") {
                isTestCoverageEnabled = true
                isMinifyEnabled = false
            }

            getByName("release") {
                isTestCoverageEnabled = false
                isMinifyEnabled = true

                val proguardConfig = ProguardConfig("$rootDir/proguard")
                proguardFiles(*(proguardConfig.customRules))
                proguardFiles(getDefaultProguardFile(proguardConfig.androidRules))
            }
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }

        lintOptions {
            baseline(file("$rootDir/buildSrc/config/lint.xml"))
        }

        testOptions {
            unitTests.isReturnDefaultValues = true
            unitTests.isIncludeAndroidResources = true
        }

        buildFeatures.apply {
            viewBinding = true
        }
    }
}

fun Project.configureAndroidExtensions() {
    val androidExtensions = extensions.findByName("androidExtensions") as AndroidExtensionsExtension

    androidExtensions.apply {
        isExperimental = true
    }
}
