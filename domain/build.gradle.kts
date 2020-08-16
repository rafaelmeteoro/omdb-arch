import dependencies.ModulesDependencies.Companion.moduleDependencies

plugins {
    id(BuildPlugins.Ids.androidModule)
}

dependencies {
    moduleDependencies {
        forEachDependencies(domain) { implementation(it) }
        forEachCompilers(domain) { kapt(it) }
        forEachTestDependencies(domain) { testImplementation(it) }

        testImplementation(project(":libraries:coroutines-testutils"))
    }
}