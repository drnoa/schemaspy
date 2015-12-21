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
        <label for='implied'><input type='checkbox' id='implied' <#if hasRealRelationships> checked="checked"</#if>>
    Implied relationships</label>
      </span>
</#if>
<#if hasRealRelationships || hasImpliedRelationships >
      <span title="By default only columns that are primary keys, foreign keys or indexes are shown">
    <label for='showNonKeys'><input type='checkbox' id='showNonKeys'>
    All columns</label>
      </span>
    </#if>
</form>
        <table width="100%"><tr><td class="container">

