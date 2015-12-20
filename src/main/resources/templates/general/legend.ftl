         <table class='legend' border='0'>
          <tr>
           <td class='dataTable' valign='bottom'>Legend:</td>
        <#if sourceForgeLogoEnabled>
               <td class='container' align='right' valign='top'><a href='http://sourceforge.net' target='_blank'><img src='http://sourceforge.net/sflogo.php?group_id=137197&amp;type=1' alt='SourceForge.net' border='0' height='31' width='88'></a></td>
        </#if>
          </tr>
          <tr><td class='container' colspan='2'>
           <table class='dataTable' border='1'>
            <tbody>
            <tr><td class='primaryKey'>Primary key columns</td></tr>
            <tr><td class='indexedColumn'>Columns with indexes</td></tr>
        <#if tableDetails>
                <tr class='impliedRelationship'><td class='detail'><span class='impliedRelationship'>Implied relationships</span></td></tr>
        </#if>
        <#if diagramDetails>
                <tr><td class='excludedColumn'>Excluded column relationships</td></tr>
            <#if !tableDetails>
                    <tr class='impliedRelationship'><td class='legendDetail'>Dashed lines show implied relationships</td></tr>
            </#if>
                <tr><td class='legendDetail'>&lt; <em>n</em> &gt; number of related tables</td></tr>
        </#if>
            </tbody>
           </table>
          </td></tr>
         </table>