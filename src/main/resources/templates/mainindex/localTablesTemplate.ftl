<table width='100%'>
<tr><td class='container'>
<span class='container'>Generated on ${globalData.database.connectTime!}</span>
</td></tr>
<tr>
<td class='container'>Database Type:
${globalData.database.databaseProduct!}
</td>
<td class='container' align='right' valign='top' rowspan='3'>

	<#if globalData.sourceForgeLogoEnabled>
			<a href='http://sourceforge.net' target='_blank'><img src='http://sourceforge.net/sflogo.php?group_id=137197&amp;type=1' alt='SourceForge.net' border='0' height='31' width='88'></a><br>
	</#if>

<br>
</td>
</tr>
<tr>
<td class='container'>

<#assign xmlName = globalData.database.name!>
<#if globalData.database.schema??>
	<#assign xmlName = xmlName +'.'+ globalData.database.schema>
<#elseif globalData.database.catalog??>
	<#assign xmlName = xmlName +'.'+ globalData.database.catalog>
</#if>	
<br><a href='${xmlName}.xml' title='XML Representation'>XML Representation</a>
<br><a href='insertionOrder.txt' title='Useful for loading data into a database'>Insertion Order</a>&nbsp;
<a href='deletionOrder.txt' title='Useful for purging data from a database'>Deletion Order</a>
</td>
</tr>
</table>

<div class='indent'>
	<p>
		<b>
			<#if numberOfViews == 0>
				<label for='showTables' style='display:none;'><input type='checkbox' id='showTables' checked></label>
			<#elseif numberOfTables == 0>
				<label for='showViews' style='display:none;'><input type='checkbox' id='showViews' checked></label>
			<#else>
				<label for='showTables'><input type='checkbox' id='showTables' checked>Tables</label>
				<label for='showViews'><input type='checkbox' id='showViews' checked>Views</label>
			</#if>
		
			<#if hasComments??>
				<label for='showComments'><input type=checkbox checked id='showComments'>Comments</label>
			<#else>
				<label for='showComments'><input type=checkbox id='showComments'>Comments</label>
			</#if>
		</b>
	</p>

<table class='dataTable' border='1' rules='groups'>
	<colgroup>
	<colgroup>
	<colgroup>
	<colgroup>
	<#if showIds>
	<colgroup>
	</#if>
	<#if globalData.displayNumRows>
	<colgroup>
	</#if>
	<colgroup class='comment'>
	<colgroup class='comment'>
	
	<thead align='left'>
		<tr>
			<#if numberOfViews == 0>
				<th valign='bottom'>Table</th>
			<#elseif numberOfTables == 0>
				<th valign='bottom'>View</th>
			<#else>
				<th valign='bottom'>Table / View</th>
			</#if>
			
			<#if showIds>
				<th align='center' valign='bottom'>ID</th>
			</#if>
			
			<th align='right' valign='bottom'>Children</th>
			<th align='right' valign='bottom'>Parents</th>
			<th align='right' valign='bottom'>Columns</th>
			<#if globalData.displayNumRows>
				<th align='right' valign='bottom'>Rows</th>
			</#if>
			<th class='comment' align='left' valign='bottom'>Comments</th>
			<th class='comment' align='left' valign='bottom'>Additionalinfo</th>
		</tr>
	</thead>
	<tbody>
		<#list tables as table>
			<#include "item.ftl" >
		</#list>
		<tr>
			<td class='detail'>&nbsp;</td>
			<td class='detail'>&nbsp;</td>
			<td class='detail'>&nbsp;</td>
			<td class='detail'>&nbsp;</td>
			<#if globalData.displayNumRows>
			<td class='detail'>&nbsp;</td>
			</#if>
			<td class='comment detail'>&nbsp;</td>
			</tr>
			
			<tr class='tbl'>
			<td class='detail'><b>${numberOfTables} Tables</b></td>
			<td class='detail'>&nbsp;</td>
			<td class='detail'>&nbsp;</td>
			<td class='detail' align='right'><b>${numberTableCols}</b></td>
			<#if globalData.displayNumRows>
			<td class='detail'>${numberTableRows}</td>
			</#if>
			<td class='comment detail'>&nbsp;</td>
			<td class='comment detail'>&nbsp;</td>
		</tr>
		<tr class='view'>
			<td class='detail'><b>${numberOfViews} Views</b></td>
			<td class='detail'>&nbsp;</td>
			<td class='detail'>&nbsp;</td>
			<td class='detail' align='right'><b>${numberViewCols}</b></td>
			<#if globalData.displayNumRows>
			<td class='detail'>&nbsp;</td>
			</#if>
			<td class='comment detail'>&nbsp;</td>
			<td class='comment detail'>&nbsp;</td>
		</tr>
	</tbody>
</table>

<#if remoteTables?has_content>
	<p><br><b>Related tables in other schemas</b></p>
	
	<table class='dataTable' border='1' rules='groups'>
		<colgroup>
		<colgroup>
		<colgroup>
		<colgroup class='comment'>
		<colgroup class='comment'>
		<thead align='left'>
			<tr>
			<#if showIds>
				<th align='center' valign='bottom'>ID</th>
			</#if>
			
				<th valign='bottom' colspan='2' style='text-align: center;'>In this schema</th>
				<th class='comment' align='left' valign='bottom'>Comments</th>
				<th class='comment' align='left' valign='bottom'>Additionalinfo</th>
			</tr>
			<tr>
				<th align='right' valign='bottom'>Parents</th>
				<th align='right' valign='bottom'>Columns</th>
			</tr>
		</thead>
		<tbody>
			<#list remoteTables as table>
				<#include "item.ftl" >
			</#list>
		</tbody>
	</table>
</#if>
<#include "../include/footer.ftl" >