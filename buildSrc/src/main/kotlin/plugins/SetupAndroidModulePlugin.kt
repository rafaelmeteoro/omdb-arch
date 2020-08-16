package plugins

import BuildPlugins
import org.gradle.api.Plugin
import org.gradle.api.Project

class SetupAndroidModulePlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            plugins.apply(BuildPlugins.Ids.androidLibrary)
            plugins.apply(BuildPlugins.Ids.newKotlinAndroid)
            plugins.apply(BuildPlugins.Ids.kotlinAndroidExtensions)
            plugins.apply(BuildPlugins.Ids.newKotlinKapt)
            plugins.apply(BuildPlugins.Ids.testLogger)

            configureKotlinTasks()
            configureTestLogger()
            configureAsAndroidLibrary()
            configureAndroidExtensions()
        }
    }
}