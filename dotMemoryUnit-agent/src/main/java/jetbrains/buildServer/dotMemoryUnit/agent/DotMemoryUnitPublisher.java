

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