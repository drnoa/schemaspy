<#if columnCounter % 2 == 1>
<tr class='even'>
<#else>
<tr class='odd'>
</#if>
<#if showIds>
	<td class='detail' align='right'>
		<#if column.id??>${column.id}</#if>
	</td>
</#if>
<#if primaries?seq_contains(column)>
	<td class='primaryKey' title='Primary Key'>

    </td>
<#elseif indexes?seq_contains(column)>
	<td class='indexedColumn' title='Indexed'>
    </td>
<#else>	
	<td class='detail'>
    </td>
</#if>


<td class='comment detail'>
	<#if table.comments??>
		${table.comments}
	</#if>
</td>

</tr>