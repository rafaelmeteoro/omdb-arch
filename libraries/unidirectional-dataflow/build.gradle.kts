import dependencies.ModulesDependencies.Companion.moduleDependencies
import modules.LibraryModule
import modules.LibraryType

val module = LibraryModule(rootDir, LibraryType.Kotlin)

apply(from = module.script())

plugins {
    id(BuildPlugins.Ids.kotlinJVM)
}

dependencies {
    moduleDependencies {
        forEachDependencies(architecture) { implementation(it) }
        forEachTestDependencies(architecture) { testImplementation(it) }
    }

    testImplementation(project(":libraries:coroutines-testutils"))
}