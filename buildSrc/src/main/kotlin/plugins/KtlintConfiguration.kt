package plugins

import org.gradle.api.Project
import org.jlleitschuh.gradle.ktlint.KtlintExtension
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

fun Project.configureKtlint() {
    val ktlintExtension = extensions.findByName("ktlint") as KtlintExtension

    ktlintExtension.apply {
        outputToConsole.set(true)
        outputColorName.set("RED")
        disabledRules.set(setOf("final-newline"))

        reporters {
            reporter(ReporterType.PLAIN)
            reporter(ReporterType.CHECKSTYLE)
            reporter(ReporterType.HTML)
        }
    }
}