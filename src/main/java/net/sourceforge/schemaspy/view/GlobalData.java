package net.sourceforge.schemaspy.view;

import net.sourceforge.schemaspy.Config;
import net.sourceforge.schemaspy.model.Database;

public class GlobalData {
	
	private Database database;
	private boolean displayNumRows;
	private boolean sourceForgeLogoEnabled = Config.getInstance().isLogoEnabled();

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

}
