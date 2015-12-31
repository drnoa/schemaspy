package net.sourceforge.schemaspy.view;

import java.util.List;

import net.sourceforge.schemaspy.model.ForeignKeyConstraint;
import net.sourceforge.schemaspy.model.Table;
import net.sourceforge.schemaspy.model.TableColumn;

public class AnomaliesPageData implements PageData{
	
	private GlobalData globalData;
	private List<? extends ForeignKeyConstraint> impliedConstraints;
	private List<Table> unindexedTables;
	private List<Table> tablesWithOneColumn;
	private List<Table> tablesWithIncrementingColumnNames;
	private List<TableColumn> defaultNullStringColumns;
	
	public GlobalData getGlobalData() {
		return globalData;
	}
	public void setGlobalData(GlobalData globalData) {
		this.globalData = globalData;
	}
	
	public List<? extends ForeignKeyConstraint> getImpliedConstraints() {
		return impliedConstraints;
	}
	public void setImpliedConstraints(List<? extends ForeignKeyConstraint> impliedConstraints) {
		this.impliedConstraints = impliedConstraints;
	}
	public List<Table> getUnindexedTables() {
		return unindexedTables;
	}
	public void setUnindexedTables(List<Table> unindexedTables) {
		this.unindexedTables = unindexedTables;
	}
	
	public List<Table> getTablesWithOneColumn() {
		return tablesWithOneColumn;
	}
	public void setTablesWithOneColumn(List<Table> tablesWithOneColumn) {
		this.tablesWithOneColumn = tablesWithOneColumn;
	}
	public List<Table> getTablesWithIncrementingColumnNames() {
		return tablesWithIncrementingColumnNames;
	}
	public void setTablesWithIncrementingColumnNames(List<Table> tablesWithIncrementingColumnNames) {
		this.tablesWithIncrementingColumnNames = tablesWithIncrementingColumnNames;
	}
	public List<TableColumn> getDefaultNullStringColumns() {
		return defaultNullStringColumns;
	}
	public void setDefaultNullStringColumns(List<TableColumn> defaultNullStringColumns) {
		this.defaultNullStringColumns = defaultNullStringColumns;
	}
	
	@Override
	public String getCurrentPageName() {
		return "anomalies";
	}
	
	@Override
	public String getDescriptionHeader(){
		return HtmlFormatter.getDescription(globalData.getDatabase(), null, "Anomalies", false);
	}
	@Override
	public String getDescriptionContent(){
		return HtmlFormatter.getDescription(globalData.getDatabase(), null, "Anomalies", true);
	}
}
