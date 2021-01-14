/*
 * Copyright 2000-2021 JetBrains s.r.o.
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
