# TeamCity dotMemory Unit plugin #

This plugin is adding advanced options for .Net related build runners in the TeamCity which allow to run build step under the dotMemory Unit profiler. The common scenario could has following steps:
- create solution with test project
- add [NuGet package for the dotMemory Unit](https://www.nuget.org/packages/JetBrains.DotMemoryUnit/)
- implement tests using [dotMemory Unit framework](https://www.jetbrains.com/dotmemory/unit/)
- create build project and configuration using the [TeamCity build server](https://www.jetbrains.com/teamcity/)
- add step to restore NuGet packages, for example `NuGet.exe restore testproj\packages.config -PackagesDirectory testproj\packages`
- add step to build project
- add step to run memory tests and use advanced option: turn on "Use dotMemory Unit", specify path to dotMemory Unit profiler, for example `%system.teamcity.build.checkoutDir%\testproj\packages\JetBrains.dotMemoryUnit.2.0.20150814.155607-RC3\tools`
- 
## Install ##

To install the plugin, put [zip archive](https://teamcity.jetbrains.com/repository/download/TeamCityPluginsByJetBrains_DotMemoryUnit_Build/551932:id/dotMemoryUnit.zip) to 'plugins' direrctory under TeamCity data directory. Restart the server.

## Implemention ##

This project contains 3 modules: 'dotMemoryUnit-server', 'dotMemoryUnit-agent' and 'dotMemoryUnit-common'. They contain code for server and agent parts and a common part, available for both (agent and server). When implementing components for server and agent parts, do not forget to update spring context files under 'main/resources/META-INF'. See [TeamCity documentation](https://confluence.jetbrains.com/display/TCD9/Developing+Plugins+Using+Maven) for details on plugin development.

## Build ##

Issue 'mvn package' command from the root project to build your plugin. Resulting package 'dotMemoryUnit.zip' will be placed in 'target' directory. The build is configured on the [JetBrains TeamCity build server](https://teamcity.jetbrains.com/project.html?projectId=TeamCityPluginsByJetBrains_DotMemoryUnit).

## License ##

TeamCity dotMemory Unit plugin is under the [Apache License](https://github.com/JetBrains/teamcity-dotmemory/blob/master/LICENSE).

## Contributors ##

- [Nikolay Pianikov](https://github.com/NikolayPianikov)
