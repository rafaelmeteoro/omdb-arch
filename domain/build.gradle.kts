import dependencies.UnitTestDependencies.Companion.unitTest
import modules.LibraryModule
import modules.LibraryType

val module = LibraryModule(rootDir, LibraryType.Kotlin)

apply(from = module.script())

plugins {
    id(BuildPlugins.Ids.kotlinJVM)
    id(BuildPlugins.Ids.coveralls)
}

dependencies {
    implementation(Libraries.kotlinStdLib)
    implementation(Libraries.rxJava)

    unitTest {
        forEachDependency { testImplementation(it) }
    }
}