import dependencies.ModulesDependencies.Companion.moduleDependencies
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
    moduleDependencies {
        forEachDependencies(persistence) { implementation(it) }
        forEachCompilers(persistence) { kapt(it) }
        forEachTestDependencies(persistence) { testImplementation(it) }
    }

    implementation(project(ModuleNames.Domain))
}