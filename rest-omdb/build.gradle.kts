import dependencies.UnitTestDependencies.Companion.unitTest
import modules.LibraryModule
import modules.LibraryType
import modules.ModuleNames

plugins {
    id(BuildPlugins.Ids.kotlinJVM)
    id(BuildPlugins.Ids.kotlinxSerialization)
    id(BuildPlugins.Ids.coveralls)
    kotlin(BuildPlugins.Ids.kotlinKapt)
}

val module = LibraryModule(rootDir, LibraryType.Kotlin)

apply(from = module.script())

dependencies {
    implementation(Libraries.kotlinStdLib)
    implementation(Libraries.kotlinSerialization)

    implementation(Libraries.okhttp)
    implementation(Libraries.okhttpLogger)
    implementation(Libraries.retrofit)
    implementation(Libraries.retrofitRxAdapter)
    implementation(Libraries.retrofitScalars)
    implementation(Libraries.retrofitGsonConverter)
    implementation(Libraries.gson)
    implementation(Libraries.dagger)
    implementation(Libraries.rxJava)
    implementation(Libraries.rxKotlin)

    implementation(project(ModuleNames.Logger))
    implementation(project(ModuleNames.Domain))
    implementation(project(ModuleNames.Infrastructure.Networking))

    unitTest {
        forEachDependency { testImplementation(it) }
    }
}