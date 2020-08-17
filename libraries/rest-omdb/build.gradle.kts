import dependencies.UnitTestDependencies.Companion.unitTest

plugins {
    id(BuildPlugins.Ids.androidModule)
    id(BuildPlugins.Ids.kotlinxSerialization)
}

dependencies {
    implementation(Libraries.kotlinStdLib)
    implementation(Libraries.kotlinSerialization)
    implementation(Libraries.okhttp)
    implementation(Libraries.okhttpLogger)
    implementation(Libraries.retrofit)
    implementation(Libraries.retrofitRxAdapter)
    implementation(Libraries.retrofitScalars)
    implementation(Libraries.retrofitGsonConverter)
    implementation(Libraries.retrofitKotlinSerialization)
    implementation(Libraries.gson)
    implementation(Libraries.rxJava)
    implementation(Libraries.rxKotlin)
    implementation(Libraries.coroutinesCore)
    implementation(Libraries.dagger)
    implementation(Libraries.daggerAndroid)
    implementation(Libraries.daggerAndroidSupport)
    implementation(Libraries.timber)
    kapt(Libraries.daggerCompiler)
    kapt(Libraries.daggerAndroidProcessor)

    implementation(project(":domain"))
    testImplementation(project(":libraries:coroutines-testutils"))

    unitTest { forEachDependency { testImplementation(it) } }
}
