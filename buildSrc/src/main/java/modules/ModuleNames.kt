package modules

object ModuleNames {
    const val MainApp = ":app"
    const val Domain = ":domain"

    object Libraries {
        const val Logger = ":libraries:logger"
        const val Networking = ":libraries:networking"
        const val Rest = ":libraries:rest-omdb"
        const val Persistance = ":libraries:persistance"
        const val UiComponents = ":libraries:ui-components"
        const val Actions = ":libraries:actions"
    }

    object Features {
        const val Onboarding = ":features:onboarding"
        const val Home = ":features:home"
        const val Details = ":features:details"
        const val Favorites = ":features:favorites"
    }
}