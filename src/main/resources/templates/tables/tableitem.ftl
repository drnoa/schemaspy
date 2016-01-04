<tr class='${evenOddStr}'>
<#if showIds>
    <td class='detail' align='right'>
		<#if column.id??>${column.id}</#if>
    </td>
</#if>
<#if showTableNameOnColumnTable>
	<td class='detail'>
		<a href='tables/${column.table.name?url}.html'>Table</a>
    </td>
</#if>

<#if primaries?seq_contains(column)>
	<td class='primaryKey' title='Primary Key'>
<#elseif indexes?seq_contains(column)>
	<td class='indexedColumn' title='Indexed'>
<#else>	
	<td class='detail'>

</#if>
<#if column.name??>
	${column.name}
</#if>
	</td>
    <td class='detail'>
		${column.typeName}
	</td>
    <td class='detail' align='right'>
		${column.detailedSize}
    </td>
<#if column.nullable>
    <td class='detail' align='center' title='nullable'>&nbsp;&radic;&nbsp;</td>
<#else>
    <td class='detail' align='center'></td>
</#if>
<#if column.autoUpdated>
    <td class='detail' align='center' title='Automatically updated by the database'>&nbsp;&radic;&nbsp;</td>
<#else>
    <td class='detail' align='center'></td>
</#if>
    <td class='detail' align='right'>
		<#if column.defaultValue??>${column.defaultValue}</#if>
    </td>
<#if showRelatives>
<td class='detail'>
<#assign dumpParents=false>
<#include "relativesitem.ftl" >
</td>
<td class='detail'>
<#assign dumpParents=true>
<#include "relativesitem.ftl" >
</td>
</#if>
<td class='comment detail'>
	<#if column.comments??>
		${column.comments}
	</#if>
</td>
</tr>
