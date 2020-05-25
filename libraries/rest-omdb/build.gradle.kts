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
    id(BuildPlugins.Ids.kotlinxSerialization)
    id(BuildPlugins.Ids.coveralls)
}

dependencies {
    moduleDependencies {
        forEachDependencies(rest) { implementation(it) }
        forEachCompilers(rest) { kapt(it) }
        forEachTestDependencies(rest) { testImplementation(it) }
    }

    implementation(project(ModuleNames.Domain))
}
