plugins {
    id(BuildPlugins.Ids.androidModule)
}

dependencies {
    implementation(Libraries.kotlinStdLib)
    implementation(Libraries.appCompat)
    implementation(Libraries.materialDesign)
    implementation(Libraries.constraintLayout)
    implementation(Libraries.lifecycleRuntime)
    implementation(Libraries.coroutinesCore)
    implementation(Libraries.coroutinesAndroid)
    implementation(Libraries.dagger)
    implementation(Libraries.daggerAndroid)
    implementation(Libraries.daggerAndroidSupport)
    implementation(Libraries.timber)
    kapt(Libraries.daggerCompiler)
    kapt(Libraries.daggerAndroidProcessor)

    implementation(project(":domain"))
    implementation(project(":libraries:ui-components"))
    implementation(project(":libraries:actions"))
}