# TeamCity dotMemory Unit plugin #

## Install ##

To install the plugin, put zip archive to 'plugins' dir under TeamCity data directory. If you only changed agent-side code of your plugin, the upgrade will be perfomed 'on the fly' (agents will upgrade when idle). If common or server-side code has changed, restart the server.

## Implemention ##

This project contains 3 modules: 'dotMemoryUnit-server', 'dotMemoryUnit-agent' and 'dotMemoryUnit-common'. They will contain code for server and agent parts and a common part, available for both (agent and server). When implementing components for server and agent parts, do not forget to update spring context files under 'main/resources/META-INF'. See TeamCity documentation for details on plugin development.

## Build ##

Issue 'mvn package' command from the root project to build your plugin. Resulting package 'dotMemoryUnit.zip' will be placed in 'target' directory.

## License ##

TeamCity dotMemory Unit plugin is under the [Apache License](https://github.com/JetBrains/teamcity-dotmemory/blob/master/LICENSE).

## Contributors ##

- [Nikolay Pianikov](https://github.com/NikolayPianikov)