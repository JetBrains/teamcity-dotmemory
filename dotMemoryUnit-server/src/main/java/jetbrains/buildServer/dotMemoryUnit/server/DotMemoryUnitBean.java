package jetbrains.buildServer.dotMemoryUnit.server;

import jetbrains.buildServer.dotMemoryUnit.Constants;
import org.jetbrains.annotations.NotNull;

public class DotMemoryUnitBean {
  public static final DotMemoryUnitBean Shared = new DotMemoryUnitBean();

  @NotNull
  public String getUseDotMemoryUnitKey() {
    return Constants.USE_VAR;
  }

  @NotNull
  public String getPathKey() {
    return Constants.PATH_VAR;
  }

  @NotNull
  public String getWorkspacesPathKey() {
    return Constants.WORKSPACES_PATH_VAR;
  }
}
