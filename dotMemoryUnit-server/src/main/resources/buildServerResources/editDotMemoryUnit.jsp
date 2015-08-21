<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>
<%@ taglib prefix="l" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="forms" tagdir="/WEB-INF/tags/forms" %>
<%@ taglib prefix="bs" tagdir="/WEB-INF/tags"  %>
<jsp:useBean id="propertiesBean" scope="request" type="jetbrains.buildServer.controllers.BasePropertiesBean"/>
<jsp:useBean id="bean" class="jetbrains.buildServer.dotMemoryUnit.server.DotMemoryUnitBean"/>

<script type="text/javascript">
  BS.DotMemoryUnit = {
    updatePathVisibility: function() {
      var useDotMemoryUnitElement = document.getElementById("${bean.useDotMemoryUnitKey}");
      var useDotMemoryUnit = useDotMemoryUnitElement.checked;

      if (useDotMemoryUnit == true) {
        $j('#dotMemoryUnitPathContainer').removeClass("hidden");
        $j('#dotMemoryUnitWorkspacesPathContainer').removeClass("hidden");
      }
      else {
        $j('#dotMemoryUnitPathContainer').addClass("hidden");
        $j('#dotMemoryUnitWorkspacesPathContainer').addClass("hidden");
      }

      BS.VisibilityHandlers.updateVisibility($('dotMemoryUnitPathContainer'));
      BS.VisibilityHandlers.updateVisibility($('dotMemoryUnitWorkspacesPathContainer'));
    }
  }
</script>


<l:settingsGroup title="<a href='http://www.jetbrains.com/dotmemory/unit/' target='JetBrains dotMemory Unit'>JetBrains dotMemory Unit</a>">
  <tr>
    <th><label for="${bean.useDotMemoryUnitKey}">Run build step under JetBrains dotMemory Unit:</label></th>
    <td><props:checkboxProperty name="${bean.useDotMemoryUnitKey}" onclick="BS.DotMemoryUnit.updatePathVisibility()"/>
      <span class="error" id="error_${bean.useDotMemoryUnitKey}"></span>
    </td>
  </tr>

  <tr id="dotMemoryUnitPathContainer" class="hidden">
    <th><label for="${bean.pathKey}">Path to dotMemoryUnit.exe: <l:star/></label></th>
    <td>
      <div class="completionIconWrapper">
        <props:textProperty name="${bean.pathKey}" className="longField"/>
      </div>
      <span class="error" id="error_${bean.pathKey}"></span>
      <span class="smallNote">See the <a href='https://github.com/JetBrains/teamcity-dotmemory/blob/master/README.DOWNLOAD.md' target='JetBrains dotMemory Unit downloading'>instruction</a> to download</span>
    </td>
  </tr>

  <tr id="dotMemoryUnitWorkspacesPathContainer" class="hidden">
    <th><label for="${bean.snapshotsPathKey}">Memory snapshots artifacts path: </label></th>
    <td>
      <div class="completionIconWrapper">
        <props:textProperty name="${bean.snapshotsPathKey}" className="longField"/>
      </div>
      <span class="error" id="error_${bean.snapshotsPathKey}"></span>
      <span class="smallNote">For failed tests JetBrains dotMemory Unit produces a set of memory snapshots. Please specify relative path in build artifacts where these snapshots should be uploaded.</span>
    </td>
  </tr>

</l:settingsGroup>

<script type="text/javascript">
  BS.DotMemoryUnit.updatePathVisibility();
</script>