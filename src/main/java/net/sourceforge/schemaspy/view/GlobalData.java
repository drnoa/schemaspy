package net.sourceforge.schemaspy.view;

import net.sourceforge.schemaspy.Config;
import net.sourceforge.schemaspy.Revision;
import net.sourceforge.schemaspy.model.Database;

public class GlobalData {
	
	private Config config = Config.getInstance();
	private Database database;
	private boolean displayNumRows;
	private boolean sourceForgeLogoEnabled = Config.getInstance().isLogoEnabled();
	private Revision schemaspyRevision = new Revision();

	public Database getDatabase() {
		return database;
	}

	public void setDatabase(Database database) {
		this.database = database;
	}

	public boolean isDisplayNumRows() {
		return displayNumRows;
	}

	public void setDisplayNumRows(boolean displayNumRows) {
		this.displayNumRows = displayNumRows;
	}

	public boolean isSourceForgeLogoEnabled() {
		return sourceForgeLogoEnabled;
	}

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	public Revision getSchemaspyRevision() {
		return schemaspyRevision;
	}

	public void setSchemaspyRevision(Revision schemaspyRevision) {
		this.schemaspyRevision = schemaspyRevision;
	}
}
