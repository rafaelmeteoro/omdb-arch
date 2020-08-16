import dependencies.ModulesDependencies.Companion.moduleDependencies

plugins {
    id(BuildPlugins.Ids.androidModule)
}

dependencies {
    moduleDependencies {
        forEachDependencies(details) { implementation(it) }
        forEachCompilers(details) { kapt(it) }
        forEachTestDependencies(details) { testImplementation(it) }
    }

    implementation(project(":domain"))
    implementation(project(":libraries:ui-components"))
    implementation(project(":libraries:actions"))
}