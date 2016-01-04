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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import net.sourceforge.schemaspy.DbAnalyzer;
import net.sourceforge.schemaspy.model.Database;
import net.sourceforge.schemaspy.model.ForeignKeyConstraint;
import net.sourceforge.schemaspy.model.Table;
import net.sourceforge.schemaspy.util.LineWriter;

/**
 * This page lists all of the 'things that might not be quite right'
 * about the schema.
 *
 * @author John Currier
 */
public class HtmlAnomaliesPage {
    private static HtmlAnomaliesPage instance = new HtmlAnomaliesPage();
    
    private TemplateService templateService;

    /**
     * Singleton: Don't allow instantiation
     */
    private HtmlAnomaliesPage() {
    	templateService = TemplateService.getInstance();
    }

    /**
     * Singleton accessor
     *
     * @return the singleton instance
     */
    public static HtmlAnomaliesPage getInstance() {
        return instance;
    }

    public void write(Database database, Collection<Table> tables, List<? extends ForeignKeyConstraint> impliedConstraints, LineWriter html) throws IOException {
    	GlobalData globalData = new GlobalData();
		globalData.setDatabase(database);
		
		AnomaliesPageData data = new AnomaliesPageData();
		data.setGlobalData(globalData);
		
		// only add Contraints on  Tables
		List<ForeignKeyConstraint>  impliedConstraintsOnTables = new ArrayList<>();
		for (ForeignKeyConstraint impliedConstraint : impliedConstraints) {
            if (!impliedConstraint.getChildTable().isView()) {
            	impliedConstraintsOnTables.add(impliedConstraint);
            }
        }
		data.setImpliedConstraints(impliedConstraintsOnTables);
		data.setUnindexedTables(DbAnalyzer.getTablesWithoutIndexes(new HashSet<Table>(tables)));
		data.setTablesWithOneColumn(DbAnalyzer.getTablesWithOneColumn(tables));
		data.setTablesWithIncrementingColumnNames(DbAnalyzer.getTablesWithIncrementingColumnNames(tables));
		data.setDefaultNullStringColumns(DbAnalyzer.getDefaultNullStringColumns(new HashSet<Table>(tables)));
    	html.write(templateService.renderTemplate("anomalies/anomaliesTemplate.ftl", data));
    }
}
