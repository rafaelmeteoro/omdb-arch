package plugins

import BuildPlugins
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType

class SetupKotlinModulePlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            plugins.apply(BuildPlugins.Ids.kotlinJVM)
            plugins.apply(BuildPlugins.Ids.testLogger)
            plugins.apply(BuildPlugins.Ids.detekt)

            configureKotlinTasks()
            configureTestLogger()
            configureDetekt()
        }
    }
}