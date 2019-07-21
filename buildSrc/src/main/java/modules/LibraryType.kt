package modules

sealed class LibraryType {
    object Kotlin : LibraryType()
    object Android : LibraryType()
    object JacocoUnified : LibraryType()
    object JacocoUnitTest : LibraryType()
}