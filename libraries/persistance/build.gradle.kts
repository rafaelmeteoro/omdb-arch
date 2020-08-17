import dependencies.UnitTestDependencies.Companion.unitTest

plugins {
    id(BuildPlugins.Ids.androidModule)
}

dependencies {
    implementation(Libraries.kotlinStdLib)
    implementation(Libraries.rxJava)
    implementation(Libraries.rxKotlin)
    implementation(Libraries.dagger)
    implementation(Libraries.daggerAndroid)
    implementation(Libraries.daggerAndroidSupport)
    implementation(Libraries.roomRuntime)
    implementation(Libraries.roomKtx)
    implementation(Libraries.roomRxJava2)
    implementation(Libraries.archCoreCommon)
    implementation(Libraries.archCoreRuntime)
    kapt(Libraries.daggerCompiler)
    kapt(Libraries.daggerAndroidProcessor)
    kapt(Libraries.roomCompiler)

    implementation(project(":domain"))

    unitTest { forEachDependency { testImplementation(it) } }
}