import dependencies.ModulesDependencies.Companion.moduleDependencies

plugins {
    id(BuildPlugins.Ids.androidModule)
}

dependencies {
    moduleDependencies {
        forEachDependencies(persistence) { implementation(it) }
        forEachCompilers(persistence) { kapt(it) }
        forEachTestDependencies(persistence) { testImplementation(it) }
    }

    implementation(project(":domain"))
}