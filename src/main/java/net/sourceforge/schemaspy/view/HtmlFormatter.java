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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.logging.Logger;

import net.sourceforge.schemaspy.Config;
import net.sourceforge.schemaspy.model.Database;
import net.sourceforge.schemaspy.model.Table;
import net.sourceforge.schemaspy.util.LineWriter;

public class HtmlFormatter {
    protected final boolean encodeComments = Config.getInstance().isEncodeCommentsEnabled();
    private   final boolean isMetered = Config.getInstance().isMeterEnabled();
    protected final boolean displayNumRows = Config.getInstance().isNumRowsEnabled();

    protected HtmlFormatter() {
    }


    protected void writeGeneratedOn(String connectTime, LineWriter html) throws IOException {
        html.write("<span class='container'>");
        html.write("Generated on ");
        html.write(connectTime);
        html.writeln("</span>");
    }

    public static String getDescription(Database db, Table table, String text, boolean hoverHelp) {
        StringBuilder description = new StringBuilder();
        if (table != null) {
            if (table.isView()) {
                description.append("View ");
            } else {
                description.append("Table ");
            }
        }
        if (hoverHelp) {
            description.append("<span title='Database'>");
        }
        description.append(db.getName());
        if (hoverHelp) {
            description.append("</span>");
        }
        if (db.getSchema() != null) {
            description.append('.');
            if (hoverHelp) {
                description.append("<span title='Schema'>");
            }
            description.append(db.getSchema());
            if (hoverHelp) {
                description.append("</span>");
            }
        } else if (db.getCatalog() != null) {
            description.append('.');
            if (hoverHelp) {
                description.append("<span title='Catalog'>");
            }
            description.append(db.getCatalog());
            if (hoverHelp) {
                description.append("</span>");
            }
        }
        if (table != null) {
            description.append('.');
            if (hoverHelp) {
                description.append("<span title='Table'>");
            }
            description.append(table.getName());
            if (hoverHelp) {
                description.append("</span>");
            }
        }
        if (text != null) {
            description.append(" - ");
            description.append(text);
        }

        return description.toString();
    }

    protected boolean sourceForgeLogoEnabled() {
        return Config.getInstance().isLogoEnabled();
    }

    protected void writeFooter(LineWriter html) throws IOException {
        html.writeln("</div>");
        if (isMetered) {
            html.writeln("<span style='float: right;' title='This link is only on the SchemaSpy sample pages'>");
            html.writeln("<!-- Site Meter -->");
            html.writeln("<script type='text/javascript' src='http://s28.sitemeter.com/js/counter.js?site=s28schemaspy'>");
            html.writeln("</script>");
            html.writeln("<noscript>");
            html.writeln("<a href='http://s28.sitemeter.com/stats.asp?site=s28schemaspy' target='_top'>");
            html.writeln("<img src='http://s28.sitemeter.com/meter.asp?site=s28schemaspy' alt='Site Meter' border='0'/></a>");
            html.writeln("</noscript>");
            html.writeln("<!-- Copyright (c)2006 Site Meter -->");
            html.writeln("</span>");
        }
        html.writeln("</body>");
        html.writeln("</html>");
    }

    /**
     * Override if your output doesn't live in the root directory.
     * If non blank must end with a trailing slash.
     *
     * @return String
     */
    protected String getPathToRoot() {
        return "";
    }

    /**
     * Override and return true if you're the main index page.
     *
     * @return boolean
     */
    protected boolean isMainIndex() {
        return false;
    }

    /**
     * Override and return true if you're the relationships page.
     *
     * @return boolean
     */
    protected boolean isRelationshipsPage() {
        return false;
    }

    /**
     * Override and return true if you're the orphans page.
     *
     * @return boolean
     */
    protected boolean isOrphansPage() {
        return false;
    }

    /**
     * Override and return true if you're the constraints page
     *
     * @return boolean
     */
    protected boolean isConstraintsPage() {
        return false;
    }

    /**
     * Override and return true if you're the anomalies page
     *
     * @return boolean
     */
    protected boolean isAnomaliesPage() {
        return false;
    }

    /**
     * Override and return true if you're the columns page
     *
     * @return boolean
     */
    protected boolean isColumnsPage() {
        return false;
    }

    /**
     * Override and return true if you're the routines page
     *
     * @return boolean
     */
    protected boolean isRoutinesPage() {
        return false;
    }

    /**
     * Encode the specified string
     *
     * @param string
     * @return
     */
    static String urlEncode(String string) {
        try {
            return URLEncoder.encode(string, Config.DOT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            Logger logger = Logger.getLogger(HtmlFormatter.class.getName());
            logger.info("Error trying to urlEncode string [" + string + "] with encoding [" + Config.DOT_CHARSET + "]");
            return string;
        }
    }
}
