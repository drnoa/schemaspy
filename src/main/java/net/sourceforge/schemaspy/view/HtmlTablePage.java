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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.sourceforge.schemaspy.Config;
import net.sourceforge.schemaspy.model.Database;
import net.sourceforge.schemaspy.model.ForeignKeyConstraint;
import net.sourceforge.schemaspy.model.Table;
import net.sourceforge.schemaspy.model.TableColumn;
import net.sourceforge.schemaspy.model.TableIndex;
import net.sourceforge.schemaspy.util.Dot;
import net.sourceforge.schemaspy.util.LineWriter;

/**
 * The page that contains the details of a specific table or view
 *
 * @author John Currier
 */
public class HtmlTablePage extends HtmlDiagramFormatter {
    private static final HtmlTablePage instance = new HtmlTablePage();
    
	private TemplateService templateService;
	
	
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
        
        data.setHasImplied(hasImplied);
        data.setCheckShowComments(checkShowComments(table));
        
        Set<TableColumn> excludedColumns = new HashSet<TableColumn>();
        for (TableColumn column : stats.getExcludedColumns()) {
            if (column.isAllExcluded() || !column.getTable().equals(table)) {
            	excludedColumns.add(column);
            }
        }
        
        data.setExcludedColumns(excludedColumns);
        
        generateDiagramAndAddToPageData(table, diagramsDir, data);

        out.write(writeMainTable(table, data));
        
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

        Set<TableColumn> primaries = new HashSet<TableColumn>(table.getPrimaryColumns());
        Set<TableColumn> indexedColumns = new HashSet<TableColumn>();
        
        for (TableIndex index : table.getIndexes()) {
            indexedColumns.addAll(index.getColumns());
        }

        boolean showIds = table.getId() != null;
        data.setShowIds(showIds);
        data.setIndexes(indexedColumns);
        data.setPrimaries(primaries);
		data.setColumns(table.getColumns());

    	return(templateService.renderTemplate("tables/localTableTemplate.ftl", data));
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

    private void generateDiagramAndAddToPageData(Table table, File diagramsDir, TablePageData data) throws IOException {
        if (table.getMaxChildren() + table.getMaxParents() > 0) {
        	boolean diagramSuccessful = writeDiagram(table, diagramsDir, data);
        	data.setDiagramSuccessful(diagramSuccessful);
        }
    }
    
    private boolean writeDiagram(Table table, File diagramDir, TablePageData data) {
        try {
            Dot dot = getDot();
            if (dot == null)
                return false;

            File oneDegreeDotFile = new File(diagramDir, table.getName() + ".1degree.dot");
            File oneDegreeDiagramFile = new File(diagramDir, table.getName() + ".1degree." + dot.getFormat());
            File twoDegreesDotFile = new File(diagramDir, table.getName() + ".2degrees.dot");
            File twoDegreesDiagramFile = new File(diagramDir, table.getName() + ".2degrees." + dot.getFormat());
            File impliedDotFile = new File(diagramDir, table.getName() + ".implied2degrees.dot");
            File impliedDiagramFile = new File(diagramDir, table.getName() + ".implied2degrees." + dot.getFormat());
            
            data.oneDegreeDiagram = dot.generateDiagram(oneDegreeDotFile, oneDegreeDiagramFile);
            data.oneDegreeDiagramName = urlEncode(oneDegreeDiagramFile.getName());

            if (impliedDotFile.exists()) {
            	data.impliedDiagram = dot.generateDiagram(impliedDotFile, impliedDiagramFile);
                data.impliedDiagramName = urlEncode(impliedDiagramFile.getName());
            } else {
                impliedDotFile.delete();
                impliedDiagramFile.delete();
            }
            if (twoDegreesDotFile.exists()) {
            	data.twoDegreesDiagram = dot.generateDiagram(twoDegreesDotFile, twoDegreesDiagramFile);
                data.twoDegreesDiagramName = urlEncode(twoDegreesDiagramFile.getName());
            } else {
                twoDegreesDotFile.delete();
                twoDegreesDiagramFile.delete();
            }
        } catch (Dot.DotFailure dotFailure) {
            System.err.println(dotFailure);
            return false;
        }

        return true;
    }

    @Override
    protected String getPathToRoot() {
        return "../";
    }
}