<div class='indent'>
    <table width='100%'>
    <tr><td class='container' valign='bottom'><b>
	${constraints?size} Foreign Key Constraints:</b>
        </td><td class='container' align='right'>
		<#if globalData.sourceForgeLogoEnabled>
			<td class='container' align='right' valign='top'><a href='http://sourceforge.net' target='_blank'><img src='http://sourceforge.net/sflogo.php?group_id=137197&amp;type=1' alt='SourceForge.net' border='0' height='31' width='88'></a></td>
		</#if>
        </td></tr>
    </table><br>
    <table id='constraints' class='dataTable' border='1' rules='groups'>
    <colgroup>
        <colgroup>
        <colgroup>
        <colgroup>
        <thead align='left'>
        <tr>
              <th>Constraint Name</th>
              <th>Child Column</th>
              <th>Parent Column</th>
              <th>Delete Rule</th>
            </tr>
        </thead>
        <tbody>
		<#list constraints as constraint>
            <#if (constraint?index % 2) == 0>
                <tr class='even'>
            <#else>
                <tr class='odd'>
            </#if>
			    <#include "contraintitem.ftl" >
            </tr>
		</#list>
		<#if constraints?? && !constraints?has_content>
			 <tr>
				  <td class='detail' valign='top' colspan='4'>None detected</td>
			 </tr>
        </#if>
        </tbody>
        </table>
    <a name='checkConstraints'></a><p>
    <b>Check Constraints:</b>
    <table class='dataTable' border='1' rules='groups'>
        <colgroup>
        <colgroup>
        <colgroup>
        <thead align='left'>
        <tr>
              <th>Table</th>
              <th>Constraint Name</th>
              <th>Constraint</th>
            </tr>
        </thead>
        <tbody>

        <#list tables as table>
            <#list table.checkConstraints?keys as checkKey>
                <#include "tableitem.ftl" >
            </#list>
        </#list>
        <#if !checkConstraints>
            <tr>
                <td class='detail' valign='top' colspan='3'>None detected</td>
            </tr>
        </#if>
        </tbody>
        </table>
</div>
<#include "../include/footer.ftl" >