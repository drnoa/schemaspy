<#if table.view>
<tr class='view' valign='top'>
<#else>
<tr class='tbl' valign='top'>
</#if>
<td class='detail'>
	<#if table.remote && !oneOfMultipleSchemas>
		${table.container}.${table.name}
	<#else>
		<#if table.remote>
			<a href="../${table.container?url}/index.html">${table.container}</a>
			<a href="../${table.container?url}/tables/${table.name}.html">${table.name}</a>
		<#else>
			<a href="tables/${table.name?url}.html">${table.name}</a>
		</#if>
	</#if>
</td>
 
<#if showIds>
<td class='detail' align='right'>
	<#if table.id??>${table.id}</#if>
</td>
</#if>

<td class='detail' align='right'>
	${table.numNonImpliedChildren}
</td>
<td class='detail' align='right'>
	${table.numNonImpliedParents}
</td>
<#if !table.remote>
<td class='detail' align='right'>
	${table.columns?size}
</td>
<td class='detail' align='right'>
	<#if table.view>
		<span title='Views contain no real rows'>view</span>
	<#else>
		${table.numRows}
	</#if>
</td>
</#if>

<td class='comment detail'>
	<#if table.comments??>
		${table.comments}
	</#if>
</td>
<td class='comment detail'>

<#assign additionalLink = table.getAdditionalInfo('additionalInfoLink')!>
	<#if additionalLink?has_content>
		<a target="new" href="${additionalLink}">Twiki</a>
	</#if>
</td>
</tr>