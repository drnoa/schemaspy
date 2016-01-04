<td class='detail'>
${constraint.name}
</td>
<td class='detail'>
<#list constraint.childColumns as childColumn>
    <a href='tables/${childColumn.table.name?url}.html'>
    ${childColumn.table.name}
    </a>.${childColumn.name} <br/>
</#list>
</td>
<td class='detail'>
<#list constraint.parentColumns as parentColumn>
    <a href='tables/${parentColumn.table.name?url}.html'>
    ${parentColumn.table.name}
    </a>.${parentColumn.name} <br/>
</#list>
</td>
<td class='detail'>
    <span title='${constraint.deleteRuleDescription}'>${constraint.deleteRuleName}&nbsp;</span>
</td>
