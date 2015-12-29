<#include "../include/header.ftl" >
<table width='100%' border='0'>
    <tr><td class='container'>
        <span class='container'>Generated on ${globalData.database.connectTime!}</span>
        </td>
        <td class='container' rowspan='2' align='right' valign='top'>
        <#assign tableDetails=false>
        <#assign diagramDetails=false>
        <#assign sourceForgeLogoEnabled=globalData.sourceForgeLogoEnabled>
        <#include "../general/legend.ftl" >
        </td>
    </tr>
    <tr valign='top'>
        <td class='container' align='left' valign='top'>
            <p>
        <form name='options' action=''>
        <#if containsComments>
        	<label for='showComments'><input type=checkbox id='showComments'>Comments</label>
        </#if>
             <label for='showLegend'><input type=checkbox checked id='showLegend'>Legend</label>
        </form>
        </p>
        </td>
        </tr>
</table>

<div class='indent'>
        <b>
        ${globalData.database.fullName}
         contains 
        ${numberOfColumns}
         columns</b> - click on heading to sort:
         
         <a name='columns'></a>
         <table id='columns' class='dataTable sortedDataTable' border='1' rules='groups'>
         <thead align='left'>
         <tr>
         	<th>Table</th>
			<th>Column</th>
			<th>Type</th>
			<th>Size</th>
			<th title='Are nulls allowed?'>Nulls</th>
			<th title='Is column automatically updated?'>Auto</th>
			<th title='Default value'>Default</th>
         </tr>
         </thead>
        <tbody valign='top'>
			<#list columns as column>
				<#if (column?index % 2) ==0>
					<#assign evenOddStr='even'>
				<#else>
					<#assign evenOddStr='odd'>
				</#if>
				<#assign showRelatives=false>
				<#include "../tables/tableitem.ftl" >
		</#list>
        </tbody>
        </table>
</div>
<#include "../include/footer.ftl" >