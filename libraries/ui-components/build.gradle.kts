import dependencies.ModulesDependencies.Companion.moduleDependencies

plugins {
    id(BuildPlugins.Ids.androidModule)
}

dependencies {
    moduleDependencies {
        forEachDependencies(uicomponents) { implementation(it) }
    }

    implementation(project(":domain"))
    implementation(project(":libraries:unidirectional-dataflow"))
}