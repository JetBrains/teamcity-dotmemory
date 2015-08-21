<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>
<%@ taglib prefix="l" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="forms" tagdir="/WEB-INF/tags/forms" %>
<%@ taglib prefix="bs" tagdir="/WEB-INF/tags"  %>
<jsp:useBean id="propertiesBean" scope="request" type="jetbrains.buildServer.controllers.BasePropertiesBean"/>
<jsp:useBean id="bean" class="jetbrains.buildServer.dotMemoryUnit.server.DotMemoryUnitBean"/>

<l:settingsGroup title="<a href='http://www.jetbrains.com/dotmemory/unit/' target='JetBrains dotMemory Unit'>JetBrains dotMemory Unit</a>">
  <tr>
    <th><label for="${bean.useDotMemoryUnitKey}">Run build step under JetBrains dotMemory Unit:</label></th>
    <td><props:checkboxProperty name="${bean.useDotMemoryUnitKey}"/>
      <span class="error" id="error_${bean.useDotMemoryUnitKey}"></span>
    </td>
  </tr>

  <tr>
    <th><label for="${bean.dotMemoryUnitPathKey}">Path to dotMemoryUnit.exe: <l:star/></label></th>
    <td>
      <div class="completionIconWrapper">
        <props:textProperty name="${bean.dotMemoryUnitPathKey}" className="longField"/>
      </div>
      <span class="error" id="error_${bean.dotMemoryUnitPathKey}"></span>
      <span class="smallNote">See the <a href='https://github.com/JetBrains/teamcity-dotmemory/blob/master/README.DOWNLOAD.md' target='JetBrains dotMemory Unit downloading'>instruction</a> to download</span>
    </td>
  </tr>

</l:settingsGroup>