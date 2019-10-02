import dependencies.InstrumentationTestsDependencies.Companion.instrumentationTest
import dependencies.UnitTestDependencies.Companion.unitTest
import modules.LibraryModule
import modules.LibraryType
import modules.ModuleNames

val module = LibraryModule(rootDir, LibraryType.Android)

apply(from = module.script())

plugins {
    id(BuildPlugins.Ids.androidLibrary)
    kotlin(BuildPlugins.Ids.kotlinAndroid)
    kotlin(BuildPlugins.Ids.kotlinExtensions)
    kotlin(BuildPlugins.Ids.kotlinKapt)
    id(BuildPlugins.Ids.realmAndroid)
}

dependencies {
    implementation(Libraries.kotlinStdLib)
    implementation(Libraries.rxJava)
    implementation(Libraries.rxKotlin)
    implementation(Libraries.dagger)
    implementation(Libraries.daggerAndroid)
    implementation(Libraries.daggerAndroidSupport)
    kapt(Libraries.daggerCompiler)
    kapt(Libraries.daggerAndroidProcessor)
    implementation(Libraries.roomRuntime)
    implementation(Libraries.roomKtx)
    implementation(Libraries.roomRxJava2)
    kapt(Libraries.roomCompiler)
    implementation(Libraries.archCoreCommon)
    implementation(Libraries.archCoreRuntime)

    implementation(project(ModuleNames.Domain))

    unitTest {
        forEachDependency { testImplementation(it) }
    }
    testImplementation(Libraries.roomTesting)

    instrumentationTest {
        forEachDependency { androidTestImplementation(it) }
    }
    androidTestImplementation(Libraries.roomTesting)
}