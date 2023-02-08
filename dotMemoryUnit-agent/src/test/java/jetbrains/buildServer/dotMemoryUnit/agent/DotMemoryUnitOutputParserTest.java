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
import java.util.Arrays;
import java.util.Collections;

import jetbrains.buildServer.dotNet.buildRunner.agent.TextParser;
import jetbrains.buildServer.dotNet.buildRunner.agent.XmlDocumentManagerImpl;
import org.jetbrains.annotations.NotNull;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.assertj.core.api.BDDAssertions.then;

public class DotMemoryUnitOutputParserTest {
  @DataProvider(name = "parseDotMemoryUnitOutputFromXmlCases")
  public Object[][] getParseDotMemoryUnitOutputFromXmlCases() {
    return new Object[][] {
     { ourXmlSample,
        new DotMemoryUnitOutput(Arrays.asList(
          new File("C:\\Users\\edward.pavlov\\AppData\\Local\\Temp\\dotMemoryUnitWorkspace\\9368c4e6-8ab2-407c-a8b5-c2e4669984d5\\Begeqez\\FailOnCheckTest.dmw"),
          new File("C:\\Users\\edward.pavlov\\AppData\\Local\\Temp\\dotMemoryUnitWorkspace\\9368c4e6-8ab2-407c-a8b5-c2e4669984d5\\Vykijuj\\SaveOnSimpleFailTest.dmw"),
          new File("C:\\Users\\edward.pavlov\\AppData\\Local\\Temp\\dotMemoryUnitWorkspace\\9368c4e6-8ab2-407c-a8b5-c2e4669984d5\\Xeryzig\\FailOnCheckTest.dmw"),
          new File("C:\\Users\\edward.pavlov\\AppData\\Local\\Temp\\dotMemoryUnitWorkspace\\9368c4e6-8ab2-407c-a8b5-c2e4669984d5\\Dokyfog\\SaveOnSimpleFailTest.dmw")))},
      { ourXmlWithoutDeclarationSample,
        new DotMemoryUnitOutput(Arrays.asList(
          new File("C:\\Users\\edward.pavlov\\AppData\\Local\\Temp\\dotMemoryUnitWorkspace\\9368c4e6-8ab2-407c-a8b5-c2e4669984d5\\Begeqez\\FailOnCheckTest.dmw"),
          new File("C:\\Users\\edward.pavlov\\AppData\\Local\\Temp\\dotMemoryUnitWorkspace\\9368c4e6-8ab2-407c-a8b5-c2e4669984d5\\Vykijuj\\SaveOnSimpleFailTest.dmw"),
          new File("C:\\Users\\edward.pavlov\\AppData\\Local\\Temp\\dotMemoryUnitWorkspace\\9368c4e6-8ab2-407c-a8b5-c2e4669984d5\\Xeryzig\\FailOnCheckTest.dmw"),
          new File("C:\\Users\\edward.pavlov\\AppData\\Local\\Temp\\dotMemoryUnitWorkspace\\9368c4e6-8ab2-407c-a8b5-c2e4669984d5\\Dokyfog\\SaveOnSimpleFailTest.dmw")))},
      { "", new DotMemoryUnitOutput(Collections.<File>emptyList()) },
    };
  }

  @Test(dataProvider = "parseDotMemoryUnitOutputFromXmlCases")
  public void shouldParseDotMemoryUnitOutputFromXml(@NotNull final String text, @NotNull final DotMemoryUnitOutput expectedOutput)
  {
    final TextParser<DotMemoryUnitOutput> instance = createInstance();

    // When
    final DotMemoryUnitOutput actualOutput = instance.parse(text);

    // Then
    then(actualOutput).isEqualTo(expectedOutput);
  }

  @NotNull
  private TextParser<DotMemoryUnitOutput> createInstance()
  {
    return new DotMemoryUnitOutputParser(new XmlDocumentManagerImpl());
  }

  private static final String ourXmlSample = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"no\"?>\n" +
                                             "<dotMemoryUnitOutput>\n" +
                                             "  <Workspace Size=\"6311229\">C:\\Users\\edward.pavlov\\AppData\\Local\\Temp\\dotMemoryUnitWorkspace\\9368c4e6-8ab2-407c-a8b5-c2e4669984d5\\Begeqez\\FailOnCheckTest.dmw</Workspace>\n" +
                                             "  <Workspace Size=\"1891\">C:\\Users\\edward.pavlov\\AppData\\Local\\Temp\\dotMemoryUnitWorkspace\\9368c4e6-8ab2-407c-a8b5-c2e4669984d5\\Vykijuj\\SaveOnSimpleFailTest.dmw</Workspace>\n" +
                                             "  <Workspace Size=\"6334943\">C:\\Users\\edward.pavlov\\AppData\\Local\\Temp\\dotMemoryUnitWorkspace\\9368c4e6-8ab2-407c-a8b5-c2e4669984d5\\Xeryzig\\FailOnCheckTest.dmw</Workspace>\n" +
                                             "  <Workspace Size=\"1891\">C:\\Users\\edward.pavlov\\AppData\\Local\\Temp\\dotMemoryUnitWorkspace\\9368c4e6-8ab2-407c-a8b5-c2e4669984d5\\Dokyfog\\SaveOnSimpleFailTest.dmw</Workspace>\n" +
                                             "</dotMemoryUnitOutput>";

  private static final String ourXmlWithoutDeclarationSample = "<dotMemoryUnitOutput>\n" +
                                             "  <Workspace Size=\"6311229\">C:\\Users\\edward.pavlov\\AppData\\Local\\Temp\\dotMemoryUnitWorkspace\\9368c4e6-8ab2-407c-a8b5-c2e4669984d5\\Begeqez\\FailOnCheckTest.dmw</Workspace>\n" +
                                             "  <Workspace Size=\"1891\">C:\\Users\\edward.pavlov\\AppData\\Local\\Temp\\dotMemoryUnitWorkspace\\9368c4e6-8ab2-407c-a8b5-c2e4669984d5\\Vykijuj\\SaveOnSimpleFailTest.dmw</Workspace>\n" +
                                             "  <Workspace Size=\"6334943\">C:\\Users\\edward.pavlov\\AppData\\Local\\Temp\\dotMemoryUnitWorkspace\\9368c4e6-8ab2-407c-a8b5-c2e4669984d5\\Xeryzig\\FailOnCheckTest.dmw</Workspace>\n" +
                                             "  <Workspace Size=\"1891\">C:\\Users\\edward.pavlov\\AppData\\Local\\Temp\\dotMemoryUnitWorkspace\\9368c4e6-8ab2-407c-a8b5-c2e4669984d5\\Dokyfog\\SaveOnSimpleFailTest.dmw</Workspace>\n" +
                                             "</dotMemoryUnitOutput>";
}
