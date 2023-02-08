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
import java.util.Arrays;

import jetbrains.buildServer.dotMemoryUnit.Constants;
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
  private RunnerParametersService myRunnerParametersService;

  @BeforeMethod
  public void setUp()
  {
    myCtx = new Mockery();
    //noinspection unchecked
    myOutputParser = (TextParser<DotMemoryUnitOutput>)myCtx.mock(TextParser.class);
    myAfterBuildPublisher = myCtx.mock(ResourcePublisher.class);
    myFileService = myCtx.mock(FileService.class);
    myLoggerService = myCtx.mock(LoggerService.class);
    myRunnerParametersService = myCtx.mock(RunnerParametersService.class);
  }

  @Test
  public void shouldSendWorkspaceFilesAsArtifactFilesWhenPublishAfterBuildArtifactFile() throws IOException {
    // Given
    final CommandLineExecutionContext executionContext = new CommandLineExecutionContext(0);
    final File workspace1File = new File("workspace1");
    final File workspace2File = new File("workspace2");
    final DotMemoryUnitOutput output = new DotMemoryUnitOutput(Arrays.asList(workspace1File, workspace2File));
    final File outputFile = new File("output");
    final File snapshotsDir = new File("snapshotsDir");

    myCtx.checking(new Expectations() {{
      oneOf(myRunnerParametersService).getRunnerParameter(Constants.SNAPSHOTS_PATH_VAR);
      will(returnValue(snapshotsDir.getPath()));

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
    return new DotMemoryUnitPublisher(myOutputParser, myAfterBuildPublisher, myFileService, myLoggerService, myRunnerParametersService);
  }
}
