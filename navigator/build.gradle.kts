import dependencies.UnitTestDependencies.Companion.unitTest
import modules.LibraryModule
import modules.LibraryType
import modules.ModuleNames

val module = LibraryModule(rootDir, LibraryType.Android)
val jacoco = LibraryModule(rootDir, LibraryType.JacocoUnified)

apply(from = module.script())
apply(from = jacoco.script())

plugins {
    id(BuildPlugins.Ids.androidLibrary)
}

dependencies {
    implementation(Libraries.kotlinStdLib)
    implementation(Libraries.appCompat)
    implementation(Libraries.kodein)

    implementation(project(ModuleNames.Features.SharedUtilities))

    testImplementation(Libraries.roboletric)
    unitTest {
        forEachDependency { testImplementation(it) }
    }
}