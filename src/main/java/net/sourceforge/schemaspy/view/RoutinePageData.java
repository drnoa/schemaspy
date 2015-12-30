package net.sourceforge.schemaspy.view;

import java.util.Collection;

import net.sourceforge.schemaspy.model.Routine;

public class RoutinePageData implements PageData{
	private GlobalData globalData;
	private int numProcs;
	private int numFuncs;
	private Collection<Routine> routines;
	
	
	public GlobalData getGlobalData() {
		return globalData;
	}
	public void setGlobalData(GlobalData globalData) {
		this.globalData = globalData;
	}
	
	public int getNumFuncs() {
		return numFuncs;
	}
	public void setNumFuncs(int numFuncs) {
		this.numFuncs = numFuncs;
	}
	public int getNumProcs() {
		return numProcs;
	}
	public void setNumProcs(int numProcs) {
		this.numProcs = numProcs;
	}
	
	public Collection<Routine> getRoutines() {
		return routines;
	}
	public void setRoutines(Collection<Routine> routines) {
		this.routines = routines;
	}
	
	@Override
	public String getCurrentPageName() {
		return "routines";
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
