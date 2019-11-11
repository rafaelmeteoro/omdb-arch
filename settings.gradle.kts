import modules.ModuleNames.Domain
import modules.ModuleNames.Libraries
import modules.ModuleNames.Features
import modules.ModuleNames.MainApp

include(
    MainApp,
    Domain,
    Libraries.Logger,
    Libraries.Networking,
    Libraries.Rest,
    Libraries.Persistance,
    Libraries.Navigator,
    Libraries.UiComponents,
    Features.Onboarding,
    Features.Home,
    Features.Details,
    Features.Favorites
)