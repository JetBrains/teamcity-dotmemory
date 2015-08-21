## Downloading JetBrains dotMemory Unit tools ##

JetBrains dotMemory Unit is a unit testing framework which allows you to write tests that check your code for all kinds of memory issues. To run memory tests using JetBrains dotMemory Unit testing framework on the TeamCity you should have JetBrains dotMemory Unit runner on the each TeamCity agent where you are going to run them.

- Add refference to the [NuGet package for the JetBrains dotMemory Unit](https://www.nuget.org/packages/JetBrains.DotMemoryUnit/) from your project. In this case you could specify relative path to dotMemoryUnit.exe, for example `%system.teamcity.build.checkoutDir%\testproj\packages\JetBrains.dotMemoryUnit.2.0.20150814.155607-RC3\tools`
- Download free stand-alone runner from the [JetBrains dotMemory Unit page](https://www.jetbrains.com/dotmemory/unit/).