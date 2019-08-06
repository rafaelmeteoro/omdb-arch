import modules.ModuleNames.MainApp
import modules.ModuleNames.Logger
import modules.ModuleNames.Domain
import modules.ModuleNames.Infrastructure
import modules.ModuleNames.Features

include(
    MainApp,
    Logger,
    Domain,
    Infrastructure.Networking,
    Infrastructure.Rest,
    Features.SharedUtilities,
    Features.Navigator
)