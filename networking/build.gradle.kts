import dependencies.UnitTestDependencies.Companion.unitTest
import modules.LibraryModule
import modules.LibraryType
import modules.ModuleNames

val module = LibraryModule(rootDir, LibraryType.Kotlin)

apply(from = module.script())

plugins {
    id(BuildPlugins.Ids.kotlinJVM)
    id(BuildPlugins.Ids.coveralls)
    kotlin(BuildPlugins.Ids.kotlinKapt)
}

dependencies {
    implementation(Libraries.kotlinStdLib)
    implementation(Libraries.kotlinSerialization)

    implementation(Libraries.okhttp)
    implementation(Libraries.okhttpLogger)
    implementation(Libraries.retrofit)
    implementation(Libraries.retrofitScalars)
    implementation(Libraries.retrofitGsonConverter)
    implementation(Libraries.retrofitKotlinSerialization)
    implementation(Libraries.gson)
    implementation(Libraries.dagger)
    kapt(Libraries.daggerCompiler)

    implementation(project(ModuleNames.Logger))
    implementation(project(ModuleNames.Domain))

    unitTest {
        forEachDependency { testImplementation(it) }
    }
}