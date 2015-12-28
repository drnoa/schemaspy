package net.sourceforge.schemaspy.view;

import java.util.Collection;
import java.util.Set;

import net.sourceforge.schemaspy.model.Table;
import net.sourceforge.schemaspy.model.TableColumn;


public class TablePageData implements PageData{
	private GlobalData globalData;

	private Table table;

	private boolean hasImplied = false;
	private boolean checkShowComments = false;
	private boolean showIds = false;

	private boolean showTableNameOnColumnTable = false;
	
	private Collection<TableColumn> columns;
	private Set<TableColumn> indexes;
	private Set<TableColumn> primaries;
	
	public GlobalData getGlobalData() {
		return globalData;
	}
	public void setGlobalData(GlobalData globalData) {
		this.globalData = globalData;
	}
	public Table getTable() {
		return table;
	}
	public void setTable(Table table) {
		this.table = table;
	}
	public void setHasImplied(boolean hasImplied) {
		this.hasImplied = hasImplied;
	}
	public boolean getHasImplied() {
		return hasImplied;
	}
	public boolean getCheckShowComments() {
		return checkShowComments;
	}
	public void setCheckShowComments(boolean checkShowComments) {
		this.checkShowComments = checkShowComments;
	}
	public void setShowIds(boolean showIds) {
		this.showIds = showIds;
		
	}
	public boolean getShowIds() {
		return this.showIds;
	}
	public Set<TableColumn> getPrimaries() {
		return primaries;
	}
	public void setPrimaries(Set<TableColumn> primaries) {
		this.primaries = primaries;
	}
	public Set<TableColumn> getIndexes() {
		return indexes;
	}
	public void setIndexes(Set<TableColumn> indexes) {
		this.indexes = indexes;
	}
	public Collection<TableColumn> getColumns() {
		return columns;
	}
	public void setColumns(Collection<TableColumn> columns) {
		this.columns = columns;
	}
	public boolean isShowTableNameOnColumnTable() {
		return showTableNameOnColumnTable;
	}

	public void setShowTableNameOnColumnTable(boolean showTableNameOnColumnTable) {
		this.showTableNameOnColumnTable = showTableNameOnColumnTable;
	}
	
	@Override
	public String getCurrentPageName() {
		return "table";
	}
	
	@Override
	public String getDescriptionHeader(){
		return HtmlFormatter.getDescription(globalData.getDatabase(), table, null, false);
	}
	@Override
	public String getDescriptionContent(){
		return HtmlFormatter.getDescription(globalData.getDatabase(), table, null, true);
	}
}
