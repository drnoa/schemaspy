/*
 * This file is a part of the SchemaSpy project (http://schemaspy.sourceforge.net).
 * Copyright (C) 2004, 2005, 2006, 2007, 2008, 2009, 2010, 2011 John Currier
 *
 * SchemaSpy is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * SchemaSpy is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package net.sourceforge.schemaspy.view;

import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.TreeSet;

import net.sourceforge.schemaspy.model.Database;
import net.sourceforge.schemaspy.model.Table;
import net.sourceforge.schemaspy.util.LineWriter;

/**
 * The main index that contains all tables and views that were evaluated
 * 
 * @author John Currier
 */
public class HtmlMainIndexPage extends HtmlFormatter {
	private static HtmlMainIndexPage instance = new HtmlMainIndexPage();
	
	private TemplateService templateService;

	/**
	 * Singleton: Don't allow instantiation
	 */
	private HtmlMainIndexPage() {
		templateService = TemplateService.getInstance();
	}

	/**
	 * Singleton accessor
	 * 
	 * @return the singleton instance
	 */
	public static HtmlMainIndexPage getInstance() {
		return instance;
	}

	

	public void write(Database database, Collection<Table> tables,
			Collection<Table> remotes, LineWriter html) throws IOException {
		Comparator<Table> sorter = new Comparator<Table>() {
			public int compare(Table table1, Table table2) {
				return table1.compareTo(table2);
			}
		};
		// sort tables and remotes by name
		Collection<Table> tmp = new TreeSet<Table>(sorter);
		tmp.addAll(tables);
		tables = tmp;
		tmp = new TreeSet<Table>(sorter);
		tmp.addAll(remotes);
		remotes = tmp;
		tmp = null;

		boolean showIds = false;
		int numViews = 0;

		int numTableCols = 0;
		int numViewCols = 0;
		long numTableRows = 0;
		
		for (Table table : tables) {
			if (table.isView()){
				++numViews;
			}
			showIds |= table.getId() != null;
			
			if (!table.isView()){
				numTableCols += table.getColumns().size();
			}else{
				numViewCols += table.getColumns().size();
			}
			numTableRows += table.getNumRows() > 0 ? table.getNumRows() : 0;
		}
		
		GlobalData globalData = new GlobalData();
		globalData.setDatabase(database);
		globalData.setDisplayNumRows(displayNumRows);
		
		MainIndexPageData data = new MainIndexPageData();
		data.setGlobalData(globalData);
		data.setTables(tables);
		data.setRemoteTables(remotes);
		data.setShowIds(showIds);
		data.setNumberOfTables(tables.size() - numViews);
		data.setNumberOfViews(numViews);
		data.setNumberTableCols(numTableCols);
		data.setNumberTableRows(numTableRows);
		data.setNumberViewCols(numViewCols);
		
		html.write(writeTables(data));
	}

	protected String writeTables(MainIndexPageData data) throws IOException {
		return templateService.renderTemplate("mainindex/localTablesTemplate.ftl", data);
	}

	@Override
	protected boolean isMainIndex() {
		return true;
	}
}
