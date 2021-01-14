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

import com.intellij.openapi.util.text.StringUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import jetbrains.buildServer.dotNet.buildRunner.agent.BuildException;
import jetbrains.buildServer.dotNet.buildRunner.agent.TextParser;
import jetbrains.buildServer.dotNet.buildRunner.agent.XmlDocumentManager;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DotMemoryUnitOutputParser implements TextParser<DotMemoryUnitOutput> {
  private static final String WORKSPACE_SHOULD_NOT_BE_EMPTY_ERROR_MESSAGE = "Error during parsing Dot Memory Unit output xml document: the workspace should not be empty";
  private static final String ERROR_DURING_PARSING_ERROR_MESSAGE = "Error during parsing Dot Memory Unit output xml document";
  private static final DotMemoryUnitOutput ourEmptyOutput = new DotMemoryUnitOutput(Collections.unmodifiableList(Collections.<File>emptyList()));
  private static final String WORKSPACE_XPATH = "//dotMemoryUnitOutput/Workspace";
  private final XmlDocumentManager myXmlDocumentManager;

  public DotMemoryUnitOutputParser(
    @NotNull final XmlDocumentManager xmlDocumentManager) {
    myXmlDocumentManager = xmlDocumentManager;
  }

  public @NotNull DotMemoryUnitOutput parse(@NotNull final String outputText) {
    if(StringUtil.isEmptyOrSpaces(outputText)) {
      return ourEmptyOutput;
    }
    final List<File> workspaces = new ArrayList<File>();
    final Document doc = myXmlDocumentManager.convertStringToDocument(outputText);
    XPath xpath = XPathFactory.newInstance().newXPath();
    try {
      final NodeList workspaceElements = (NodeList)xpath.evaluate(WORKSPACE_XPATH, doc, XPathConstants.NODESET);
      for (int workspaceIndex = 0; workspaceIndex < workspaceElements.getLength(); workspaceIndex++) {
        final Node workspaceElement = workspaceElements.item(workspaceIndex);
        final String workspaceFileName = workspaceElement.getTextContent();
        if(StringUtil.isEmptyOrSpaces(workspaceFileName)) {
          throw new BuildException(WORKSPACE_SHOULD_NOT_BE_EMPTY_ERROR_MESSAGE);
        }

        final File workspaceFile = new File(workspaceFileName);
        workspaces.add(workspaceFile);
      }
    } catch (XPathExpressionException e) {
      throw new BuildException(ERROR_DURING_PARSING_ERROR_MESSAGE);
    }

    return new DotMemoryUnitOutput(workspaces);
  }
}
