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

import java.util.HashMap;
import java.util.Map;
import javax.xml.transform.OutputKeys;
import jetbrains.buildServer.dotNet.buildRunner.agent.CommandLineArgumentsService;
import jetbrains.buildServer.dotNet.buildRunner.agent.FileService;
import jetbrains.buildServer.dotNet.buildRunner.agent.ResourceGenerator;
import jetbrains.buildServer.dotNet.buildRunner.agent.XmlDocumentManager;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DotMemoryUnitProjectGenerator implements ResourceGenerator<DotMemoryUnitContext> {
  private static final Map<String, String> outDocumentProperties;
  private static final String NO_VAL = "no";
  private static final String ROOT_ELEMENT = "dotMemoryUnit";
  private static final String TARGET_EXECUTABLE_ELEMENT = "TargetExecutable";
  private static final String TARGET_ARGUMENTS_ELEMENT = "TargetArguments";
  private static final String TARGET_WORKING_DIR_ELEMENT = "TargetWorkingDir";
  private static final String INHERIT_CONSOLE_ELEMENT = "InheritConsole";
  private static final String RETURN_TARGET_EXIT_CODE_ELEMENT = "ReturnTargetExitCode";
  private static final String TEMP_DIR_ELEMENT = "TempDir";
  private static final String OVERRIDE_WORKSPACE_DIR_ELEMENT = "OverrideWorkspaceDir";
  private static final String OUTPUT_FILE_PATH_ELEMENT = "OutputFilePath";
  private static final String INHERIT_CONSOLE_ARG_VAL = "True";
  private static final String DMU_MODE_ELEMENT = "dmuMode";
  private static final String DMU_MODE_TEAM_CITY = "TeamCity";

  private final XmlDocumentManager myDocumentManager;
  private final CommandLineArgumentsService myCommandLineArgumentsService;
  private final FileService myFileService;

  static {
    outDocumentProperties = new HashMap<String, String>();
    outDocumentProperties.put(OutputKeys.OMIT_XML_DECLARATION, NO_VAL);
  }

  public DotMemoryUnitProjectGenerator(
    @NotNull final XmlDocumentManager documentManager,
    @NotNull final CommandLineArgumentsService commandLineArgumentsService,
    @NotNull final FileService fileService) {
    myDocumentManager = documentManager;
    myCommandLineArgumentsService = commandLineArgumentsService;
    myFileService = fileService;
  }

  @Override
  @NotNull
  public String create(@NotNull final DotMemoryUnitContext ctx) {
    final Document doc = myDocumentManager.createDocument();
    Element rootElement = doc.createElement(ROOT_ELEMENT);
    rootElement.appendChild(createSimpleElement(doc, TARGET_EXECUTABLE_ELEMENT, ctx.getBaseSetup().getToolPath()));
    rootElement.appendChild(createSimpleElement(doc, TARGET_ARGUMENTS_ELEMENT, myCommandLineArgumentsService.createCommandLineString(ctx.getBaseSetup().getArgs())));
    rootElement.appendChild(createSimpleElement(doc, TARGET_WORKING_DIR_ELEMENT, myFileService.getCheckoutDirectory().getPath()));
    rootElement.appendChild(createSimpleElement(doc, INHERIT_CONSOLE_ELEMENT, INHERIT_CONSOLE_ARG_VAL));
    rootElement.appendChild(createSimpleElement(doc, RETURN_TARGET_EXIT_CODE_ELEMENT, ""));
    rootElement.appendChild(createSimpleElement(doc, TEMP_DIR_ELEMENT, myFileService.getTempDirectory().getPath()));
    rootElement.appendChild(createSimpleElement(doc, OVERRIDE_WORKSPACE_DIR_ELEMENT, ctx.getSnapshotDirectory().getPath()));
    rootElement.appendChild(createSimpleElement(doc, OUTPUT_FILE_PATH_ELEMENT, ctx.getOutputFile().getPath()));
    rootElement.appendChild(createSimpleElement(doc, DMU_MODE_ELEMENT, DMU_MODE_TEAM_CITY));
    doc.appendChild(rootElement);
    return myDocumentManager.convertDocumentToString(doc, outDocumentProperties);
  }

  @NotNull
  private static Element createSimpleElement(@NotNull final Document doc, @NotNull final String name, @NotNull final String value) {
    Element executableElement = doc.createElement(name);
    executableElement.setTextContent(value);
    return executableElement;
  }
}
