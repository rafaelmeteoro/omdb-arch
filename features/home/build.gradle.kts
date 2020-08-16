import dependencies.ModulesDependencies.Companion.moduleDependencies

plugins {
    id(BuildPlugins.Ids.androidModule)
}

dependencies {
    moduleDependencies {
        forEachDependencies(home) { implementation(it) }
        forEachCompilers(home) { kapt(it) }
        forEachTestDependencies(home) { testImplementation(it) }
    }

    implementation(project(":domain"))
    implementation(project(":libraries:ui-components"))
    implementation(project(":libraries:actions"))
    implementation(project(":libraries:unidirectional-dataflow"))
    testImplementation(project(":libraries:coroutines-testutils"))
}