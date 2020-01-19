/*
 * Copyright 2000-2020 JetBrains s.r.o.
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
import jetbrains.buildServer.dotNet.buildRunner.agent.CommandLineSetup;
import org.jetbrains.annotations.NotNull;

public class DotMemoryUnitContext {
  private final CommandLineSetup myBaseSetup;
  private final File mySnapshotDirectory;
  private final File myOutputFile;

  public DotMemoryUnitContext(
    @NotNull final CommandLineSetup baseSetup,
    @NotNull final File snapshotDirectory,
    @NotNull final File outputFile) {
    myBaseSetup = baseSetup;
    mySnapshotDirectory = snapshotDirectory;
    myOutputFile = outputFile;
  }

  @NotNull
  public CommandLineSetup getBaseSetup() {
    return myBaseSetup;
  }

  @NotNull
  public File getOutputFile() {
    return myOutputFile;
  }

  @NotNull
  public File getSnapshotDirectory() {
    return mySnapshotDirectory;
  }

  @NotNull
  @Override
  public String toString() {
    return "DotMemoryUnitContext{" +
           "myBaseSetup=" + myBaseSetup +
           ", mySnapshotDirectory=" + mySnapshotDirectory +
           ", myOutputFile=" + myOutputFile +
           '}';
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    final DotMemoryUnitContext that = (DotMemoryUnitContext)o;

    if (!getBaseSetup().equals(that.getBaseSetup())) return false;
    if (!getSnapshotDirectory().equals(that.getSnapshotDirectory())) return false;
    return getOutputFile().equals(that.getOutputFile());

  }

  @Override
  public int hashCode() {
    int result = getBaseSetup().hashCode();
    result = 31 * result + getSnapshotDirectory().hashCode();
    result = 31 * result + getOutputFile().hashCode();
    return result;
  }
}