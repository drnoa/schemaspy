package net.sourceforge.schemaspy.view;

/**
 *
 */
public class RelationshipPageData {

    private GlobalData globalData;
    private boolean hasRealRelationships;
    private boolean hasImpliedRelationships;

    public boolean isHasImpliedRelationships() {
        return hasImpliedRelationships;
    }

    public void setHasImpliedRelationships(boolean hasImpliedRelationships) {
        this.hasImpliedRelationships = hasImpliedRelationships;
    }

    public boolean isHasRealRelationships() {
        return hasRealRelationships;
    }

    public void setHasRealRelationships(boolean hasRealRelationships) {
        this.hasRealRelationships = hasRealRelationships;
    }


    public GlobalData getGlobalData() {
        return globalData;
    }

    public void setGlobalData(GlobalData globalData) {
        this.globalData = globalData;
    }



}
