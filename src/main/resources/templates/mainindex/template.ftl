<table class='dataTable' border='1' rules='groups'>

<colgroup>
<colgroup>
<colgroup>
<colgroup>
<#if showIds>
<colgroup>
</#if>
<#if displayNumRows>
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
<#if displayNumRows>
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
<#if displayNumRows>
<td class='detail'>&nbsp;</td>
</#if>
<td class='comment detail'>&nbsp;</td>
</tr>

<tr class='tbl'>
<td class='detail'><b>${numberOfTables} Tables</b></td>
<td class='detail'>&nbsp;</td>
<td class='detail'>&nbsp;</td>
<td class='detail' align='right'><b>TODO numRows</b></td>
<#if displayNumRows>
<td class='detail'>&nbsp;</td>
</#if>
<td class='comment detail'>&nbsp;</td>
<td class='comment detail'>&nbsp;</td>
 </tr>
<tr class='view'>
<td class='detail'><b>${numberOfViews} Views</b></td>
<td class='detail'>&nbsp;</td>
<td class='detail'>&nbsp;</td>
<td class='detail' align='right'><b>TODO Viewcols</b></td>
<#if displayNumRows>
<td class='detail'>&nbsp;</td>
</#if>
<td class='comment detail'>&nbsp;</td>
<td class='comment detail'>&nbsp;</td>
 </tr>
 </tbody>
 </table>
