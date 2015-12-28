package net.sourceforge.schemaspy.view;

import java.util.List;
import java.util.Set;

import net.sourceforge.schemaspy.model.Table;

/**
 * This Class holds the model for the OrphansPage
 */
public class OrphansPageData implements PageData{
	private GlobalData globalData;
	private List<Table> orphanTables;
	private Set<Table> orphansWithImpliedRelationships;
	private String maps;

    public GlobalData getGlobalData() {
        return globalData;
    }
    public void setGlobalData(GlobalData globalData) {
        this.globalData = globalData;
    }
    
    public List<Table> getOrphanTables() {
        return orphanTables;
    }
    public void setOrphanTables(List<Table> orphanTables) {
        this.orphanTables = orphanTables;
    }
    
    public Set<Table> getOrphansWithImpliedRelationships() {
        return orphansWithImpliedRelationships;
    }
    public void setOrphansWithImpliedRelationships(Set<Table> orphansWithImpliedRelationships) {
        this.orphansWithImpliedRelationships = orphansWithImpliedRelationships;
    }
	public void setMaps(String maps) {
		this.maps = maps;
	}
	public String getMaps(){
		return maps;
	}
	@Override
	public String getCurrentPageName() {
		return "utilities";
	}
	@Override
	public String getDescriptionHeader(){
		return HtmlFormatter.getDescription(globalData.getDatabase(), null, "Utility Tables", false);
	}
	@Override
	public String getDescriptionContent(){
		return HtmlFormatter.getDescription(globalData.getDatabase(), null, "Utility Tables", true);
	}

}
