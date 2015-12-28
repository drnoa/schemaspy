<#include "../include/header.ftl" >
<SCRIPT LANGUAGE='JavaScript' TYPE='text/javascript'>
            table='${table.name}';
</SCRIPT>

<table width='100%'>
<tr><td class='container'>
<span class='container'>Generated on ${globalData.database.connectTime!}</span>
</td></tr>
<tr>
<td class='container'></td>
<td class='container' align='right' valign='top' rowspan='3'>
</td>
</tr>
</table>

<table width='100%' border='0'>
	<tr valign='top'><td class='container' align='left' valign='top'>
	<form name='options' action=''>
		<#if hasImplied>
			<#if table.isOrphan(false)>
			<label for='implied'><input type="checkbox" id='implied' checked>Implied relationships</label>
			<#else>
			<label for='implied'><input type="checkbox" id='implied'>Implied relationships</label>
			</#if>
		</#if>
		
		<label for='showRelatedCols'><input type="checkbox" id='showRelatedCols'>Related columns</label>
		<label for='showConstNames'><input type="checkbox" id='showConstNames'>Constraints</label>
		<#if checkShowComments>
			<label for='showComments'><input type="checkbox" checked id='showComments'>Comments</label>
		<#else>	
			<label for='showComments'><input type="checkbox" id='showComments'>Comments</label>
		</#if>
		<label for='showLegend'><input type="checkbox" checked id='showLegend'>Legend</label>
	</form>
</td><td class='container' rowspan='2' align='right' valign='top'>
<#assign tableDetails=true>
<#assign diagramDetails=true>
<#assign sourceForgeLogoEnabled=globalData.sourceForgeLogoEnabled>
<#include "../general/legend.ftl" >
</td><tr valign='top'><td class='container' align='left' valign='top'>

<table class='dataTable sortedDataTable' border='1' rules='groups'>
	<colgroup>
	<colgroup>
	<colgroup>
	<colgroup>
	<colgroup>
	<colgroup>
	<colgroup>
	<colgroup>
	<colgroup class='comment'>
	<thead align='left'>
		<tr>
			<th valign='bottom'>Column</th>
			<th valign='bottom'>Type</th>
			<th valign='bottom'>Size</th>
			<th valign='bottom'>Nulls</th>
			<th valign='bottom'>Auto</th>
			<th valign='bottom'>Default</th>
			<th valign='bottom'>Children</th>
			<th valign='bottom'>Parents</th>
			<th class='comment' align='left' valign='bottom'>Comments</th>
		</tr>
	</thead>
	<tbody>
		<#list columns as column>
			<#if (column?index % 2) ==0>
            <tr class='even'>
			<#else>
            <tr class='odd'>
			</#if>
			<#include "tableitem.ftl" >
			</tr>
		</#list>
	</tbody>
</table>
	<p title='${columns?size} columns'>
        Table contained ${table.numRows} Rows at ${globalData.database.connectTime}
	</p>

<#if table.checkConstraints?? && table.checkConstraints?has_content>
<!-- Check constraints-->
    <div class='indent'>
    	<b>Requirements (check constraints):</b>
        <table class='dataTable' border='1' rules='groups'><colgroup><colgroup>
        	<thead>
				<tr>
                    <th>Constraint</th>
                    <th class='constraint' style='text-align:left;'>Constraint Name</th>
                </tr>
            </thead>
			<tbody>
				<#list table.checkConstraints?keys as constraintKey>
                	<td class='detail'>${table.checkConstraints[constraintKey]}</td>
                	<td class='constraint' style='text-align:left;'>${constraintKey}</td>
				</#list>
			</tbody>
        <tbody>
	</div>
</#if>
<#if table.indexes?? && table.indexes?has_content>
    <!-- indexes-->
    <div class='indent'>
        <b>Indexes:</b>
        <table class='dataTable' border='1' rules='groups'>
            <thead>
            <tr>
			<#if showIds>
                <th>ID</th>
			</#if>
                <th>Column(s)</th>
                <th>Type</th>
                <th>Sort</th>
                <th class='constraint' style='text-align:left;'>Constraint Name</th>
            </tr>
            </thead>
            <tbody>
			<#list table.indexes as index>
				<tr>
					<#if showIds>
                    	<td class='detail' align='right'>
							<#if index.id??>${index.id}</#if>
						</td>
					</#if>
					<#if index.primaryKey>
                    <td class='primaryKey'>
					<#else>
                    <td class='indexedColumn'>
					</#if>
						${index.columnsAsString}
					</td>
                    <td class='detail'>
                    ${index.type}
                    </td>
                    <td class='detail' style='text-align:left;'>
						<#list index.columns as indexColumn>
							<#if index.isAscending(indexColumn)>
                                <span title='Ascending'>Asc</span>
							<#else>
                                <span title='Descending'>Desc</span>
							</#if>
							/
						</#list>
					</td>
                    <td class='constraint' style='text-align:left;'>
						${index.name}
					</td>

				</tr>
			</#list>
			</tbody>
        </table>
    </div>
</#if>
<#if table.view && table.viewSql??>
    <!-- view -->
    <div class='indent spacer'>
        <b>View Definition:</b>
		<pre>${table.viewSqlFormated}</pre>
	</div>
</#if>
<#if table.additionalInfo?? && table.additionalInfo?has_content>
    <!-- additionalInfo -->
    <div class='indent'>
        <b>Additional Info:</b><br/>
		<#list table.additionalInfo as addInfo>
            <a href="../${addInfo.value}">Additional Info</a><br/>
		</#list>
	</div>
</#if>
<#include "../include/footer.ftl" >