<#include "../include/header.ftl" >
<table width='100%'>
<#if globalData.sourceForgeLogoEnabled>
	<tr><td class='container' align='right' valign='top' colspan='2'><a href='http://sourceforge.net' target='_blank'><img src='http://sourceforge.net/sflogo.php?group_id=137197&amp;type=1' alt='SourceForge.net' border='0' height='31' width='88'></a></td></tr>
</#if>
<tr><td class='container'><b>Things that might not be 'quite right' about your schema:</b></td></tr>
</table>
<ul>
	<li>
		<b>Columns whose name and type imply a relationship to another table's primary key:</b>
		<#if impliedConstraints?? && impliedConstraints?has_content>
			<table class='dataTable' border='1' rules='groups'>
            <colgroup>
            <colgroup>
            <thead align='left'>
            <tr>
              <th>Child Column</th>
              <th>Implied Parent Column</th>
            </tr>
            </thead>
            <tbody>
            <#list impliedConstraints as impliedConstraint>
            	<tr>
            		<td class='detail'>
            			<a href='tables/${impliedConstraint.childTable.name}.html'>${impliedConstraint.childTable.name}</a>.
            			<#list impliedConstraint.childColumns as childColumn>
            				${childColumn.name}<#if childColumn?has_next>, </#if>
            			</#list>
            		</td>
            		<td class='detail'>
            			<a href='tables/${impliedConstraint.parentTable.name}.html'>${impliedConstraint.parentTable.name}</a>.
            			<#list impliedConstraint.parentColumns as parentColumn>
            				${parentColumn.name}<#if parentColumn?has_next>, </#if>
            			</#list>
            		</td>
            	</tr>
            </#list>
            </tbody>
            </table>
		</#if>
		<#if impliedConstraints?? && impliedConstraints?has_content && impliedConstraints?size == 1>
			1 instance of anomaly detected
		<#elseif impliedConstraints?? && impliedConstraints?has_content && impliedConstraints?size gt 1>
			${impliedConstraints?size} instances of anomaly detected
		<#else>
			Anomaly not detected
		</#if>
		<p/>
	</li>
	<li>
		<b>Tables without indexes:</b>
		<#if unindexedTables?? && unindexedTables?has_content>
			<table class='dataTable' border='1' rules='groups'>
            <colgroup>
            <#if globalData.displayNumRows>
			<colgroup>
			</#if>
            <thead align='left'>
            <tr>
            <th>Table</th>
            <#if globalData.displayNumRows>
			<th>Rows</th>
			</#if>
            </tr>
            </thead>
            <tbody>
            <#list unindexedTables as unindexedTable>
            	<tr>
            		<td class='detail'>
            			<a href='tables/${unindexedTable.name}.html'>${unindexedTable.name}</a>
                	</td>
                	<#if globalData.displayNumRows>
                		<td class='detail' align='right'>
                			${unindexedTable.numRows}
                		</td>
                	</#if>
            	</tr>
            </#list>
            </tbody>
            </table>
		</#if>
		<#if unindexedTables?? && unindexedTables?has_content && unindexedTables?size == 1>
			1 instance of anomaly detected
		<#elseif unindexedTables?? && unindexedTables?has_content && unindexedTables?size gt 1>
			${unindexedTables?size} instances of anomaly detected
		<#else>
			Anomaly not detected
		</#if>
		<p/>
	</li>
	<li>
		<b>Tables that contain a single column:</b>
		<#if tablesWithOneColumn?? && tablesWithOneColumn?has_content>
			<table class='dataTable' border='1' rules='groups'>
            <colgroup>
            <colgroup>
            <thead align='left'>
            <tr>
              <th>Table</th>
              <th>Column</th>
            </tr>
            </thead>
            <tbody>
            <#list tablesWithOneColumn as tableWithOneColumn>
            <tr>
                <td class='detail'>
                	<a href='tables/${tableWithOneColumn.name}.html'>${tableWithOneColumn.name}</a>
                </td>
                <td class='detail'>
                	<#list tableWithOneColumn.columns as column>
                		${column.name}
                	</#list>
                </td>
            </tr>
            </#list>
            </tbody>
            </table>
		</#if>
		<#if tablesWithOneColumn?? && tablesWithOneColumn?has_content && tablesWithOneColumn?size == 1>
			1 instance of anomaly detected
		<#elseif tablesWithOneColumn?? && tablesWithOneColumn?has_content && tablesWithOneColumn?size gt 1>
			${tablesWithOneColumn?size} instances of anomaly detected
		<#else>
			Anomaly not detected
		</#if>
		<p/>
	</li>
	<li>
		<b>Tables with incrementing column names, potentially indicating denormalization:</b>
		<#if tablesWithIncrementingColumnNames?? && tablesWithIncrementingColumnNames?has_content>
			<table class='dataTable' border='1' rules='groups'>
            <thead align='left'>
            <tr>
              <th>Table</th>
            </tr>
            </thead>
            <tbody>
            <#list tablesWithIncrementingColumnNames as tablesWithIncrementingColumnName>
            <tr>
            	<td class='detail'>
                	<a href='tables/${tablesWithIncrementingColumnName.name}.html'>${tablesWithIncrementingColumnName.name}</a>
                </td>
            </tr>
            </#list>
            </tbody>
            </table>
		</#if>
		<#if tablesWithIncrementingColumnNames?? && tablesWithIncrementingColumnNames?has_content && tablesWithIncrementingColumnNames?size == 1>
			1 instance of anomaly detected
		<#elseif tablesWithIncrementingColumnNames?? && tablesWithIncrementingColumnNames?has_content && tablesWithIncrementingColumnNames?size gt 1>
			${tablesWithIncrementingColumnNames?size} instances of anomaly detected
		<#else>
			Anomaly not detected
		</#if>
		<p/>
	</li>
	<li>
		<b>Columns whose default value is the word 'NULL' or 'null', but the SQL NULL value may have been intended:</b>
		<#if defaultNullStringColumns?? && defaultNullStringColumns?has_content>
			<table class='dataTable' border='1' rules='groups'>
            <thead align='left'>
            <tr>
              <th>Column</th>
            </tr>
            </thead>
            <tbody>
            <#list defaultNullStringColumns as defaultNullStringColumn>
            <tr>
            	<td class='detail'>
                	<a href='tables/${defaultNullStringColumn.table.name}.html'>${defaultNullStringColumn.table.name}</a>.${defaultNullStringColumn.name}
                </td>
            </tr>
            </#list>
            </tbody>
            </table>
		</#if>
		<#if defaultNullStringColumns?? && defaultNullStringColumns?has_content && defaultNullStringColumns?size == 1>
			1 instance of anomaly detected
		<#elseif defaultNullStringColumns?? && defaultNullStringColumns?has_content && defaultNullStringColumns?size gt 1>
			${defaultNullStringColumns?size} instances of anomaly detected
		<#else>
			Anomaly not detected
		</#if>
		<p/>
	</li>
</ul>
<#include "../include/footer.ftl" >