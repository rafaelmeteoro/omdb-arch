import dependencies.ModulesDependencies.Companion.moduleDependencies

plugins {
    id(BuildPlugins.Ids.androidModule)
    id(BuildPlugins.Ids.safeArgs)
}

dependencies {
    moduleDependencies {
        forEachDependencies(favorites) { implementation(it) }
        forEachCompilers(favorites) { kapt(it) }
        forEachTestDependencies(favorites) { testImplementation(it) }
    }

    implementation(project(":domain"))
    implementation(project(":libraries:ui-components"))
    implementation(project(":libraries:actions"))
}