package jetbrains.buildServer.dotMemoryUnit.agent;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import jetbrains.buildServer.dotNet.buildRunner.agent.*;
import jetbrains.buildServer.messages.serviceMessages.Message;
import org.jetbrains.annotations.NotNull;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DotMemoryUnitPublisherTest {
  private Mockery myCtx;
  private TextParser<DotMemoryUnitOutput> myOutputParser;
  private ResourcePublisher myAfterBuildPublisher;
  private FileService myFileService;
  private LoggerService myLoggerService;

  @BeforeMethod
  public void setUp()
  {
    myCtx = new Mockery();
    //noinspection unchecked
    myOutputParser = (TextParser<DotMemoryUnitOutput>)myCtx.mock(TextParser.class);
    myAfterBuildPublisher = myCtx.mock(ResourcePublisher.class);
    myFileService = myCtx.mock(FileService.class);
    myLoggerService = myCtx.mock(LoggerService.class);
  }

  @Test
  public void shouldSendWorkspaceFilesAsArtifactFilesWhenPublishAfterBuildArtifactFile() throws IOException {
    // Given
    final CommandLineExecutionContext executionContext = new CommandLineExecutionContext(0);
    final File workspace1File = new File("workspace1");
    final File workspace2File = new File("workspace2");
    final DotMemoryUnitOutput output = new DotMemoryUnitOutput(Arrays.asList(workspace1File, workspace2File));
    final File outputFile = new File("output");
    myCtx.checking(new Expectations() {{
      oneOf(myFileService).readAllTextFile(outputFile);
      will(Expectations.returnValue("output content"));

      oneOf(myOutputParser).parse("output content");
      will(Expectations.returnValue(output));

      oneOf(myAfterBuildPublisher).publishAfterBuildArtifactFile(executionContext, outputFile);

      exactly(2).of(myLoggerService).onMessage(with(any(Message.class)));
    }});

    final ResourcePublisher instance = createInstance();

    // When
    instance.publishAfterBuildArtifactFile(executionContext, outputFile);

    // Then
    myCtx.assertIsSatisfied();
  }

  @NotNull
  private DotMemoryUnitPublisher createInstance()
  {
    return new DotMemoryUnitPublisher(myOutputParser, myAfterBuildPublisher, myFileService, myLoggerService);
  }
}
