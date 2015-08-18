package jetbrains.buildServer.dotMemoryUnit.agent;

import java.io.File;
import jetbrains.buildServer.dotNet.buildRunner.agent.CommandLineSetup;
import org.jetbrains.annotations.NotNull;

public class DotMemoryUnitContext {
  private final CommandLineSetup myBaseSetup;
  private final File myWorkspaceDirectory;
  private final File myOutputFile;

  public DotMemoryUnitContext(
    @NotNull final CommandLineSetup baseSetup,
    @NotNull final File workspaceDirectory,
    @NotNull final File outputFile) {
    myBaseSetup = baseSetup;
    myWorkspaceDirectory = workspaceDirectory;
    myOutputFile = outputFile;
  }

  public CommandLineSetup getBaseSetup() {
    return myBaseSetup;
  }

  public File getOutputFile() {
    return myOutputFile;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    final DotMemoryUnitContext that = (DotMemoryUnitContext) o;

    return getBaseSetup().equals(that.getBaseSetup()) && getOutputFile().equals(that.getOutputFile());
  }

  @Override
  public int hashCode() {
    int result = getBaseSetup().hashCode();
    result = 31 * result + getOutputFile().hashCode();
    return result;
  }

  public File getWorkspaceDirectory() {
    return myWorkspaceDirectory;
  }
}
