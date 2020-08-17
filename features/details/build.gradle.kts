import dependencies.UnitTestDependencies.Companion.unitTest

plugins {
    id(BuildPlugins.Ids.androidModule)
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
    implementation(Libraries.picasso)
    implementation(Libraries.coil)
    implementation(Libraries.coilBase)
    implementation(Libraries.dagger)
    implementation(Libraries.daggerAndroid)
    implementation(Libraries.daggerAndroidSupport)
    implementation(Libraries.roomRuntime)
    implementation(Libraries.roomKtx)
    implementation(Libraries.roomRxJava2)
    implementation(Libraries.timber)
    kapt(Libraries.daggerCompiler)
    kapt(Libraries.daggerAndroidProcessor)
    kapt(Libraries.roomCompiler)

    implementation(project(":domain"))
    implementation(project(":libraries:ui-components"))
    implementation(project(":libraries:actions"))

    unitTest { forEachDependency { testImplementation(it) } }
}