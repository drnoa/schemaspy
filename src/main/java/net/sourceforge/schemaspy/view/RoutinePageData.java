package net.sourceforge.schemaspy.view;

public class RoutinePageData implements PageData{
	private GlobalData globalData;
	
	
	public GlobalData getGlobalData() {
		return globalData;
	}
	public void setGlobalData(GlobalData globalData) {
		this.globalData = globalData;
	}
	
	@Override
	public String getCurrentPageName() {
		return "table";
	}
	
	@Override
	public String getDescriptionHeader(){
		return HtmlFormatter.getDescription(globalData.getDatabase(), null, "Procedures and Functions", false);
	}
	@Override
	public String getDescriptionContent(){
		return HtmlFormatter.getDescription(globalData.getDatabase(), null, "Procedures and Functions", true);
	}
}
