import modules.LibraryModule
import modules.LibraryType
import modules.ModuleNames

plugins {
    id(BuildPlugins.Ids.androidLibrary)
    kotlin(BuildPlugins.Ids.kotlinKapt)
}

val module = LibraryModule(rootDir, LibraryType.Android)

apply(from = module.script())

dependencies {
    implementation(Libraries.kotlinStdLib)
    implementation(Libraries.appCompat)
    implementation(Libraries.rxJava)
    implementation(Libraries.rxKotlin)
    implementation(Libraries.lifecycleCommon)
    implementation(Libraries.lifecycleJava8)
    implementation(Libraries.dagger)

    implementation(project(ModuleNames.Logger))
    implementation(project(ModuleNames.Domain))
}