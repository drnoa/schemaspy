package net.sourceforge.schemaspy.view;

public interface PageData {
	
	/**
	 * @return the currentPageName
	 */
	public String getCurrentPageName();
	/**
	 * @return The description to use in the HTML Header Tag
	 */
	public String getDescriptionHeader();
	
	/**
	 * @return the description to use in the HTML Content
	 */
	public String getDescriptionContent();

}
