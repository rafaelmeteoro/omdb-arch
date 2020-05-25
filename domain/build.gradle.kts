import dependencies.ModulesDependencies.Companion.moduleDependencies
import modules.LibraryModule
import modules.LibraryType

val module = LibraryModule(rootDir, LibraryType.Android)

apply(from = module.script())

plugins {
    id(BuildPlugins.Ids.androidLibrary)
    kotlin(BuildPlugins.Ids.kotlinAndroid)
    kotlin(BuildPlugins.Ids.kotlinExtensions)
    kotlin(BuildPlugins.Ids.kotlinKapt)
    id(BuildPlugins.Ids.coveralls)
}

dependencies {
    moduleDependencies {
        forEachDependencies(domain) { implementation(it) }
        forEachCompilers(domain) { kapt(it) }
        forEachTestDependencies(domain) { testImplementation(it) }
    }
}