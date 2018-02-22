## [<img src="http://jb.gg/badges/official.svg" height="20" align="center"/>](https://confluence.jetbrains.com/display/ALL/JetBrains+on+GitHub) JetBrains dotMemory Unit plugin for [<img src="https://cdn.worldvectorlogo.com/logos/teamcity.svg" height="20" align="center"/>](https://www.jetbrains.com/teamcity/)

This plugin provides the ability to run a build step under [JetBrains dotMemory Unit](https://www.jetbrains.com/dotmemory/unit/) for the .Net related build runners in TeamCity. 

[<img src="http://teamcity.jetbrains.com/app/rest/builds/buildType:(id:TeamCityPluginsByJetBrains_DotMemoryUnit_Build)/statusIcon.svg"/>](http://teamcity.jetbrains.com/viewType.html?buildTypeId=TeamCityPluginsByJetBrains_DotMemoryUnit_Build)

The proposed scenario has the following steps:
- create a solution with a test project
- add the [NuGet package for the JetBrains dotMemory Unit](https://www.nuget.org/packages/JetBrains.DotMemoryUnit/)
- implement tests using [JetBrains dotMemory Unit](https://www.jetbrains.com/dotmemory/unit/)
- create a build project and a configuration using the [TeamCity build server](https://www.jetbrains.com/teamcity/)
- add a build step to restore NuGet packages, for example `NuGet.exe restore testproj\packages.config -PackagesDirectory testproj\packages`
- add a build step to build the project
- add a build step to run memory tests and turn on "Run build step under JetBrains dotMemory Unit", use the "Path to dotMemoryUnit.exe" field to specify the path to the JetBrains dotMemory Unit profiler, for example `%system.teamcity.build.checkoutDir%\testproj\packages\JetBrains.dotMemoryUnit.2.0.20150814.155607-RC3\tools`, use the  "Path for storing workspaces" field to specify the path to the directory which will be used by the JetBrains dotMemory Unit to store workspaces.

:heavy_check_mark: Now it aslo compatible with [.NET CLI Plugin](https://github.com/JetBrains/teamcity-dotnet-plugin)

## Installation ##

To install the plugin, put the [zip archive](https://teamcity.jetbrains.com/guestAuth/app/rest/builds/buildType:TeamCityPluginsByJetBrains_DotMemoryUnit_Build,pinned:true,status:SUCCESS,branch:master,tags:release/artifacts/content/dotMemoryUnit.zip) into the 'plugins' directory under the TeamCity Data Directory. Restart the server.

<a name="agent_deployment"/>
##  Deployment of the JetBrains dotMemory Unit profiling command-line tool to a TeamCity agent ##

JetBrains dotMemory Unit is a unit testing framework allowing you to write tests that check your code for all kinds of memory issues. To run memory tests using JetBrains dotMemory Unit testing framework on TeamCity, you need to have the JetBrains dotMemory Unit profiling command-line tool on the each TeamCity agent where tests will be run. There are two ways to deploy the command-line tool to the TeamCity agent:

- Add a reference to the [NuGet package for the JetBrains dotMemory Unit](https://www.nuget.org/packages/JetBrains.DotMemoryUnit/) from your project. In this case you can specify a relative path to dotMemoryUnit.exe, for example `%system.teamcity.build.checkoutDir%\testproj\packages\JetBrains.dotMemoryUnit.2.0.20150814.155607-RC3\tools`. Add a step to restore this package before the tests' run.
- Download a free stand-alone runner from the [JetBrains dotMemory Unit page](https://www.jetbrains.com/dotmemory/unit/).

## Build ##

Use the 'mvn package' command from the root project to build your plugin. The resulting package 'dotMemoryUnit.zip' will be placed in the 'target' directory. The build is configured on the [JetBrains TeamCity build server](https://teamcity.jetbrains.com/viewLog.html?buildTypeId=TeamCityPluginsByJetBrains_DotMemoryUnit_Build&buildId=lastPinned&buildBranch=%3Cdefault%3E).

## License ##

JetBrains dotMemory Unit plugin for TeamCity is under the [Apache License](https://github.com/JetBrains/teamcity-dotmemory/blob/master/LICENSE).

## Resources ##

- [JetBrains dotMemory Unit](https://www.jetbrains.com/dotmemory/unit/)
- [NuGet package for the JetBrains dotMemory Unit](https://www.nuget.org/packages/JetBrains.DotMemoryUnit/)
- [JetBrains dotMemory Unit - Get Started](https://www.jetbrains.com/dotmemory/unit/help/Get_Started.html)
- [Unit Testing and Memory Profiling](http://blog.jetbrains.com/dotnet/2015/03/04/unit-testing-and-memory-profiling-can-they-be-combined/)
