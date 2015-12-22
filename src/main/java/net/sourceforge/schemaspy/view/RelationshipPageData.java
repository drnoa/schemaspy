package net.sourceforge.schemaspy.view;

/**
 *
 */
public class RelationshipPageData {

    private GlobalData globalData;
    private boolean hasRealRelationships;
    private boolean hasImpliedRelationships;

    private String compactRelationshipsDiagramFileName;
    private String compactRelationshipsDiagram;
    private String largeRelationshipsDiagramFileName;
    private String largeRelationshipsDiagram;
    private String compactImpliedDiagramFileName;
    private String compactImpliedDiagram;
    private String largeImpliedDiagramFileName;
    private String largeImpliedDiagram;

    private String excludedColumns;

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


    public String getCompactRelationshipsDiagramFileName() {
        return compactRelationshipsDiagramFileName;
    }

    public void setCompactRelationshipsDiagramFileName(String compactRelationshipsDiagramFileName) {
        this.compactRelationshipsDiagramFileName = compactRelationshipsDiagramFileName;
    }

    public String getLargeRelationshipsDiagramFileName() {
        return largeRelationshipsDiagramFileName;
    }

    public void setLargeRelationshipsDiagramFileName(String largeRelationshipsDiagramFileName) {
        this.largeRelationshipsDiagramFileName = largeRelationshipsDiagramFileName;
    }

    public String getCompactImpliedDiagramFileName() {
        return compactImpliedDiagramFileName;
    }

    public void setCompactImpliedDiagramFileName(String compactImpliedDiagramFileName) {
        this.compactImpliedDiagramFileName = compactImpliedDiagramFileName;
    }

    public String getLargeImpliedDiagramFileName() {
        return largeImpliedDiagramFileName;
    }

    public void setLargeImpliedDiagramFileName(String largeImpliedDiagramFileName) {
        this.largeImpliedDiagramFileName = largeImpliedDiagramFileName;
    }

    public String getCompactRelationshipsDiagram() {
        return compactRelationshipsDiagram;
    }

    public void setCompactRelationshipsDiagram(String compactRelationshipsDiagram) {
        this.compactRelationshipsDiagram = compactRelationshipsDiagram;
    }

    public String getLargeRelationshipsDiagram() {
        return largeRelationshipsDiagram;
    }

    public void setLargeRelationshipsDiagram(String largeRelationshipsDiagram) {
        this.largeRelationshipsDiagram = largeRelationshipsDiagram;
    }

    public String getCompactImpliedDiagram() {
        return compactImpliedDiagram;
    }

    public void setCompactImpliedDiagram(String compactImpliedDiagram) {
        this.compactImpliedDiagram = compactImpliedDiagram;
    }

    public String getLargeImpliedDiagram() {
        return largeImpliedDiagram;
    }

    public void setLargeImpliedDiagram(String largeImpliedDiagram) {
        this.largeImpliedDiagram = largeImpliedDiagram;
    }

    public String getExcludedColumns() {
        return excludedColumns;
    }

    public void setExcludedColumns(String excludedColumns) {
        this.excludedColumns = excludedColumns;
    }
}
