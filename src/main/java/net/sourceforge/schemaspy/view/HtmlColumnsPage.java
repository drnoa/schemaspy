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
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import net.sourceforge.schemaspy.Config;
import net.sourceforge.schemaspy.model.Database;
import net.sourceforge.schemaspy.model.Table;
import net.sourceforge.schemaspy.model.TableColumn;
import net.sourceforge.schemaspy.model.TableIndex;
import net.sourceforge.schemaspy.util.LineWriter;

/**
 * The page that lists all of the columns in the schema,
 * allowing the end user to sort by column's attributes.
 *
 * @author John Currier
 */
public class HtmlColumnsPage {
    private static HtmlColumnsPage instance = new HtmlColumnsPage();
    private TemplateService templateService;

    /**
     * Singleton: Don't allow instantiation
     */
    private HtmlColumnsPage() {
    	templateService = TemplateService.getInstance();
    }

    /**
     * Singleton accessor
     *
     * @return the singleton instance
     */
    public static HtmlColumnsPage getInstance() {
        return instance;
    }
    
    public ColumnInfo getDefaultColumnInfo() {
		return new ColumnInfo("Column", new ByColumnComparator());
	}

    public class ColumnInfo
    {
        private final String columnName;
        private final Comparator<TableColumn> comparator;

        private ColumnInfo(String columnName, Comparator<TableColumn> comparator)
        {
            this.columnName = columnName;
            this.comparator = comparator;
        }

        public String getColumnName() {
            return columnName;
        }

        public String getLocation() {
            return getLocation(columnName);
        }

        public String getLocation(String colName) {
            return "columns.by" + colName + ".html";
        }

        private Comparator<TableColumn> getComparator() {
            return comparator;
        }

        @Override
        public String toString() {
            return getLocation();
        }
    }

    public void write(Database database, Collection<Table> tables, ColumnInfo columnInfo, LineWriter html) throws IOException {
        Set<TableColumn> columns = new TreeSet<TableColumn>(columnInfo.getComparator());
        Set<TableColumn> primaryColumns = new HashSet<TableColumn>();
        Set<TableColumn> indexedColumns = new HashSet<TableColumn>();

        for (Table table : tables) {
            columns.addAll(table.getColumns());

            primaryColumns.addAll(table.getPrimaryColumns());
            for (TableIndex index : table.getIndexes()) {
                indexedColumns.addAll(index.getColumns());
            }
        }

        GlobalData globalData = new GlobalData();
		globalData.setDatabase(database);
		
		ColumnsPageData data = new ColumnsPageData();
		data.setGlobalData(globalData);
		data.setContainsComments(Config.getInstance().getColumnDetails().contains("comments"));
		data.setNumberOfColumns(columns.size());
		data.setColumns(columns);
		data.setIndexes(indexedColumns);
		data.setPrimaries(primaryColumns);
        
		html.write(templateService.renderTemplate("columns/localColumnsTemplate.ftl", data));
    }

    private class ByColumnComparator implements Comparator<TableColumn> {
        public int compare(TableColumn column1, TableColumn column2) {
            int rc = column1.getName().compareToIgnoreCase(column2.getName());
            if (rc == 0)
                rc = column1.getTable().compareTo(column2.getTable());
            return rc;
        }
    }
}