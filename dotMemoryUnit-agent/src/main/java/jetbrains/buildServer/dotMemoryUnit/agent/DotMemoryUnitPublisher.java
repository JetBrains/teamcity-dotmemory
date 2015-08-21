package jetbrains.buildServer.dotMemoryUnit.agent;

import java.io.File;
import java.io.IOException;
import jetbrains.buildServer.dotNet.buildRunner.agent.*;
import jetbrains.buildServer.messages.serviceMessages.Message;
import org.jetbrains.annotations.NotNull;

public class DotMemoryUnitPublisher implements ResourcePublisher {
  private static final String OUTPUT_FILE_NOT_FOUND_ERROR_MESSAGE = "Dot Memory Unit output file \"%s\" was not found";
  private final TextParser<DotMemoryUnitOutput> myOutputParser;
  private final FileService myFileService;
  private final LoggerService myLoggerService;
  private final ResourcePublisher myAfterBuildPublisher;

  public DotMemoryUnitPublisher(
    @NotNull final TextParser<DotMemoryUnitOutput> outputParser,
    @NotNull final ResourcePublisher afterBuildPublisher,
    @NotNull final FileService fileService,
    @NotNull final LoggerService loggerService) {
    myOutputParser = outputParser;
    myAfterBuildPublisher = afterBuildPublisher;
    myFileService = fileService;
    myLoggerService = loggerService;
  }

  @Override
  public void publishBeforeBuildFile(@NotNull final CommandLineExecutionContext executionContext, @NotNull final File file, @NotNull final String content) {
  }

  @Override
  public void publishAfterBuildArtifactFile(@NotNull final CommandLineExecutionContext executionContext, @NotNull final File file) {
    myAfterBuildPublisher.publishAfterBuildArtifactFile(executionContext, file);

    try {
      final String outputText = myFileService.readAllTextFile(file);
      final DotMemoryUnitOutput output = myOutputParser.parse(outputText);
      for(File workspaceFile : output.getWorkspaces()) {
        myLoggerService.onMessage(new Message(String.format("The workspace %s was created by the JetBrains dotMemory unit", workspaceFile.toURI().toURL()), "NORMAL", ""));
      }

    } catch (IOException e) {
      throw new BuildException(String.format(OUTPUT_FILE_NOT_FOUND_ERROR_MESSAGE, file.getPath()));
    }
  }
}
