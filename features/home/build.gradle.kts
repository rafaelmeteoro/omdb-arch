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
}

dependencies {
    moduleDependencies {
        forEachDependencies(home) { implementation(it) }
        forEachCompilers(home) { kapt(it) }
        forEachTestDependencies(home) { testImplementation(it) }
    }

    implementation(project(":domain"))
    implementation(project(":libraries:ui-components"))
    implementation(project(":libraries:actions"))
    implementation(project(":libraries:unidirectional-dataflow"))
    testImplementation(project(":libraries:coroutines-testutils"))
}