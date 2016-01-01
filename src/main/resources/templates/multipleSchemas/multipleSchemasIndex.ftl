<#include "../include/header.ftl" >
<table width='100%'>
<tr><td class='container'>
<span class='container'>Generated on ${connectTime?datetime}</span>
</td></tr>
<tr>
<td class='container'>
<#if databaseProduct?? && databaseProduct?has_content>Database Type: ${databaseProduct}</#if>
</td>
<td class='container' align='right' valign='top' rowspan='3'>
	<#if globalData.sourceForgeLogoEnabled>
			<a href='http://sourceforge.net' target='_blank'><img src='http://sourceforge.net/sflogo.php?group_id=137197&amp;type=1' alt='SourceForge.net' border='0' height='31' width='88'></a><br>
	</#if>
<br>
</td>
</tr>
</table>
<div class='indent'>
<b>${populatedSchemas?size}<#if databaseName?has_content>Schema<#else>Database</#if><#if populatedSchemas?size gt 1>s</#if>:</b>
<table class='dataTable' border='1' rules='groups'>
<colgroup>
<thead align='left'>
<tr>
<th valign='bottom'>
	<#if databaseName?has_content>Schema<#else>Database</#if>
</th>
</tr>
</thead>
<tbody>
<#list populatedSchemas as schema>
<tr>
	<td class='detail'><a href='${schema}/index.html'>${schema}</a></td>
</tr>
</#list>
</tbody>
</table>
<#include "../include/footer.ftl" >