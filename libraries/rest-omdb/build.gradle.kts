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
    id(BuildPlugins.Ids.kotlinxSerialization)
}

dependencies {
    moduleDependencies {
        forEachDependencies(rest) { implementation(it) }
        forEachCompilers(rest) { kapt(it) }
        forEachTestDependencies(rest) { testImplementation(it) }
    }

    implementation(project(":domain"))
    testImplementation(project(":libraries:coroutines-testutils"))
}
