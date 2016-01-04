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
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.List;

import net.sourceforge.schemaspy.util.LineWriter;

/**
 * The page that contains links to the various schemas that were analyzed
 *
 * @author John Currier
 */
public class HtmlMultipleSchemasIndexPage {
    private static HtmlMultipleSchemasIndexPage instance = new HtmlMultipleSchemasIndexPage();
    private TemplateService templateService;

    /**
     * Singleton: Don't allow instantiation
     */
    private HtmlMultipleSchemasIndexPage() {
    	templateService = TemplateService.getInstance();
    }

    /**
     * Singleton accessor
     *
     * @return the singleton instance
     */
    public static HtmlMultipleSchemasIndexPage getInstance() {
        return instance;
    }

    public void write(String dbName, List<String> populatedSchemas, DatabaseMetaData meta, LineWriter html) throws IOException {
    	GlobalData globalData = new GlobalData();
		
		MultipleSchemasPageData data = new MultipleSchemasPageData();
		data.setGlobalData(globalData);
		data.setDatabaseName(dbName);
		if(meta != null){
			data.setDatabaseProduct(getDatabaseProduct(meta));
		}
		data.setAdditionalAssetPath(populatedSchemas.get(0)+ "/");
		data.setPopulatedSchemas(populatedSchemas);
    	html.write(templateService.renderTemplate("multipleSchemas/multipleSchemasIndex.ftl", data));
    }

    /**
     * Copy / paste from Database, but we can't use Database here...
     *
     * @param meta DatabaseMetaData
     * @return String
     */
    private String getDatabaseProduct(DatabaseMetaData meta) {
        try {
            return meta.getDatabaseProductName() + " - " + meta.getDatabaseProductVersion();
        } catch (SQLException exc) {
            return "";
        }
    }
}
