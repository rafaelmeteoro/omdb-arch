import modules.ModuleNames.Domain
import modules.ModuleNames.Libraries
import modules.ModuleNames.Features
import modules.ModuleNames.MainApp

include(
    MainApp,
    Domain,
    Libraries.Rest,
    Libraries.Persistance,
    Libraries.UiComponents,
    Libraries.Actions,
    Libraries.Architecture,
    Libraries.CoroutinesTestUtils,
    Features.Onboarding,
    Features.Home,
    Features.Details,
    Features.Favorites
)