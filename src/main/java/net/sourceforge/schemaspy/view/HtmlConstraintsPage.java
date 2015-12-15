/*
 * This file is a part of the SchemaSpy project (http://schemaspy.sourceforge.net).
 * Copyright (C) 2004, 2005, 2006, 2007, 2008, 2009, 2010 John Currier
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
import java.util.List;

import net.sourceforge.schemaspy.DbAnalyzer;
import net.sourceforge.schemaspy.model.Database;
import net.sourceforge.schemaspy.model.ForeignKeyConstraint;
import net.sourceforge.schemaspy.model.Table;
import net.sourceforge.schemaspy.util.LineWriter;

/**
 * The page that lists all of the constraints in the schema
 *
 * @author John Currier
 */
public class HtmlConstraintsPage extends HtmlFormatter {
    private static HtmlConstraintsPage instance = new HtmlConstraintsPage();

    private TemplateService templateService;

    /**
     * Singleton: Don't allow instantiation
     */
    private HtmlConstraintsPage() {
        templateService = TemplateService.getInstance();
    }

    /**
     * Singleton accessor
     *
     * @return the singleton instance
     */
    public static HtmlConstraintsPage getInstance() {
        return instance;
    }

    public void write(Database db, List<ForeignKeyConstraint> constraints, Collection<Table> tables, LineWriter html) throws IOException {
        writeHeader(db, null, "Constraints", html);

        GlobalData globalData = new GlobalData();
        globalData.setDatabase(db);
        ConstraintPageData data = new ConstraintPageData();
        data.setGlobalData(globalData);
        data.setConstraints(constraints);
        data.setTables(DbAnalyzer.sortTablesByName(new ArrayList<Table>(tables)));

        html.write(writeConstraints(data));
    }

    protected String writeConstraints(ConstraintPageData data) throws IOException {
        return templateService.renderTemplate("constraints/localConstraintsTemplate.ftl", data);
    }

    @Override
    protected boolean isConstraintsPage() {
        return true;
    }
}