package modules

sealed class LibraryType {
    object Kotlin : LibraryType()
    object Android : LibraryType()
    object DependencyGraph : LibraryType()
    object KtLint : LibraryType()
    object Detekt : LibraryType()
}