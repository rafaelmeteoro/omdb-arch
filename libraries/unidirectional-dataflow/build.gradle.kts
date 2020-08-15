import dependencies.ModulesDependencies.Companion.moduleDependencies
import modules.LibraryModule
import modules.LibraryType
import modules.ModuleNames

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

    testImplementation(project(ModuleNames.Libraries.CoroutinesTestUtils))
}