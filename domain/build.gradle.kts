import dependencies.UnitTestDependencies.Companion.unitTest
import modules.LibraryModule
import modules.LibraryType

val module = LibraryModule(rootDir, LibraryType.Kotlin)
val jacoco = LibraryModule(rootDir, LibraryType.JacocoUnified)

apply(from = module.script())
apply(from = jacoco.script())

plugins {
    id(BuildPlugins.Ids.kotlinJVM)
    id(BuildPlugins.Ids.coveralls)
}

dependencies {
    implementation(Libraries.kotlinStdLib)
    implementation(Libraries.rxJava)
    implementation(Libraries.rxKotlin)

    unitTest {
        forEachDependency { testImplementation(it) }
    }
}