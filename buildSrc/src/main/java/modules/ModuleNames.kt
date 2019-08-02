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
        const val SharedUtilities = ":shared-utilities"
    }
}