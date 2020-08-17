import configs.AndroidConfig
import configs.KotlinConfig
import configs.ProguardConfig
import dependencies.InstrumentationTestDependencies.Companion.instrumentationTest
import dependencies.UnitTestDependencies.Companion.unitTest
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import plugins.configureDetekt
import plugins.configureKtlint
import plugins.configureTestLogger

plugins {
    id(BuildPlugins.Ids.androidApplication)
    id(BuildPlugins.Ids.kotlinAndroid)
    id(BuildPlugins.Ids.kotlinAndroidExtensions)
    id(BuildPlugins.Ids.kotlinKapt)
    id(BuildPlugins.Ids.testLogger)
    id(BuildPlugins.Ids.ktlint)
    id(BuildPlugins.Ids.detekt)
}

configureTestLogger()
configureKtlint()
configureDetekt()

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

        multiDexEnabled = true

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

            buildConfigField("String", "API_KEY_VALUE", "\"1abc75a6\"")
            buildConfigField("String", "API_URL", "\"https://www.omdbapi.com/\"")
        }
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true

            buildConfigField("String", "API_KEY_VALUE", "\"1abc75a6\"")
            buildConfigField("String", "API_URL", "\"https://www.omdbapi.com/\"")

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

    buildFeatures {
        viewBinding = true
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = KotlinConfig.targetJVM
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
        unitTests.isIncludeAndroidResources = true
    }
}

dependencies {
    implementation(Libraries.kotlinStdLib)
    implementation(Libraries.appCompat)
    implementation(Libraries.cardView)
    implementation(Libraries.recyclerView)
    implementation(Libraries.materialDesign)
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
    implementation(Libraries.picasso)
    implementation(Libraries.coil)
    implementation(Libraries.coilBase)
    implementation(Libraries.stetho)
    implementation(Libraries.stethoOkHttp)
    implementation(Libraries.dagger)
    implementation(Libraries.daggerAndroid)
    implementation(Libraries.roomRuntime)
    implementation(Libraries.roomKtx)
    implementation(Libraries.roomRxJava2)
    implementation(Libraries.timber)
    kapt(Libraries.daggerCompiler)
    kapt(Libraries.daggerAndroidProcessor)
    kapt(Libraries.roomCompiler)

    debugImplementation(Libraries.leakCanary)

    implementation(project(":domain"))
    implementation(project(":libraries:rest-omdb"))
    implementation(project(":libraries:persistance"))
    implementation(project(":libraries:ui-components"))
    implementation(project(":libraries:actions"))
    implementation(project(":features:onboarding"))
    implementation(project(":features:home"))
    implementation(project(":features:details"))
    implementation(project(":features:favorites"))
    implementation(project(":libraries:unidirectional-dataflow"))

    unitTest { forEachDependency { testImplementation(it) } }
    instrumentationTest { forEachDependency { androidTestImplementation(it) } }
}

androidExtensions {
    extensions.add("experimental", true)
}