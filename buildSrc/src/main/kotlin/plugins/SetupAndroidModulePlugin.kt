package plugins

import BuildPlugins
import org.gradle.api.Plugin
import org.gradle.api.Project

class SetupAndroidModulePlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            plugins.apply(BuildPlugins.Ids.androidLibrary)
            plugins.apply(BuildPlugins.Ids.kotlinAndroid)
            plugins.apply(BuildPlugins.Ids.kotlinAndroidExtensions)
            plugins.apply(BuildPlugins.Ids.kotlinKapt)
            plugins.apply(BuildPlugins.Ids.testLogger)
            plugins.apply(BuildPlugins.Ids.ktlint)
            plugins.apply(BuildPlugins.Ids.detekt)

            configureKotlinTasks()
            configureTestLogger()
            configureAsAndroidLibrary()
            configureAndroidExtensions()
            configureKtlint()
            configureDetekt()
        }
    }
}