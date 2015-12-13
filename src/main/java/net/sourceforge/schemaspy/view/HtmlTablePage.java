/*
 * This file is a part of the SchemaSpy project (http://schemaspy.sourceforge.net).
 * Copyright (C) 2004, 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2014 John Currier
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
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import net.sourceforge.schemaspy.Config;
import net.sourceforge.schemaspy.model.AdditionalInfo;
import net.sourceforge.schemaspy.model.Database;
import net.sourceforge.schemaspy.model.ForeignKeyConstraint;
import net.sourceforge.schemaspy.model.Table;
import net.sourceforge.schemaspy.model.TableColumn;
import net.sourceforge.schemaspy.model.TableIndex;
import net.sourceforge.schemaspy.model.View;
import net.sourceforge.schemaspy.util.CaseInsensitiveMap;
import net.sourceforge.schemaspy.util.Dot;
import net.sourceforge.schemaspy.util.HtmlEncoder;
import net.sourceforge.schemaspy.util.LineWriter;

/**
 * The page that contains the details of a specific table or view
 *
 * @author John Currier
 */
public class HtmlTablePage extends HtmlFormatter {
    private static final HtmlTablePage instance = new HtmlTablePage();
    
	private TemplateService templateService;
	private int columnCounter;
	
	
    private final Map<String, String> defaultValueAliases = new HashMap<String, String>();
    {
        defaultValueAliases.put("CURRENT TIMESTAMP", "now"); // DB2
        defaultValueAliases.put("CURRENT TIME", "now");      // DB2
        defaultValueAliases.put("CURRENT DATE", "now");      // DB2
        defaultValueAliases.put("SYSDATE", "now");           // Oracle
        defaultValueAliases.put("CURRENT_DATE", "now");      // Oracle
    }

    /**
     * Singleton: Don't allow instantiation
     */
    private HtmlTablePage() {
    	templateService = TemplateService.getInstance();
    }

    /**
     * Singleton accessor
     *
     * @return the singleton instance
     */
    public static HtmlTablePage getInstance() {
        return instance;
    }

    public WriteStats write(Database db, Table table, File outputDir, WriteStats stats, LineWriter out) throws IOException {
        File diagramsDir = new File(outputDir, "diagrams");
        boolean hasImplied = generateDots(table, diagramsDir, stats);

        GlobalData globalData = new GlobalData();
		globalData.setDatabase(db);
		
		TablePageData data = new TablePageData();
        data.setTable(table);
        data.setGlobalData(globalData);
        
        writeHeader(db, table, null, out);
        data.setHasImplied(hasImplied);

        data.setCheckShowComments(checkShowComments(table));
        
        
        out.write(writeMainTable(table, data));
        
        /*
        
        
        writeNumRows(db, table, out);
        out.writeln("</td></tr></table>");
        writeCheckConstraints(table, out);
        writeIndexes(table, out);
        writeView(table, db, out);
        writeAdditionalInfos(table, out);
        writeDiagram(table, stats, diagramsDir, out);
        writeFooter(out);
*/
        
        //out.write(writeTables(data));
        
        
        return stats;
    }

	/*
	 *  initially show comments if any of the columns contain comments
	 */
	private boolean checkShowComments(Table table) throws IOException {
        
        boolean showCommentsInitially = false;
        for (TableColumn column : table.getColumns()) {
            if (column.getComments() != null) {
                showCommentsInitially = true;
                break;
            }
        }
        return showCommentsInitially;
    }

    public String writeMainTable(Table table, TablePageData data) throws IOException {
    	
    	
    	
        //HtmlColumnsPage.getInstance().writeMainTableHeader(table.getId() != null, null, out);

        Set<TableColumn> primaries = new HashSet<TableColumn>(table.getPrimaryColumns());
        Set<TableColumn> indexedColumns = new HashSet<TableColumn>();
        
        for (TableIndex index : table.getIndexes()) {
            indexedColumns.addAll(index.getColumns());
        }

        boolean showIds = table.getId() != null;
        data.setShowIds(showIds);
        data.setIndexes(indexedColumns);
        data.setPrimaries(primaries);
        List<TableColumn> columns = table.getColumns();
		data.setColumns(columns);

    	return(templateService.renderTemplate("tables/localTableTemplate.ftl", data));
    }

    public void writeColumn(TableColumn column, String tableName, Set<TableColumn> primaries, Set<TableColumn> indexedColumns, boolean slim, boolean showIds, LineWriter out) throws IOException {
        
		boolean even = columnCounter++ % 2 == 0;
        if (even)
            out.writeln("<tr class='even'>");
        else
            out.writeln("<tr class='odd'>");

        if (showIds) {
            out.write(" <td class='detail' align='right'>");
            out.write(String.valueOf(column.getId()));
            out.writeln("</td>");
        }
        if (tableName != null) {
            out.write(" <td class='detail'><a href='tables/");
            out.write(urlEncode(tableName));
            out.write(".html'>");
            out.write(tableName);
            out.writeln("</a></td>");
        }
        if (primaries.contains(column))
            out.write(" <td class='primaryKey' title='Primary Key'>");
        else if (indexedColumns.contains(column))
            out.write(" <td class='indexedColumn' title='Indexed'>");
        else
            out.write(" <td class='detail'>");
        out.write(column.getName());
        out.writeln("</td>");
        out.write(" <td class='detail'>");
        out.write(column.getTypeName().toLowerCase());
        out.writeln("</td>");
        out.write(" <td class='detail' align='right'>");
        out.write(column.getDetailedSize());
        out.writeln("</td>");
        out.write(" <td class='detail' align='center'");
        if (column.isNullable())
            out.write(" title='nullable'>&nbsp;&radic;&nbsp;");
        else
            out.write(">");
        out.writeln("</td>");
        out.write(" <td class='detail' align='center'");
        if (column.isAutoUpdated()) {
            out.write(" title='Automatically updated by the database'>&nbsp;&radic;&nbsp;");
        } else {
            out.write(">");
        }
        out.writeln("</td>");

        Object defaultValue = column.getDefaultValue();
        if (defaultValue != null || column.isNullable()) {
            Object alias = defaultValueAliases.get(String.valueOf(defaultValue).trim());
            if (alias != null) {
                out.write(" <td class='detail' align='right' title='");
                out.write(String.valueOf(defaultValue));
                out.write("'><i>");
                out.write(alias.toString());
                out.writeln("</i></td>");
            } else {
                out.write(" <td class='detail' align='right'>");
                out.write(String.valueOf(defaultValue));
                out.writeln("</td>");
            }
        } else {
            out.writeln(" <td class='detail'></td>");
        }
        if (!slim) {
            out.write(" <td class='detail'>");
            String path = tableName == null ? "" : "tables/";
            writeRelatives(column, false, path, even, out);
            out.writeln("</td>");
            out.write(" <td class='detail'>");
            writeRelatives(column, true, path, even, out);
            out.writeln(" </td>");
        }
        out.write(" <td class='comment detail'>");
        String comments = column.getComments();
        if (comments != null) {
            if (encodeComments)
                for (int i = 0; i < comments.length(); ++i)
                    out.write(HtmlEncoder.encodeToken(comments.charAt(i)));
            else
                out.write(comments);
        }
        out.writeln("</td>");
        out.writeln("</tr>");
    }
    
    /**
     * Write our relatives
     * @param tableName String
     * @param baseRelative TableColumn
     * @param dumpParents boolean
     * @param out LineWriter
     * @throws IOException
     */
    private void writeRelatives(TableColumn baseRelative, boolean dumpParents, String path, boolean even, LineWriter out) throws IOException {
        Set<TableColumn> columns = dumpParents ? baseRelative.getParents() : baseRelative.getChildren();
        final int numColumns = columns.size();
        final String evenOdd = (even ? "even" : "odd");

        if (numColumns > 0) {
            out.newLine();
            out.writeln("  <table border='0' width='100%' cellspacing='0' cellpadding='0'>");
        }

        for (TableColumn column : columns) {
            Table columnTable = column.getTable();
            String columnTableName = columnTable.getName();
            ForeignKeyConstraint constraint = dumpParents ? column.getChildConstraint(baseRelative) : column.getParentConstraint(baseRelative);
            if (constraint.isImplied())
                out.writeln("   <tr class='impliedRelationship relative " + evenOdd + "' valign='top'>");
            else
                out.writeln("   <tr class='relative " + evenOdd + "' valign='top'>");
            out.write("    <td class='relatedTable detail' title=\"");
            out.write(constraint.toString());
            out.write("\">");
            if (columnTable.isRemote() && !Config.getInstance().isOneOfMultipleSchemas()) {
                out.write(columnTable.getContainer());
                out.write('.');
                out.write(columnTableName);
            } else {
                if (columnTable.isRemote()) {
                    out.write("<a href='");
                    out.write(path);
                    out.write("../../" + urlEncode(columnTable.getContainer()) + "/index.html'>");
                    out.write(columnTable.getContainer());
                    out.write("</a>.");
                }
                out.write("<a href='");
                out.write(path);
                if (columnTable.isRemote()) {
                    out.write("../../" + urlEncode(columnTable.getContainer()) + "/tables/");
                }
                out.write(urlEncode(columnTableName));
                out.write(".html'>");
                out.write(columnTableName);
                out.write("</a>");
            }
            out.write("<span class='relatedKey'>.");
            out.write(column.getName());
            out.writeln("</span>");
            out.writeln("    </td>");

            out.write("    <td class='constraint detail'>");
            out.write(constraint.getName());
            String ruleText = constraint.getDeleteRuleDescription();
            if (ruleText.length() > 0)
            {
                String ruleAlias = constraint.getDeleteRuleAlias();
                out.write("<span title='" + ruleText + "'>&nbsp;" + ruleAlias + "</span>");
            }
            out.writeln("</td>");

            out.writeln("   </tr>");
        }
        if (numColumns > 0) {
            out.writeln("  </table>");
        }
    }

    private void writeCheckConstraints(Table table, LineWriter out) throws IOException {
        Map<String, String> constraints = table.getCheckConstraints();
        if (constraints != null && !constraints.isEmpty()) {
            out.writeln("<div class='indent'>");
            out.writeln("<b>Requirements (check constraints):</b>");
            out.writeln("<table class='dataTable' border='1' rules='groups'><colgroup><colgroup>");
            out.writeln("<thead>");
            out.writeln(" <tr>");
            out.writeln("  <th>Constraint</th>");
            out.writeln("  <th class='constraint' style='text-align:left;'>Constraint Name</th>");
            out.writeln(" </tr>");
            out.writeln("</thead>");
            out.writeln("<tbody>");
            for (String name : constraints.keySet()) {
                out.writeln(" <tr>");
                out.write("  <td class='detail'>");
                out.write(HtmlEncoder.encodeString(constraints.get(name).toString()));
                out.writeln("</td>");
                out.write("  <td class='constraint' style='text-align:left;'>");
                out.write(name);
                out.writeln("</td>");
                out.writeln(" </tr>");
            }
            out.writeln("</tbody>");
            out.writeln("</table></div><p>");
        }
    }
    

    private void writeIndexes(Table table, LineWriter out) throws IOException {
        boolean showId = table.getId() != null;
        Set<TableIndex> indexes = table.getIndexes();
        if (indexes != null && !indexes.isEmpty()) {
            out.writeln("<div class='indent'>");
            out.writeln("<b>Indexes:</b>");
            out.writeln("<table class='dataTable' border='1' rules='groups'><colgroup><colgroup><colgroup><colgroup>" + (showId ? "<colgroup>" : ""));
            out.writeln("<thead>");
            out.writeln(" <tr>");
            if (showId)
                out.writeln("  <th>ID</th>");
            out.writeln("  <th>Column(s)</th>");
            out.writeln("  <th>Type</th>");
            out.writeln("  <th>Sort</th>");
            out.writeln("  <th class='constraint' style='text-align:left;'>Constraint Name</th>");
            out.writeln(" </tr>");
            out.writeln("</thead>");
            out.writeln("<tbody>");

            indexes = new TreeSet<TableIndex>(indexes); // sort primary keys first

            for (TableIndex index : indexes) {
                out.writeln(" <tr>");

                if (showId) {
                    out.write("  <td class='detail' align='right'>");
                    out.write(String.valueOf(index.getId()));
                    out.writeln("</td>");
                }

                if (index.isPrimaryKey())
                    out.write("  <td class='primaryKey'>");
                else
                    out.write("  <td class='indexedColumn'>");
                String columns = index.getColumnsAsString();
                if (columns.startsWith("+"))
                    columns = columns.substring(1);
                out.write(columns);
                out.writeln("</td>");

                out.write("  <td class='detail'>");
                out.write(index.getType());
                out.writeln("</td>");

                out.write("  <td class='detail' style='text-align:left;'>");
                Iterator<TableColumn> columnsIter = index.getColumns().iterator();
                while (columnsIter.hasNext()) {
                    TableColumn column = columnsIter.next();
                    if (index.isAscending(column))
                        out.write("<span title='Ascending'>Asc</span>");
                    else
                        out.write("<span title='Descending'>Desc</span>");
                    if (columnsIter.hasNext())
                        out.write("/");
                }
                out.writeln("</td>");

                out.write("  <td class='constraint' style='text-align:left;'>");
                out.write(index.getName());
                out.writeln("</td>");
                out.writeln(" </tr>");
            }
            out.writeln("</tbody>");
            out.writeln("</table>");
            out.writeln("</div>");
        }
    }

    private void writeView(Table table, Database db, LineWriter out) throws IOException {
        String sql;
        if (table.isView() && (sql = table.getViewSql()) != null) {
            Map<String, Table> tables = new CaseInsensitiveMap<Table>();

            for (Table t : db.getTables())
                tables.put(t.getName(), t);
            for (View v : db.getViews())
                tables.put(v.getName(), v);

            Set<Table> references = new TreeSet<Table>();
            String formatted = Config.getInstance().getSqlFormatter().format(sql, db, references);

            out.writeln("<div class='indent spacer'>");
            out.writeln("  <b>View Definition:</b><br/>");
            out.writeln(formatted);
            out.writeln("</div>");
            out.writeln("<div class='spacer'>&nbsp;</div>");

            if (!references.isEmpty()) {
                out.writeln("<div class='indent'>");
                out.writeln("  Possibly Referenced Tables/Views:");
                out.writeln("  <div class='viewReferences'>");
                out.write("  ");
                for (Table t : references) {
                    out.write("<a href='");
                    out.write(urlEncode(t.getName()));
                    out.write(".html'>");
                    out.write(t.getName());
                    out.write("</a>&nbsp;");
                }

                out.writeln("  </div>");
                out.writeln("</div><p/>");
            }

        }
    }

    /**
     * Generate the .dot file(s) to represent the specified table's relationships.
     *
     * Generates a <TABLENAME>.dot if the table has real relatives.
     *
     * Also generates a <TABLENAME>.implied2degrees.dot if the table has implied relatives within
     * two degrees of separation.
     *
     * @param table Table
     * @param diagramsDir File
     * @throws IOException
     * @return boolean <code>true</code> if the table has implied relatives within two
     *                 degrees of separation.
     */
    private boolean generateDots(Table table, File diagramDir, WriteStats stats) throws IOException {
        Dot dot = Dot.getInstance();
        String extension = dot == null ? "png" : dot.getFormat();

        File oneDegreeDotFile = new File(diagramDir, table.getName() + ".1degree.dot");
        File oneDegreeDiagramFile = new File(diagramDir, table.getName() + ".1degree." + extension);
        File twoDegreesDotFile = new File(diagramDir, table.getName() + ".2degrees.dot");
        File twoDegreesDiagramFile = new File(diagramDir, table.getName() + ".2degrees." + extension);
        File impliedDotFile = new File(diagramDir, table.getName() + ".implied2degrees.dot");
        File impliedDiagramFile = new File(diagramDir, table.getName() + ".implied2degrees." + extension);

        // delete before we start because we'll use the existence of these files to determine
        // if they should be turned into pngs & presented
        oneDegreeDotFile.delete();
        oneDegreeDiagramFile.delete();
        twoDegreesDotFile.delete();
        twoDegreesDiagramFile.delete();
        impliedDotFile.delete();
        impliedDiagramFile.delete();

        if (table.getMaxChildren() + table.getMaxParents() > 0) {
            Set<ForeignKeyConstraint> impliedConstraints;

            DotFormatter formatter = DotFormatter.getInstance();
            LineWriter dotOut = new LineWriter(oneDegreeDotFile, Config.DOT_CHARSET);
            WriteStats oneStats = new WriteStats(stats);
            formatter.writeRealRelationships(table, false, oneStats, dotOut);
            dotOut.close();

            dotOut = new LineWriter(twoDegreesDotFile, Config.DOT_CHARSET);
            WriteStats twoStats = new WriteStats(stats);
            impliedConstraints = formatter.writeRealRelationships(table, true, twoStats, dotOut);
            dotOut.close();

            if (oneStats.getNumTablesWritten() + oneStats.getNumViewsWritten() == twoStats.getNumTablesWritten() + twoStats.getNumViewsWritten()) {
                twoDegreesDotFile.delete(); // no different than before, so don't show it
            }

            if (!impliedConstraints.isEmpty()) {
                dotOut = new LineWriter(impliedDotFile, Config.DOT_CHARSET);
                formatter.writeAllRelationships(table, true, stats, dotOut);
                dotOut.close();
                return true;
            }
        }

        return false;
    }

    private void writeDiagram(Table table, WriteStats stats, File diagramsDir, LineWriter html) throws IOException {
        if (table.getMaxChildren() + table.getMaxParents() > 0) {
            html.writeln("<table width='100%' border='0'><tr><td class='container'>");
            if (HtmlTableDiagrammer.getInstance().write(table, diagramsDir, html)) {
                html.writeln("</td></tr></table>");
                writeExcludedColumns(stats.getExcludedColumns(), table, html);
            } else {
                html.writeln("</td></tr></table><p>");
                writeInvalidGraphvizInstallation(html);
            }
        }
    }

    @Override
    protected String getPathToRoot() {
        return "../";
    }
}