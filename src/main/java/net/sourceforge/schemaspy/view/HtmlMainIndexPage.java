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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
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
		boolean hasComments = false;

		int numTableCols = 0;
		int numViewCols = 0;
		long numTableRows = 0;
		
		for (Table table : tables) {
			if (table.isView()){
				++numViews;
			}
			showIds |= table.getId() != null;
			if (table.getComments() != null){
				hasComments = true;
			}
			
			if (!table.isView()){
				numTableCols += table.getColumns().size();
			}else{
				numViewCols += table.getColumns().size();
			}
			numTableRows += table.getNumRows() > 0 ? table.getNumRows() : 0;
		}

		writeLocalsHeader(database, tables.size() - numViews, numViews,
				showIds, hasComments, html);
		
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


	private void writeLocalsHeader(Database db, int numberOfTables,
			int numberOfViews, boolean showIds, boolean hasComments,
			LineWriter html) throws IOException {
		List<String> javascript = new ArrayList<String>();

		// we can't use the hard-coded even odd technique that we use
		// everywhere else because we're dynamically changing the visibility
		// of tables/views within the list
		javascript.add("$(function(){");
		javascript.add("  associate($('#showTables'), $('.tbl'));");
		javascript.add("  associate($('#showViews'),  $('.view'));");
		javascript.add("  jQuery.fn.alternateRowColors = function() {");
		javascript.add("    $('tbody tr:visible').each(function(i) {");
		javascript.add("      if (i % 2 == 0) {");
		javascript.add("        $(this).removeClass('even').addClass('odd');");
		javascript.add("      } else {");
		javascript.add("        $(this).removeClass('odd').addClass('even');");
		javascript.add("      }");
		javascript.add("    });");
		javascript.add("    return this;");
		javascript.add("  };");
		javascript.add("  $('#showTables, #showViews').click(function() {");
		javascript.add("    $('table.dataTable').alternateRowColors();");
		javascript.add("  });");
		javascript.add("  $('table.dataTable').alternateRowColors();");
		javascript.add("})");

		writeHeader(db, null, null, javascript, html);
	}

	protected String writeTables(MainIndexPageData data) throws IOException {
		return templateService.renderTemplate("mainindex/localTablesTemplate.ftl", data);
	}

	@Override
	protected boolean isMainIndex() {
		return true;
	}
}
