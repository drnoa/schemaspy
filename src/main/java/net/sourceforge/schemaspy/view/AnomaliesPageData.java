package net.sourceforge.schemaspy.view;

public class AnomaliesPageData implements PageData{
	
	private GlobalData globalData;
	
	
	
	public GlobalData getGlobalData() {
		return globalData;
	}
	public void setGlobalData(GlobalData globalData) {
		this.globalData = globalData;
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
