import modules.ModuleNames.MainApp
import modules.ModuleNames.Logger
import modules.ModuleNames.Domain
import modules.ModuleNames.Infrastructure

include(
    MainApp,
    Logger,
    Domain,
    Infrastructure.Networking,
    Infrastructure.Rest
)