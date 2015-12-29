<#if dumpParents>
    <#assign baseRelativesColumns=column.parents>
<#else>
    <#assign baseRelativesColumns=column.children>
</#if>

<#if baseRelativesColumns??>
<table border='0' width='100%' cellspacing='0' cellpadding='0'>
	<#list baseRelativesColumns as relativeColumn>
		<#if dumpParents>
    		<#assign constraint=column.getParentConstraint(relativeColumn)>
		<#else>
    		<#assign constraint=column.getChildConstraint(relativeColumn)>
		</#if>
		<tr class='<#if constraint.implied>impliedRelationship</#if> relative ${evenOddStr}' valign='top'>
			<td class='relatedTable detail' title="${constraint}">
			<#if relativeColumn.table.remote && !globalData.config.oneOfMultipleSchemas>
				${relativeColumn.table.container}.${relativeColumn.table.name}
			<#else>
				<#if relativeColumn.table.remote>
					<a href='${relativeColumn.table.container}/index.html'></a>.
				</#if>
				<a href='<#if relativeColumn.table.remote>../../${relativeColumn.table.container}/tables/</#if>.html'>${relativeColumn.table.name}</a>
			</#if>
			<span class='relatedKey'>.${relativeColumn.name}</span>
			</td>
			<td class='constraint detail'>
				${constraint.name}
				<#if constraint.deleteRuleDescription??>
					<span title='${constraint.deleteRuleDescription}'>&nbsp;${constraint.deleteRuleAlias}</span>
				</#if>
			</td>
		</tr>
	</#list>
</table>
</#if>