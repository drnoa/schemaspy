<table width='100%'>
<tr><td class='container'>
<span class='container'>Generated on ${globalData.database.connectTime!}</span>
</td></tr>
<tr>
<td class='container'></td>
<td class='container' align='right' valign='top' rowspan='3'>
</td>
</tr>
</table>

<table width='100%' border='0'>
	<tr valign='top'><td class='container' align='left' valign='top'>
	<form name='options' action=''>
		<#if hasImplied>
			<#if table.isOrphan(false)>
			<label for='implied'><input type="checkbox" id='implied' checked>Implied relationships</label>
			<#else>
			<label for='implied'><input type="checkbox" id='implied'>Implied relationships</label>
			</#if>
		</#if>
		
		<label for='showRelatedCols'><input type="checkbox" id='showRelatedCols'>Related columns</label>
		<label for='showConstNames'><input type="checkbox" id='showConstNames'>Constraints</label>
		<#if checkShowComments>
			<label for='showComments'><input type="checkbox" checked id='showComments'>Comments</label>
		<#else>	
			<label for='showComments'><input type="checkbox" id='showComments'>Comments</label>
		</#if>
		<label for='showLegend'><input type="checkbox" checked id='showLegend'>Legend</label>
	</form>
</td><td class='container' rowspan='2' align='right' valign='top'>		
        <table class='legend' border='0'>
       	    <tr>
        	<td class='dataTable' valign='bottom'>Legend:</td>
        <#if globalData.sourceForgeLogoEnabled>
           <td class='container' align='right' valign='top'><a href='http://sourceforge.net' target='_blank'><img src='http://sourceforge.net/sflogo.php?group_id=137197&amp;type=1' alt='SourceForge.net' border='0' height='31' width='88'></a></td>
        </#if>
        	</tr>
        	<tr><td class='container' colspan='2'>
        		<table class='dataTable' border='1'>
        			<tbody>
	        			<tr><td class='primaryKey'>Primary key columns</td></tr>
	        			<tr><td class='indexedColumn'>Columns with indexes</td></tr>
		        		<tr class='impliedRelationship'><td class='detail'><span class='impliedRelationship'>Implied relationships</span></td></tr>
		        		<tr><td class='excludedColumn'>Excluded column relationships</td></tr>
	            		<tr><td class='legendDetail'>&lt; <em>n</em> &gt; number of related tables</td></tr>
        			</tbody>
        		</table>
        	</td></tr>
        </table>
</td><tr valign='top'><td class='container' align='left' valign='top'>

<table class='dataTable' border='1' rules='groups'>
	<colgroup>
	<colgroup>
	<colgroup>
	<colgroup>
	<colgroup>
	<colgroup>
	<colgroup>
	<colgroup>
	<colgroup class='comment'>
	<thead align='left'>
		<tr>
		Column 	Type 	Size 	Nulls 	Auto 	Default 	Children 	Parents
			<th valign='bottom'>Column</th>
			<th valign='bottom'>Type</th>
			<th valign='bottom'>Size</th>
			<th valign='bottom'>Nulls</th>
			<th valign='bottom'>Auto</th>
			<th valign='bottom'>Default</th>
			<th valign='bottom'>Children</th>
			<th valign='bottom'>Parents</th>
			<th class='comment' align='left' valign='bottom'>Comments</th>
		</tr>
	</thead>
	<tbody>
		<#list columns as column>
			<#include "item.ftl" >
		</#list>
	</tbody>
</table>
	

<#include "../include/footer.ftl" >