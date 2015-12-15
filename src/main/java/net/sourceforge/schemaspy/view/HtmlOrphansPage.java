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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.sourceforge.schemaspy.Config;
import net.sourceforge.schemaspy.model.Database;
import net.sourceforge.schemaspy.model.Table;
import net.sourceforge.schemaspy.util.Dot;
import net.sourceforge.schemaspy.util.LineWriter;

/**
 * The page that contains the all tables that aren't related to others (orphans)
 *
 * @author John Currier
 */
public class HtmlOrphansPage extends HtmlDiagramFormatter {
    private static HtmlOrphansPage instance = new HtmlOrphansPage();
    
    private TemplateService templateService;


    /**
     * Singleton: Don't allow instantiation
     */
    private HtmlOrphansPage() {
    	templateService = TemplateService.getInstance();
    }

    /**
     * Singleton accessor
     *
     * @return the singleton instance
     */
    public static HtmlOrphansPage getInstance() {
        return instance;
    }
    
    public boolean write(Database db, List<Table> orphanTables, File diagramDir, LineWriter html) throws IOException {
        Dot dot = getDot();
        if (dot == null)
            return false;

        Set<Table> orphansWithImpliedRelationships = new HashSet<Table>();

        for (Table table : orphanTables) {
            if (!table.isOrphan(true)){
                orphansWithImpliedRelationships.add(table);
            }
        }
        
        writeHeader(db, null, "Utility Tables", html);

        StringBuilder maps = new StringBuilder(64 * 1024);

        for (Table table : orphanTables) {
            String dotBaseFilespec = table.getName();

            File dotFile = new File(diagramDir, dotBaseFilespec + ".1degree.dot");
            File imgFile = new File(diagramDir, dotBaseFilespec + ".1degree." + dot.getFormat());

            LineWriter dotOut = new LineWriter(dotFile, Config.DOT_CHARSET);
            DotFormatter.getInstance().writeOrphan(table, dotOut);
            dotOut.close();
            try {
                maps.append(dot.generateDiagram(dotFile, imgFile));
            } catch (Dot.DotFailure dotFailure) {
                System.err.println(dotFailure);
                return false;
            }
            table.setOrphanDiagramFileName(imgFile.getName());
        } 
        
        GlobalData globalData = new GlobalData();
        globalData.setDatabase(db);
        OrphansPageData data = new OrphansPageData();
        data.setGlobalData(globalData);
        data.setOrphanTables(orphanTables);
        data.setOrphansWithImpliedRelationships(orphansWithImpliedRelationships);
        data.setMaps(maps.toString());

        html.write(writeOrphans(data));
        
        return true;
    }
    
    protected String writeOrphans(OrphansPageData data) throws IOException {
        return templateService.renderTemplate("orphans/localOrphansTemplate.ftl", data);
    }

    @Override
    protected boolean isOrphansPage() {
        return true;
    }
}
