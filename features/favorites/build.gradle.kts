import dependencies.ModulesDependencies.Companion.moduleDependencies
import modules.LibraryModule
import modules.LibraryType
import modules.ModuleNames

val module = LibraryModule(rootDir, LibraryType.Android)

apply(from = module.script())

plugins {
    id(BuildPlugins.Ids.androidLibrary)
    id(BuildPlugins.Ids.safeArgs)
    kotlin(BuildPlugins.Ids.kotlinAndroid)
    kotlin(BuildPlugins.Ids.kotlinExtensions)
    kotlin(BuildPlugins.Ids.kotlinKapt)
}

dependencies {
    moduleDependencies {
        forEachDependencies(favorites) { implementation(it) }
        forEachCompilers(favorites) { kapt(it) }
        forEachTestDependencies(favorites) { testImplementation(it) }
    }

    implementation(project(ModuleNames.Domain))
    implementation(project(ModuleNames.Libraries.UiComponents))
    implementation(project(ModuleNames.Libraries.Actions))
}