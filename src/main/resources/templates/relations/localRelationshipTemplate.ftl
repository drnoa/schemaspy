<#include "../include/header.ftl" >
<table class='container' width='100%'>
<tr><td class='container'>
<span class='container'>Generated on ${globalData.database.connectTime!}</span>
</td>
    <td class='container' align='right' valign='top' rowspan='2'>
<#assign tableDetails=false>
<#assign diagramDetails=true>
<#assign sourceForgeLogoEnabled=globalData.sourceForgeLogoEnabled>
<#include "../general/legend.ftl" >
    </td></tr>

<#if !hasRealRelationships>
	<tr><td class='container' align='left' valign='top'>
	<#if hasImpliedRelationships>
        No 'real' Foreign Key relationships were detected in the schema.<br>
        Displayed relationships are implied by a column's name/type/size matching another table's primary key.<p>
	<#else>
        No relationships were detected in the schema.
	</#if>
</#if>
    <tr><td class='container' align='left' valign='top'>

<form name='options' action=''>
<#if hasImpliedRelationships>
      <span <#if !hasRealRelationships> style="display:none" </#if>
    title="Show relationships implied by column name/type/size matching another table's primary key">
        <label for='implied'><input type='checkbox' id='implied' <#if !hasRealRelationships> checked="checked"</#if>>
    Implied relationships</label>
      </span>
</#if>
<#if hasRealRelationships || hasImpliedRelationships >
      <span title="By default only columns that are primary keys, foreign keys or indexes are shown">
    <label for='showNonKeys'><input type='checkbox' id='showNonKeys'>
    All columns</label>
      </span>
    </#if>
</form></td></tr></table>
        <table width="100%">
            <tr>
                <td class="container">
                <#if hasRealRelationships>
                    ${compactRelationshipsDiagram}
                    <a name='diagram'><img id='realCompactImg' src='diagrams/summary/${compactRelationshipsDiagramFileName}' usemap='#compactRelationshipsDiagram' class='diagram' border='0' alt=''></a>
                    ${largeRelationshipsDiagram}
                    <a name='diagram'><img id='realLargeImg' src='diagrams/summary/${largeRelationshipsDiagramFileName}' usemap='#largeRelationshipsDiagram' class='diagram' border='0' alt=''></a>
                </#if>
                <#if hasImpliedRelationships>
                    ${compactImpliedDiagram}
                    <a name='diagram'><img id='impliedCompactImg' src='diagrams/summary/${compactImpliedDiagramFileName}' usemap='#compactImpliedRelationshipsDiagram' class='diagram' border='0' alt=''></a>
                    ${largeImpliedDiagram}
                    <a name='diagram'><img id='impliedLargeImg' src='diagrams/summary/${largeImpliedDiagramFileName}' usemap='#largeImpliedRelationshipsDiagram' class='diagram' border='0' alt=''></a>
                </#if>
                </td>
            </tr>
        </table>
        <#assign columnsNotInDiagram=excludedColumns>
        
        <#include "../columns/excludedColumns.ftl" >


    <#include "../include/footer.ftl" >

