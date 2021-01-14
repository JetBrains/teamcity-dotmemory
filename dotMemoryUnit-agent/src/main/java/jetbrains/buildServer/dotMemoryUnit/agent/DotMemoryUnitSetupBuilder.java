/*
 * Copyright 2000-2021 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jetbrains.buildServer.dotMemoryUnit.agent;

import com.intellij.openapi.util.text.StringUtil;
import jetbrains.buildServer.dotMemoryUnit.Constants;
import jetbrains.buildServer.dotNet.buildRunner.agent.*;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DotMemoryUnitSetupBuilder implements CommandLineSetupBuilder {
  static final String DOT_MEMORY_UNIT_EXE_NAME = "dotMemoryUnit.exe";
  static final String DOT_MEMORY_UNIT_PROJECT_EXT = ".xml";
  static final String DOT_MEMORY_UNIT_OUTPUT_EXT = ".dotMemoryUnitResult";

  private final ResourceGenerator<DotMemoryUnitContext> myDotMemoryUnitProjectGenerator;
  private final ResourcePublisher myBeforeBuildPublisher;
  private final ResourcePublisher myDotMemoryUnitPublisher;
  private final RunnerParametersService myParametersService;
  private final FileService myFileService;
  private final RunnerAssertions myAssertions;

  public DotMemoryUnitSetupBuilder(
    @NotNull final ResourceGenerator<DotMemoryUnitContext> dotMemoryUnitProjectGenerator,
    @NotNull final ResourcePublisher beforeBuildPublisher,
    @NotNull final ResourcePublisher dotMemoryUnitPublisher,
    @NotNull final RunnerParametersService parametersService,
    @NotNull final FileService fileService,
    @NotNull final RunnerAssertions assertions) {
    myDotMemoryUnitProjectGenerator = dotMemoryUnitProjectGenerator;
    myBeforeBuildPublisher = beforeBuildPublisher;
    myDotMemoryUnitPublisher = dotMemoryUnitPublisher;
    myParametersService = parametersService;
    myFileService = fileService;
    myAssertions = assertions;
  }

  @Override
  @NotNull
  public Iterable<CommandLineSetup> build(@NotNull final CommandLineSetup baseSetup) {
    if(myAssertions.contains(RunnerAssertions.Assertion.PROFILING_IS_NOT_ALLOWED)) {
      return Collections.singleton(baseSetup);
    }

    final String dotMemoryUnitTool = myParametersService.tryGetRunnerParameter(Constants.USE_VAR);
    if (StringUtil.isEmptyOrSpaces(dotMemoryUnitTool) || !Boolean.parseBoolean(dotMemoryUnitTool)) {
      return Collections.singleton(baseSetup);
    }

    File toolPath = new File(myParametersService.getRunnerParameter(Constants.PATH_VAR), DOT_MEMORY_UNIT_EXE_NAME);
    if(!toolPath.isAbsolute()) {
      toolPath = new File(myFileService.getCheckoutDirectory(), toolPath.getPath());
    }

    myFileService.validatePath(toolPath);

    List<CommandLineResource> resources = new ArrayList<CommandLineResource>(baseSetup.getResources());
    final File projectFile = myFileService.getTempFileName(DOT_MEMORY_UNIT_PROJECT_EXT);
    final File outputFile = myFileService.getTempFileName(DOT_MEMORY_UNIT_OUTPUT_EXT);
    final File snapshotsDirectory = myFileService.getTempDirectory();
    final String projectFileContent = myDotMemoryUnitProjectGenerator.create(new DotMemoryUnitContext(baseSetup, snapshotsDirectory, outputFile));
    resources.add(new CommandLineFile(myBeforeBuildPublisher, projectFile, projectFileContent));
    resources.add(new CommandLineArtifact(myDotMemoryUnitPublisher, outputFile));
    return Collections.singleton(new CommandLineSetup(toolPath.getPath(), Collections.singletonList(new CommandLineArgument(projectFile.getPath(), CommandLineArgument.Type.PARAMETER)), resources));
  }
}