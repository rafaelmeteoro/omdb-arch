package plugins

import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Project

fun Project.configureDetekt() {
    val detektExtension = extensions.findByName("detekt") as DetektExtension

    detektExtension.apply {
        val fileDir = "$rootDir/buildSrc/config"
        val baseBuildDir = "$buildDir/reports/detekt/detekt-checkstyle"

        config.from("$fileDir/detekt-config.yml")

        reports {
            xml {
                enabled = true
                destination = file("${baseBuildDir}.xml")
            }
            html {
                enabled = true
                destination = file("${baseBuildDir}.html")
            }
            txt {
                enabled = true
                destination = file("${baseBuildDir}.txt")
            }
        }
    }
}