<p><br><b>Related tables in other schemas</b>

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
<#list tables as table>
	<#include "item.ftl" >
</#list>
</tbody>
</table>