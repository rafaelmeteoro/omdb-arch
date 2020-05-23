package modules

import java.io.File

class LibraryModule(private val rootDir: File, private val type: LibraryType) {

    fun script() = "$rootDir/buildSrc/shared/${target()}"

    private fun target() = when (type) {
        LibraryType.Kotlin -> kotlinBuildLogic
        LibraryType.Android -> androidBuildLogic
        LibraryType.JacocoUnified -> jacocoUnifiedLogic
        LibraryType.JacocoUnitTest -> jacocoUnitLogic
        LibraryType.DependencyGraph -> dependencyGraph
        LibraryType.KtLint -> ktlint
        LibraryType.Detekt -> detekt
    }

    private companion object {
        const val androidBuildLogic = "android-module.gradle"
        const val kotlinBuildLogic = "kotlin-module.gradle"
        const val jacocoUnifiedLogic = "jacoco-unified.gradle"
        const val jacocoUnitLogic = "jacoco-unit-test.gradle"
        const val dependencyGraph = "projectDependencyGraph.gradle"
        const val ktlint = "ktlint.gradle"
        const val detekt = "detekt.gradle"
    }
}