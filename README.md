# JetBrains dotMemory Unit plugin for TeamCity #

This plugin provides ability to run build step under the [JetBrains dotMemory Unit](https://www.jetbrains.com/dotmemory/unit/) for the .Net related build runners in the TeamCity. The common scenario could has following steps:
- create solution with test project
- add [NuGet package for the JetBrains dotMemory Unit](https://www.nuget.org/packages/JetBrains.DotMemoryUnit/)
- implement tests using [JetBrains dotMemory Unit](https://www.jetbrains.com/dotmemory/unit/)
- create build project and configuration using the [TeamCity build server](https://www.jetbrains.com/teamcity/)
- add build step to restore NuGet packages, for example `NuGet.exe restore testproj\packages.config -PackagesDirectory testproj\packages`
- add build step to build project
- add build step to run memory tests and turn on "Run build step under JetBrains dotMemory Unit", use field "Path to dotMemoryUnit.exe" to specify path to JetBrains dotMemory Unit profiler, for example `%system.teamcity.build.checkoutDir%\testproj\packages\JetBrains.dotMemoryUnit.2.0.20150814.155607-RC3\tools`, use field "Path for storing workspaces" to specify path to directory which will be used by the JetBrains dotMemory Unit for storing workspaces

## Install ##

To install the plugin, put [zip archive](http://teamcity.jetbrains.com/httpAuth/app/rest/builds/buildType:TeamCityPluginsByJetBrains_DotMemoryUnit_Build,pinned:true,status:SUCCESS,branch:dorMemoryUnit-1.0.6/artifacts/content/dotMemoryUnit.zip) to 'plugins' direrctory under TeamCity data directory. Restart the server. See the [instruction](README.DOWNLOAD.md) to download JetBrains dotMemory Unit on the TeamCity agent.

## Implemention ##

This project contains 3 modules: 'dotMemoryUnit-server', 'dotMemoryUnit-agent' and 'dotMemoryUnit-common'. They contain code for server and agent parts and a common part, available for both (agent and server). When implementing components for server and agent parts, do not forget to update spring context files under 'main/resources/META-INF'. See [TeamCity documentation](https://confluence.jetbrains.com/display/TCD9/Developing+Plugins+Using+Maven) for details on plugin development.

## Build ##

Use 'mvn package' command from the root project to build your plugin. Resulting package 'dotMemoryUnit.zip' will be placed in 'target' directory. The build is configured on the [JetBrains TeamCity build server](https://teamcity.jetbrains.com/viewLog.html?buildTypeId=TeamCityPluginsByJetBrains_DotMemoryUnit_Build&buildId=lastPinned&buildBranch=%3Cdefault%3E).

## License ##

JetBrains dotMemory Unit plugin for TeamCity is under the [Apache License](https://github.com/JetBrains/teamcity-dotmemory/blob/master/LICENSE).

## Contributors ##

- [Nikolay Pianikov](https://github.com/NikolayPianikov)

## Resources ##

- [JetBrains dotMemory Unit](https://www.jetbrains.com/dotmemory/unit/)
- [NuGet package for the JetBrains dotMemory Unit](https://www.nuget.org/packages/JetBrains.DotMemoryUnit/)
- [JetBrains dotMemory Unit - Get Started](https://www.jetbrains.com/dotmemory/unit/help/Get_Started.html)
- [Unit Testing and Memory Profiling](http://blog.jetbrains.com/dotnet/2015/03/04/unit-testing-and-memory-profiling-can-they-be-combined/)
