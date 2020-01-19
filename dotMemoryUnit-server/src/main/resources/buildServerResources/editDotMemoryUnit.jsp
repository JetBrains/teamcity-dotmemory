<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>
<%@ taglib prefix="l" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="forms" tagdir="/WEB-INF/tags/forms" %>
<%@ taglib prefix="bs" tagdir="/WEB-INF/tags"  %>
<%--
  ~ Copyright 2000-2020 JetBrains s.r.o.
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~ http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software
  ~ distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~ See the License for the specific language governing permissions and
  ~ limitations under the License.
  --%>

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

      BS.VisibilityHandlers.updateVisibility($('runnerParams'));
    },

    showHomePage: function() {
      var winSize = BS.Util.windowSize();
      BS.Util.popupWindow('http://www.jetbrains.com/dotmemory/unit/', 'JetBrains dotMemory Unit', { width: 0.9 * winSize[0], height: 0.9 * winSize[1] });
      BS.stopPropagation(event);
    }
  }
</script>

<l:settingsGroup title="JetBrains dotMemory Unit <i class='icon-external-link' title='Open in new window' onclick='BS.DotMemoryUnit.showHomePage()'/i>">
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
      <span class="smallNote">See the <a href='https://github.com/JetBrains/teamcity-dotmemory/blob/master/README.md#agent_deployment'  target='JetBrains dotMemory Unit downloading'>instruction</a> to download</span>
    </td>
  </tr>

  <tr id="dotMemoryUnitWorkspacesPathContainer" class="hidden">
    <th><label for="${bean.snapshotsPathKey}">Memory snapshots artifacts path: <l:star/></label></th>
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