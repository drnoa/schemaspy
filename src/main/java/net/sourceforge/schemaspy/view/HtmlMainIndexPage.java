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

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sourceforge.schemaspy.Config;
import net.sourceforge.schemaspy.model.Database;
import net.sourceforge.schemaspy.model.Table;
import net.sourceforge.schemaspy.util.LineWriter;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

/**
 * The main index that contains all tables and views that were evaluated
 * 
 * @author John Currier
 */
public class HtmlMainIndexPage extends HtmlFormatter {
	private static HtmlMainIndexPage instance = new HtmlMainIndexPage();
	private Configuration cfg;
	private final static Logger logger = Logger
			.getLogger(HtmlMainIndexPage.class.getName());

	/**
	 * Singleton: Don't allow instantiation
	 */
	private HtmlMainIndexPage() {
		cfg = getFreemarkerConfig();
	}

	/**
	 * Singleton accessor
	 * 
	 * @return the singleton instance
	 */
	public static HtmlMainIndexPage getInstance() {
		return instance;
	}

	private static Configuration getFreemarkerConfig() {
		Configuration cfg = new Configuration();
		ClassLoader classLoader = HtmlMainIndexPage.class.getClassLoader();
		File file = new File(
				"/home/tphilipona/projects/schemaspy/schemaspy/src/main/resources/templates");

		try {
			cfg.setDirectoryForTemplateLoading(file);
		} catch (IOException e) {
			logger.log(Level.WARNING, "unable to set Directory for Template Loading" , e);
		}
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
		return cfg;
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
		long numRows = 0;
		
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
			numRows += table.getNumRows() > 0 ? table.getNumRows() : 0;
		}

		writeLocalsHeader(database, tables.size() - numViews, numViews,
				showIds, hasComments, html);

		writeTables(tables, showIds, html, tables.size() - numViews, numViews);

		if (!remotes.isEmpty()) {
			writeRemoteTables(remotes, showIds, html, 0, 0);
		}

		writeFooter(html);
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
		html.writeln("<table width='100%'>");
		html.writeln(" <tr><td class='container'>");
		writeGeneratedOn(db.getConnectTime(), html);
		html.writeln(" </td></tr>");
		html.writeln(" <tr>");
		html.write("  <td class='container'>Database Type: ");
		html.write(db.getDatabaseProduct());
		html.writeln("  </td>");
		html.writeln("  <td class='container' align='right' valign='top' rowspan='3'>");
		if (sourceForgeLogoEnabled())
			html.writeln("    <a href='http://sourceforge.net' target='_blank'><img src='http://sourceforge.net/sflogo.php?group_id=137197&amp;type=1' alt='SourceForge.net' border='0' height='31' width='88'></a><br>");
		html.writeln("    <br>");
		html.writeln("  </td>");
		html.writeln(" </tr>");
		html.writeln(" <tr>");
		html.write("  <td class='container'>");
		String xmlName = db.getName();
		if (db.getSchema() != null)
			xmlName += '.' + db.getSchema();
		else if (db.getCatalog() != null)
			xmlName += '.' + db.getCatalog();
		html.write("<br><a href='" + xmlName
				+ ".xml' title='XML Representation'>XML Representation</a>");
		html.write("<br><a href='insertionOrder.txt' title='Useful for loading data into a database'>Insertion Order</a>&nbsp;");
		html.write("<a href='deletionOrder.txt' title='Useful for purging data from a database'>Deletion Order</a>");
		html.writeln("</td>");
		html.writeln(" </tr>");
		html.writeln("</table>");

		html.writeln("<div class='indent'>");
		html.write("<p>");
		html.write("<b>");
		if (numberOfViews == 0) {
			html.writeln("<label for='showTables' style='display:none;'><input type='checkbox' id='showTables' checked></label>");
		} else if (numberOfTables == 0) {
			html.writeln("<label for='showViews' style='display:none;'><input type='checkbox' id='showViews' checked></label>");
		} else {
			html.write("<label for='showTables'><input type='checkbox' id='showTables' checked>Tables</label>");
			html.write(" <label for='showViews'><input type='checkbox' id='showViews' checked>Views</label>");
		}

		html.writeln(" <label for='showComments'><input type=checkbox "
				+ (hasComments ? "checked " : "")
				+ "id='showComments'>Comments</label>");
		html.writeln("</b>");
	}

	protected void writeTables(Collection<Table> tables, boolean showIds,
			LineWriter html, int numberOfTables, int numberOfViews) throws IOException {
		Template temp = cfg.getTemplate("mainindex/template.ftl");

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("tables", tables);
		data.put("showIds", showIds);
		data.put("oneOfMultipleSchemas", Config.getInstance()
				.isOneOfMultipleSchemas());
		String link = getAdditionalLinkBase();
		data.put("additionalLinkBase", link);
		data.put("displayNumRows", displayNumRows);
		data.put("numberOfTables", numberOfTables);
		data.put("numberOfViews", numberOfViews);
		

		Writer out = new StringWriter();
		try {
			temp.process(data, out);
		} catch (TemplateException e) {
			logger.log(Level.WARNING, "TemplateException: ", e);
		}
		html.write(out.toString());

	}
	
	protected void writeRemoteTables(Collection<Table> tables, boolean showIds,
			LineWriter html, int numberOfTables, int numberOfViews) throws IOException {
		Template temp = cfg.getTemplate("mainindex/remoteTablesTemplate.ftl");

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("tables", tables);
		data.put("showIds", showIds);
		data.put("oneOfMultipleSchemas", Config.getInstance()
				.isOneOfMultipleSchemas());
		String link = getAdditionalLinkBase();
		data.put("additionalLinkBase", link);
		data.put("displayNumRows", displayNumRows);
		

		Writer out = new StringWriter();
		try {
			temp.process(data, out);
		} catch (TemplateException e) {
			logger.log(Level.WARNING, "TemplateException: ", e);
		}
		html.write(out.toString());

	}

	private String getAdditionalLinkBase() {
		return "https://twiki.puzzle.ch/bin/view/NDBJS";
	}


	@Override
	protected boolean isMainIndex() {
		return true;
	}
}
