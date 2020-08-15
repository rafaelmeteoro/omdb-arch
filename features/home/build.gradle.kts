import dependencies.ModulesDependencies.Companion.moduleDependencies
import modules.LibraryModule
import modules.LibraryType
import modules.ModuleNames

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

    implementation(project(ModuleNames.Domain))
    implementation(project(ModuleNames.Libraries.UiComponents))
    implementation(project(ModuleNames.Libraries.Actions))
    implementation(project(ModuleNames.Libraries.Architecture))
    testImplementation(project(ModuleNames.Libraries.CoroutinesTestUtils))
}