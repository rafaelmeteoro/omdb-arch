import dependencies.UnitTestDependencies.Companion.unitTest

plugins {
    id(BuildPlugins.Ids.androidModule)
}

dependencies {
    implementation(Libraries.kotlinStdLib)
    implementation(Libraries.rxJava)
    implementation(Libraries.rxKotlin)
    implementation(Libraries.lifecycleCommon)
    implementation(Libraries.lifecycleJava8)
    implementation(Libraries.coroutinesCore)
    implementation(Libraries.dagger)
    implementation(Libraries.daggerAndroid)
    implementation(Libraries.daggerAndroidSupport)
    implementation(Libraries.timber)
    kapt(Libraries.daggerCompiler)
    kapt(Libraries.daggerAndroidProcessor)

    unitTest { forEachDependency { testImplementation(it) } }

    testImplementation(project(":libraries:coroutines-testutils"))
}