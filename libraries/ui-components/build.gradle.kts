plugins {
    id(BuildPlugins.Ids.androidModule)
}

dependencies {
    implementation(Libraries.kotlinStdLib)
    implementation(Libraries.appCompat)
    implementation(Libraries.materialDesign)
    implementation(Libraries.recyclerView)
    implementation(Libraries.lifecycleRuntime)
    implementation(Libraries.lifecycleExtensions)
    implementation(Libraries.lifecycleViewModel)
    implementation(Libraries.rxJava)
    implementation(Libraries.rxKotlin)

    implementation(project(":domain"))
    implementation(project(":libraries:unidirectional-dataflow"))
}