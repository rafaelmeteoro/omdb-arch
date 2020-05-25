import dependencies.ModulesDependencies.Companion.moduleDependencies
import modules.LibraryModule
import modules.LibraryType

val module = LibraryModule(rootDir, LibraryType.Android)

apply(from = module.script())

plugins {
    id(BuildPlugins.Ids.androidLibrary)
}

dependencies {
    moduleDependencies {
        forEachDependencies(actions) { implementation(it) }
        forEachTestDependencies(actions) { testImplementation(it) }
    }
}