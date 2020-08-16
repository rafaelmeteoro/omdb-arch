import dependencies.ModulesDependencies.Companion.moduleDependencies

plugins {
    id(BuildPlugins.Ids.androidModule)
}

dependencies {
    moduleDependencies {
        forEachDependencies(onboarding) { implementation(it) }
        forEachCompilers(onboarding) { kapt(it) }
    }

    implementation(project(":domain"))
    implementation(project(":libraries:ui-components"))
    implementation(project(":libraries:actions"))
}