<div class='indent'>
        <table class='container' width='100%'>
        <tr><td class='container'>
        <span class='container'>Generated on ${globalData.database.connectTime!}</span>
        </td>
        <td class='container' align='right' valign='top' rowspan='2'>
        
        <table class='legend' border='0'>
       	    <tr>
        	<td class='dataTable' valign='bottom'>Legend:</td>
        <#if globalData.sourceForgeLogoEnabled>
           <td class='container' align='right' valign='top'><a href='http://sourceforge.net' target='_blank'><img src='http://sourceforge.net/sflogo.php?group_id=137197&amp;type=1' alt='SourceForge.net' border='0' height='31' width='88'></a></td>
        </#if>
        	</tr>
        	<tr><td class='container' colspan='2'>
        		<table class='dataTable' border='1'>
        			<tbody>
	        			<tr><td class='primaryKey'>Primary key columns</td></tr>
	        			<tr><td class='indexedColumn'>Columns with indexes</td></tr>
		        		<tr class='impliedRelationship'><td class='detail'><span class='impliedRelationship'>Implied relationships</span></td></tr>
		        		<tr><td class='excludedColumn'>Excluded column relationships</td></tr>
	            		<tr><td class='legendDetail'>&lt; <em>n</em> &gt; number of related tables</td></tr>
        			</tbody>
        		</table>
        	</td></tr>
        </table>
        </td></tr>
        <tr><td class='container' align='left' valign='top'>
        <#if orphansWithImpliedRelationships?? && orphansWithImpliedRelationships?has_content>
            <form action=''>
             <label for='removeImpliedOrphans'><input type=checkbox id='removeImpliedOrphans'>
              Hide tables with implied relationships</label>
            </form>
        </#if>
        </td></tr></table>
		<a name='diagram'>
		<#list orphanTables as orphanTable>
			<#assign cssStyle='' >
            <#if orphansWithImpliedRelationships?? && orphansWithImpliedRelationships?seq_contains(orphanTable)>
            	<#assign cssStyle='impliedNotOrphan' >
            </#if>
            <img src='diagrams/orphans/${orphanTable.orphanDiagramFileName}' usemap='#${orphanTable}' border='0' alt='' align='top' class='${cssStyle}' />
		</#list>
		</a>
		${maps}
</div>
<#include "../include/footer.ftl" >