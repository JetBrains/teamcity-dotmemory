package jetbrains.buildServer.dotMemoryUnit.agent;

import java.io.File;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class DotMemoryUnitOutput {
  private final List<File> myWorkspaces;

  public DotMemoryUnitOutput(@NotNull final List<File> workspaces) {
    myWorkspaces = workspaces;
  }

  @NotNull
  public List<File> getWorkspaces() {
    return myWorkspaces;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    final DotMemoryUnitOutput that = (DotMemoryUnitOutput)o;

    return getWorkspaces().equals(that.getWorkspaces());

  }

  @Override
  public int hashCode() {
    return getWorkspaces().hashCode();
  }
}
