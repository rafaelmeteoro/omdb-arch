import com.android.build.gradle.internal.dsl.DataBindingOptions
import configs.AndroidConfig
import configs.KotlinConfig
import configs.ProguardConfig
import dependencies.InstrumentationTestsDependencies.Companion.instrumentationTest
import dependencies.UnitTestDependencies.Companion.unitTest
import modules.ModuleNames
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id(BuildPlugins.Ids.androidApplication)
    kotlin(BuildPlugins.Ids.kotlinAndroid)
    kotlin(BuildPlugins.Ids.kotlinExtensions)
    kotlin(BuildPlugins.Ids.kotlinKapt)
}

android {
    compileSdkVersion(AndroidConfig.compileSdk)
    buildToolsVersion(AndroidConfig.buildToolsVersion)

    defaultConfig {
        minSdkVersion(AndroidConfig.minSdk)
        targetSdkVersion(AndroidConfig.targetSdk)

        applicationId = AndroidConfig.applicationId
        testInstrumentationRunner = AndroidConfig.instrumentationTestRunner
        versionCode = Versioning.version.code
        versionName = Versioning.version.name

        vectorDrawables.apply {
            useSupportLibrary = true
            generatedDensities(*(AndroidConfig.noGeneratedDesities))
        }
    }

    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-DEBUG"
            isTestCoverageEnabled = false
        }
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true

            val proguardConfig = ProguardConfig("$rootDir/proguard")
            proguardFiles(*(proguardConfig.customRules))
            proguardFiles(getDefaultProguardFile(proguardConfig.androidRules))
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = KotlinConfig.targetJVM
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
        unitTests.isIncludeAndroidResources = true
    }

    val action = object : Action<DataBindingOptions> {
        override fun execute(t: DataBindingOptions) {
            t.isEnabled = true
        }
    }
    dataBinding(action)
}

dependencies {
    implementation(Libraries.kotlinStdLib)
    implementation(Libraries.kotlinSerialization)
    implementation(Libraries.appCompat)
    implementation(Libraries.coreAndroidx)
    implementation(Libraries.constraintLayout)
    implementation(Libraries.lifecycleCommon)
    implementation(Libraries.lifecycleJava8)
    implementation(Libraries.lifecycleViewModel)
    implementation(Libraries.lifecycleExtensions)
    implementation(Libraries.rxJava)
    implementation(Libraries.rxKotlin)
    implementation(Libraries.rxAndroid)
    implementation(Libraries.okhttp)
    implementation(Libraries.okhttpLogger)
    implementation(Libraries.retrofit)
    implementation(Libraries.retrofitRxAdapter)
    implementation(Libraries.retrofitScalars)
    implementation(Libraries.retrofitKotlinSerialization)
    implementation(Libraries.retrofitGsonConverter)
    implementation(Libraries.gson)
    implementation(Libraries.kodein)
    implementation(Libraries.kodeinConf)
    implementation(Libraries.kodeinAndroid)
    implementation(project(ModuleNames.Logger))
    implementation(project(ModuleNames.Domain))
    implementation(project(ModuleNames.Infrastructure.Networking))
    implementation(project(ModuleNames.Infrastructure.Rest))
    implementation(project(ModuleNames.Features.SharedUtilities))

    debugImplementation(Libraries.leakCanary)

    unitTest {
        forEachDependency { testImplementation(it) }
    }

    instrumentationTest {
        forEachDependency { androidTestImplementation(it) }
    }
}

androidExtensions {
    extensions.add("experimental", true)
}