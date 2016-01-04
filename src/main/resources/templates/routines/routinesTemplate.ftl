<#include "../include/header.ftl" >
<table width='100%'>
<tr>
 <td class='container'>
        <span class='container'>Generated on ${globalData.database.connectTime!}</span>
          </td>
        <#if globalData.sourceForgeLogoEnabled>
			<td class='container' align='right' valign='top' colspan='2'><a href='http://sourceforge.net' target='_blank'><img src='http://sourceforge.net/sflogo.php?group_id=137197&amp;type=1' alt='SourceForge.net' border='0' height='31' width='88'></a></td>
		</#if>       
</tr>
<tr>
	<td class='container'>
	<br><b>${globalData.database.name}
	<#if globalData.database.schema??>
		.${globalData.database.schema}
	<#elseif globalData.database.catalog??>
		.${globalData.database.catalog}
	</#if>
	contains ${numProcs} procedures and ${numFuncs} functions:
	</b><br>
	<div class='indent'>
	<#list routines as routine>
		<a href='#${routine.name?url}'>${routine.name}</a>&nbsp;&nbsp;
	</#list>
	</div>
	</td>
</tr>
<tr>
	<td colspan='3'>
	
	<#list routines as routine>
		<br><a id='${routine.name}'></a><hr>
		<br>
		<code><b>${routine.type} ${routine.name} (
			<#list routine.parameters as parameter>
				<#if parameter.mode??>${parameter.mode} </#if>
				<#if parameter.name??>${parameter.name} </#if>
				<#if parameter.type??>${parameter.type} </#if>
				<#if parameter?has_next>, </#if>
			</#list>
		)
		<#if routine.returnType??>
			RETURNS ${routine.returnType}
		</#if>
		</b>
		<br>
		<#if routine.definitionLanguage??>
			LANGUAGE ${routine.definitionLanguage} <br/>
		</#if>
		<#if routine.function>
			<#if !routine.deterministic>NOT</#if> DETERMINISTIC<br>
		</#if>
		<#if routine.dataAccess??>
			${routine.dataAccess}<br>
		</#if>
		<#if routine.securityType??>
			SQL SECURITY ${routine.securityType}<br>
		</#if>
		<#if routine.comment??>
			COMMENT '${routine.comment}'<br>
		</#if>
		</code>
		<pre>
			${routine.definition}
		</pre>
	</#list>
	</td>
</tr>
</table>
<#include "../include/footer.ftl" >