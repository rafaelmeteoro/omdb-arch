import configs.AndroidConfig
import configs.KotlinConfig
import configs.ProguardConfig
import dependencies.ModulesDependencies.Companion.moduleDependencies
import modules.LibraryModule
import modules.LibraryType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val dependencyGraph = LibraryModule(rootDir, LibraryType.DependencyGraph)
val ktlintModule = LibraryModule(rootDir, LibraryType.KtLint)
val detektModule = LibraryModule(rootDir, LibraryType.Detekt)

plugins {
    id(BuildPlugins.Ids.androidApplication)
    kotlin(BuildPlugins.Ids.kotlinAndroid)
    kotlin(BuildPlugins.Ids.kotlinExtensions)
    kotlin(BuildPlugins.Ids.kotlinKapt)
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
//        setLintConfig(file("${rootDir}/buildSrc/config/lint.xml"))
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
    moduleDependencies {
        forEachDependencies(app) { implementation(it) }
        forEachCompilers(app) { kapt(it) }
        forEachTestDependencies(app) { testImplementation(it) }
        forEachAndroidTestDependencies(app) { androidTestImplementation(it) {} }
    }

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
}

androidExtensions {
    extensions.add("experimental", true)
}