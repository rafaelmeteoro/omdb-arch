import dependencies.ModulesDependencies.Companion.moduleDependencies

plugins {
    id(BuildPlugins.Ids.androidModule)
    id(BuildPlugins.Ids.kotlinxSerialization)
}

dependencies {
    moduleDependencies {
        forEachDependencies(rest) { implementation(it) }
        forEachCompilers(rest) { kapt(it) }
        forEachTestDependencies(rest) { testImplementation(it) }
    }

    implementation(project(":domain"))
    testImplementation(project(":libraries:coroutines-testutils"))
}
