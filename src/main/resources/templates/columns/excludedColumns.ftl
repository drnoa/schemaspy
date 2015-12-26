<#if columnsNotInDiagram?? && columnsNotInDiagram?has_content>
<span class='excludedRelationship'> <br>Excluded from diagram's relationships:
<#list columnsNotInDiagram as column>
	<#if tableNotInDiagram == null || column.table == tableNotInDiagram>
	 <a href="tables/${column.table.name}.html">${column.table.name}.${column.name}</a>
	</#if>
</#list>
</span>
</#if>