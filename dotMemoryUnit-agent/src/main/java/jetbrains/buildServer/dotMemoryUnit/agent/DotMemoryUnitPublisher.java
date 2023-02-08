/*
 * Copyright 2000-2023 JetBrains s.r.o.
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

import java.io.File;
import java.io.IOException;
import jetbrains.buildServer.dotMemoryUnit.Constants;
import jetbrains.buildServer.dotNet.buildRunner.agent.*;
import jetbrains.buildServer.messages.serviceMessages.PublishArtifacts;
import org.jetbrains.annotations.NotNull;

public class DotMemoryUnitPublisher implements ResourcePublisher {
  private final TextParser<DotMemoryUnitOutput> myOutputParser;
  private final FileService myFileService;
  private final LoggerService myLoggerService;
  private final RunnerParametersService myParametersService;
  private final ResourcePublisher myAfterBuildPublisher;

  public DotMemoryUnitPublisher(
    @NotNull final TextParser<DotMemoryUnitOutput> outputParser,
    @NotNull final ResourcePublisher afterBuildPublisher,
    @NotNull final FileService fileService,
    @NotNull final LoggerService loggerService,
    @NotNull final RunnerParametersService parametersService) {
    myOutputParser = outputParser;
    myAfterBuildPublisher = afterBuildPublisher;
    myFileService = fileService;
    myLoggerService = loggerService;
    myParametersService = parametersService;
  }

  @Override
  public void publishBeforeBuildFile(@NotNull final CommandLineExecutionContext executionContext, @NotNull final File file, @NotNull final String content) {
  }

  @Override
  public void publishAfterBuildArtifactFile(@NotNull final CommandLineExecutionContext executionContext, @NotNull final File file) {
    myAfterBuildPublisher.publishAfterBuildArtifactFile(executionContext, file);
    final File snapshotsTargetDirectory = new File(myParametersService.getRunnerParameter(Constants.SNAPSHOTS_PATH_VAR));

    try {
      final String outputText = myFileService.readAllTextFile(file);
      final DotMemoryUnitOutput output = myOutputParser.parse(outputText);
      for(File workspaceFile : output.getWorkspaces()) {
        final String artifactPath = String.format("%s => %s", workspaceFile.getPath(), snapshotsTargetDirectory.getPath());
        myLoggerService.onMessage(new PublishArtifacts(artifactPath));
      }

    } catch (IOException e) {
      throw new BuildException(e.getMessage());
    }
  }
}
