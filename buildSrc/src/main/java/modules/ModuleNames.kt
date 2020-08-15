package modules

object ModuleNames {
    const val MainApp = ":app"
    const val Domain = ":domain"

    object Libraries {
        const val Logger = ":libraries:logger"
        const val Rest = ":libraries:rest-omdb"
        const val Persistance = ":libraries:persistance"
        const val UiComponents = ":libraries:ui-components"
        const val Actions = ":libraries:actions"
        const val Architecture = ":libraries:unidirectional-dataflow"
        const val CoroutinesTestUtils = ":libraries:coroutines-testutils"
    }

    object Features {
        const val Onboarding = ":features:onboarding"
        const val Home = ":features:home"
        const val Details = ":features:details"
        const val Favorites = ":features:favorites"
    }
}