package modules

object ModuleNames {
    const val MainApp = ":app"
    const val Logger = ":logger"
    const val Domain = ":domain"

    object Infrastructure {
        const val Networking = ":networking"
        const val Rest = ":rest-omdb"
    }

    object Features {
        const val SharedAssets = ":shared-assets"
        const val SharedUtilities = ":shared-utilities"
        const val Navigator = ":navigator"
        const val Home = ":home"
        const val Details = ":details"
    }
}