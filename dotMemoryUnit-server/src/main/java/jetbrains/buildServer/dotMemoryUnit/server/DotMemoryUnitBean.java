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
  public String getDotMemoryUnitPathKey() {
    return Constants.PATH_VAR;
  }
}
