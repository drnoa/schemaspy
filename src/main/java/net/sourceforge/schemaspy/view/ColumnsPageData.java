package net.sourceforge.schemaspy.view;

import java.util.Collection;
import java.util.Set;

import net.sourceforge.schemaspy.model.TableColumn;

public class ColumnsPageData {
	private GlobalData globalData;
	private boolean containsComments;
	private int numberOfColumns;
	private boolean showIds = false;
	private boolean showTableNameOnColumnTable = true;
	private Collection<TableColumn> columns;
	private Set<TableColumn> indexes;
	private Set<TableColumn> primaries;
	
	public boolean getContainsComments() {
		return containsComments;
	}
	public void setContainsComments(boolean containsComments) {
		this.containsComments = containsComments;
	}
	
	public int getNumberOfColumns() {
		return numberOfColumns;
	}
	public void setNumberOfColumns(int numberOfColumns) {
		this.numberOfColumns = numberOfColumns;
	}
	public GlobalData getGlobalData() {
		return globalData;
	}
	public void setGlobalData(GlobalData globalData) {
		this.globalData = globalData;
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
}
