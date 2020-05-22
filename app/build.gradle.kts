import configs.AndroidConfig
import configs.KotlinConfig
import configs.ProguardConfig
import dependencies.InstrumentationTestsDependencies.Companion.instrumentationTest
import dependencies.UnitTestDependencies.Companion.unitTest
import modules.LibraryModule
import modules.LibraryType
import modules.ModuleNames
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val dependencyGraph = LibraryModule(rootDir, LibraryType.DependencyGraph)
val ktlintModule = LibraryModule(rootDir, LibraryType.KtLint)

plugins {
    id(BuildPlugins.Ids.androidApplication)
    kotlin(BuildPlugins.Ids.kotlinAndroid)
    kotlin(BuildPlugins.Ids.kotlinExtensions)
    kotlin(BuildPlugins.Ids.kotlinKapt)
    id(BuildPlugins.Ids.realmAndroid)
}

apply(from = dependencyGraph.script())
apply(from = ktlintModule.script())

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
        isAbortOnError = false
    }

    viewBinding {
        isEnabled = true
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
    implementation(Libraries.kotlinSerialization)
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
    implementation(Libraries.daggerAndroidSupport)
    kapt(Libraries.daggerCompiler)
    kapt(Libraries.daggerAndroidProcessor)
    implementation(Libraries.roomRuntime)
    implementation(Libraries.roomKtx)
    implementation(Libraries.roomRxJava2)
    kapt(Libraries.roomCompiler)

    implementation(project(ModuleNames.Domain))
    implementation(project(ModuleNames.Libraries.Logger))
    implementation(project(ModuleNames.Libraries.Rest))
    implementation(project(ModuleNames.Libraries.Persistance))
    implementation(project(ModuleNames.Libraries.UiComponents))
    implementation(project(ModuleNames.Libraries.Actions))
    implementation(project(ModuleNames.Features.Onboarding))
    implementation(project(ModuleNames.Features.Home))
    implementation(project(ModuleNames.Features.Details))
    implementation(project(ModuleNames.Features.Favorites))

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