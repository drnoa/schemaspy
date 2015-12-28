package net.sourceforge.schemaspy.view;

import java.util.Collection;


import net.sourceforge.schemaspy.Config;
import net.sourceforge.schemaspy.model.Table;

public class MainIndexPageData implements PageData{
	private GlobalData globalData;
	private boolean showIds;
	
	private Collection<Table> tables;
	private Collection<Table> remoteTables;
	private boolean oneOfMultipleSchemas = Config.getInstance().isOneOfMultipleSchemas();
	private int numberOfTables;
	private int numberOfViews;
	private long numberTableRows;
	private int numberTableCols;
	private int numberViewCols;
	
	public GlobalData getGlobalData() {
		return globalData;
	}
	public void setGlobalData(GlobalData globalData) {
		this.globalData = globalData;
	}
	public boolean isShowIds() {
		return showIds;
	}
	public void setShowIds(boolean showIds) {
		this.showIds = showIds;
	}
	public Collection<Table> getTables() {
		return tables;
	}
	public void setTables(Collection<Table> tables) {
		this.tables = tables;
	}
	public Collection<Table> getRemoteTables() {
		return remoteTables;
	}
	public void setRemoteTables(Collection<Table> remoteTables) {
		this.remoteTables = remoteTables;
	}
	public int getNumberOfTables() {
		return numberOfTables;
	}
	public void setNumberOfTables(int numberOfTables) {
		this.numberOfTables = numberOfTables;
	}
	public int getNumberOfViews() {
		return numberOfViews;
	}
	public void setNumberOfViews(int numberOfViews) {
		this.numberOfViews = numberOfViews;
	}
	public long getNumberTableRows() {
		return numberTableRows;
	}
	public void setNumberTableRows(long numberTableRows) {
		this.numberTableRows = numberTableRows;
	}
	public int getNumberTableCols() {
		return numberTableCols;
	}
	public void setNumberTableCols(int numberTableCols) {
		this.numberTableCols = numberTableCols;
	}
	public int getNumberViewCols() {
		return numberViewCols;
	}
	public void setNumberViewCols(int numberViewCols) {
		this.numberViewCols = numberViewCols;
	}
	public boolean isOneOfMultipleSchemas() {
		return oneOfMultipleSchemas;
	}
	@Override
	public String getCurrentPageName() {
		return "index";
	}
	@Override
	public String getDescriptionHeader(){
		return HtmlFormatter.getDescription(globalData.getDatabase(), null, null, false);
	}
	@Override
	public String getDescriptionContent(){
		return HtmlFormatter.getDescription(globalData.getDatabase(), null, null, true);
	}
}
