package jetbrains.buildServer.dotMemoryUnit.agent;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;

import jetbrains.buildServer.dotMemoryUnit.Constants;
import jetbrains.buildServer.dotNet.buildRunner.agent.*;
import org.jetbrains.annotations.NotNull;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.assertj.core.api.BDDAssertions.then;

public class DotMemoryUnitSetupBuilderTest {
  private Mockery myCtx;
  private RunnerParametersService myRunnerParametersService;
  private FileService myFileService;
  private ResourceGenerator<DotMemoryUnitContext> myDotMemoryUnitProjectGenerator;
  private ResourcePublisher myBeforeBuildPublisher;
  private CommandLineResource myCommandLineResource;
  private ResourcePublisher myAfterBuildPublisher;
  private RunnerAssertions myAssertions;

  @BeforeMethod
  public void setUp()
  {
    myCtx = new Mockery();
    //noinspection unchecked
    myDotMemoryUnitProjectGenerator = (ResourceGenerator<DotMemoryUnitContext>)myCtx.mock(ResourceGenerator.class);
    myBeforeBuildPublisher = myCtx.mock(ResourcePublisher.class, "BeforeBuildPublisher");
    myAfterBuildPublisher = myCtx.mock(ResourcePublisher.class, "AfterBuildPublisher");
    myRunnerParametersService = myCtx.mock(RunnerParametersService.class);
    myFileService = myCtx.mock(FileService.class);
    myCommandLineResource = myCtx.mock(CommandLineResource.class);
    myAssertions = myCtx.mock(RunnerAssertions.class);
  }

  @Test
  public void shouldCreateSetupWhenGetSetup()
  {
    // Given
    final CommandLineSetup baseSetup = new CommandLineSetup("someTool", Arrays.asList(new CommandLineArgument("/arg1", CommandLineArgument.Type.PARAMETER), new CommandLineArgument("/arg2", CommandLineArgument.Type.PARAMETER)), Collections.singletonList(myCommandLineResource));
    final File projectFile = new File("aaa");
    final File outputFile = new File("output");
    final File workspaceDir = new File("workspaceDir");
    final File dotMemoryUnitFile = new File("c:\\abc", DotMemoryUnitSetupBuilder.DOT_MEMORY_UNIT_EXE_NAME);
    myCtx.checking(new Expectations() {{
      oneOf(myAssertions).contains(RunnerAssertions.Assertion.PROFILING_IS_NOT_ALLOWED);
      will(returnValue(false));
      
      oneOf(myRunnerParametersService).tryGetRunnerParameter(Constants.USE_VAR);
      will(returnValue("True"));

      oneOf(myRunnerParametersService).getRunnerParameter(Constants.PATH_VAR);
      will(returnValue(dotMemoryUnitFile.getParent()));

      oneOf(myRunnerParametersService).getRunnerParameter(Constants.WORKSPACES_PATH_VAR);
      will(returnValue(workspaceDir.getPath()));

      oneOf(myFileService).getTempFileName(DotMemoryUnitSetupBuilder.DOT_MEMORY_UNIT_PROJECT_EXT);
      will(returnValue(projectFile));

      oneOf(myFileService).getTempFileName(DotMemoryUnitSetupBuilder.DOT_MEMORY_UNIT_OUTPUT_EXT);
      will(returnValue(outputFile));

      oneOf(myDotMemoryUnitProjectGenerator).create(new DotMemoryUnitContext(baseSetup, workspaceDir, outputFile));
      will(returnValue("project content"));

      oneOf(myFileService).validatePath(dotMemoryUnitFile);
    }});

    final DotMemoryUnitSetupBuilder instance = createInstance();

    // When
    final CommandLineSetup setup = instance.build(baseSetup).iterator().next();

    // Then
    myCtx.assertIsSatisfied();
    then(setup.getToolPath()).isEqualTo(dotMemoryUnitFile.getPath());
    then(setup.getArgs()).containsExactly(new CommandLineArgument(projectFile.getPath(), CommandLineArgument.Type.PARAMETER));
    then(setup.getResources()).containsExactly(
      myCommandLineResource,
      new CommandLineFile(myBeforeBuildPublisher, projectFile, "project content"),
      new CommandLineArtifact(myAfterBuildPublisher, outputFile));
  }

  @DataProvider(name = "runnerParamUseDotMemoryUnitCases")
  public Object[][] getParseTestsFromStringCases() {
    return new Object[][] {
      { null },
      { "" },
      { "abc" },
      { "False" },
      { "false" },
    };
  }

  @Test(dataProvider = "runnerParamUseDotMemoryUnitCases")
  public void shouldReturnBaseSetupWhenRunnerParamUseDotMemoryUnitIsEmptyOrFalse(final String useDotMemoryUnit)
  {
    // Given
    final CommandLineSetup baseSetup = new CommandLineSetup("someTool", Arrays.asList(new CommandLineArgument("/arg1", CommandLineArgument.Type.PARAMETER), new CommandLineArgument("/arg2", CommandLineArgument.Type.PARAMETER)), Collections.singletonList(myCommandLineResource));
    myCtx.checking(new Expectations() {{
      oneOf(myAssertions).contains(RunnerAssertions.Assertion.PROFILING_IS_NOT_ALLOWED);
      will(returnValue(false));

      oneOf(myRunnerParametersService).tryGetRunnerParameter(Constants.USE_VAR);
      will(returnValue(useDotMemoryUnit));
    }});

    final DotMemoryUnitSetupBuilder instance = createInstance();

    // When
    final CommandLineSetup setup = instance.build(baseSetup).iterator().next();

    // Then
    myCtx.assertIsSatisfied();
    then(setup).isEqualTo(baseSetup);
  }

  @Test
  public void shouldReturnBaseSetupWhenProfilingIsNotAllowed()
  {
    // Given
    final CommandLineSetup baseSetup = new CommandLineSetup("someTool", Arrays.asList(new CommandLineArgument("/arg1", CommandLineArgument.Type.PARAMETER), new CommandLineArgument("/arg2", CommandLineArgument.Type.PARAMETER)), Collections.singletonList(myCommandLineResource));
    myCtx.checking(new Expectations() {{
      oneOf(myAssertions).contains(RunnerAssertions.Assertion.PROFILING_IS_NOT_ALLOWED);
      will(returnValue(true));
    }});

    final DotMemoryUnitSetupBuilder instance = createInstance();

    // When
    final CommandLineSetup setup = instance.build(baseSetup).iterator().next();

    // Then
    myCtx.assertIsSatisfied();
    then(setup).isEqualTo(baseSetup);
  }

  @NotNull
  private DotMemoryUnitSetupBuilder createInstance()
  {
    return new DotMemoryUnitSetupBuilder(
      myDotMemoryUnitProjectGenerator,
      myBeforeBuildPublisher,
      myAfterBuildPublisher,
      myRunnerParametersService,
      myFileService,
      myAssertions);
  }
}
