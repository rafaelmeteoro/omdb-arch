import configs.AndroidConfig
import configs.KotlinConfig
import configs.ProguardConfig
import dependencies.ModulesDependencies.Companion.moduleDependencies
import modules.LibraryModule
import modules.LibraryType
import modules.ModuleNames
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val dependencyGraph = LibraryModule(rootDir, LibraryType.DependencyGraph)
val ktlintModule = LibraryModule(rootDir, LibraryType.KtLint)
val detektModule = LibraryModule(rootDir, LibraryType.Detekt)

plugins {
    id(BuildPlugins.Ids.androidApplication)
    kotlin(BuildPlugins.Ids.kotlinAndroid)
    kotlin(BuildPlugins.Ids.kotlinExtensions)
    kotlin(BuildPlugins.Ids.kotlinKapt)
    id(BuildPlugins.Ids.realmAndroid)
}

apply(from = dependencyGraph.script())
apply(from = ktlintModule.script())
apply(from = detektModule.script())

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
        setLintConfig(file("${rootDir}/buildSrc/config/lint.xml"))
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
    moduleDependencies {
        forEachDependencies(app) { implementation(it) }
        forEachCompilers(app) { kapt(it) }
        forEachTestDependencies(app) { testImplementation(it) }
        forEachAndroidTestDependencies(app) { androidTestImplementation(it) {} }
    }

    debugImplementation(Libraries.leakCanary)

    implementation(project(ModuleNames.Domain))
    implementation(project(ModuleNames.Libraries.Rest))
    implementation(project(ModuleNames.Libraries.Persistance))
    implementation(project(ModuleNames.Libraries.UiComponents))
    implementation(project(ModuleNames.Libraries.Actions))
    implementation(project(ModuleNames.Features.Onboarding))
    implementation(project(ModuleNames.Features.Home))
    implementation(project(ModuleNames.Features.Details))
    implementation(project(ModuleNames.Features.Favorites))
    implementation(project(ModuleNames.Libraries.Architecture))
}

androidExtensions {
    extensions.add("experimental", true)
}