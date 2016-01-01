package net.sourceforge.schemaspy.view;

import java.util.Date;
import java.util.List;

public class MultipleSchemasPageData implements PageData{

	private GlobalData globalData;
	private String databaseName;
	private List<String> populatedSchemas;
	private Date connectTime = new Date();
	private String databaseProduct;
	private boolean showIds;
	private String additionalAssetPath;
	
	public GlobalData getGlobalData() {
		return globalData;
	}
	public void setGlobalData(GlobalData globalData) {
		this.globalData = globalData;
	}
	public String getDatabaseName() {
		return databaseName;
	}
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}
	
	public List<String> getPopulatedSchemas() {
		return populatedSchemas;
	}
	public void setPopulatedSchemas(List<String> populatedSchemas) {
		this.populatedSchemas = populatedSchemas;
	}
	public Date getConnectTime() {
		return connectTime;
	}
	public String getDatabaseProduct() {
		return databaseProduct;
	}
	public void setDatabaseProduct(String databaseProduct) {
		this.databaseProduct = databaseProduct;
	}
	public boolean isShowIds() {
		return showIds;
	}
	public void setShowIds(boolean showIds) {
		this.showIds = showIds;
	}
	
	public String getAdditionalAssetPath() {
		return additionalAssetPath;
	}
	public void setAdditionalAssetPath(String additionalAssetPath) {
		this.additionalAssetPath = additionalAssetPath;
	}
	@Override
	public String getCurrentPageName() {
		return "multipleSchemasIndex";
	}
	@Override
	public String getDescriptionHeader() {
		if(databaseName != null && !databaseName.isEmpty()){
			return " Analysis of Database " + databaseName;
		}
		return " Analysis";
	}

	@Override
	public String getDescriptionContent() {
		if(databaseName != null && !databaseName.isEmpty()){
			return " of Database " + databaseName;
		}
		return "";
	}
	

}
