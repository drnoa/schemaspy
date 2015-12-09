<#if columnCounter++ % 2 == 0>
<tr class='even'>
<#else>
<tr class='odd'>
</#if>
<#if showIds>
	<td class='detail' align='right'>
		<#if column.id??>${column.id}</#if>
	</td>
</#if>
<#if primaries.contains(column)>
	<td class='primaryKey' title='Primary Key'>
<#elseif indexedColumns.contains(column)>
	<td class='indexedColumn' title='Indexed'>
<#else>	
	<td class='detail'>
</#if>


<td class='comment detail'>
	<#if table.comments??>
		${table.comments}
	</#if>
</td>

</tr>