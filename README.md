# Description
SchemaSpy analyzes database metadata to reverse engineer dynamic Entity Relationship (ER) diagrams. It works with just about any JDBC-compliant database (Oracle/MySQL/DB2/SQL Server/PostgreSQL/Sybase/etc) and can identify Ruby on Rails relationships.

# About schemaspy
Schemaspy is orginally written by johncurrier (https://sites.google.com/site/johncurrier) and can be found on Sourceforge.
http://sourceforge.net/projects/schemaspy/
http://schemaspy.sourceforge.net/

During a project we startet to extend schemaspy by freemaker templates and we like to share this work here.

# License
GNU Library or Lesser General Public License version 2.0 (LGPLv2)


# SchemaSpy Release Notes
##     actual work in progress
        Added freemaker templates (this is still very work in progress).


##     5.0.0 - 08/16/2010
        Added SQL to query view details for MySQL databases.
        Implemented feature request 1600035 - Exclusion regex I don't like the inconsistency of the command line that this introduces, but there's a new -I option that takes a regular expression of tables to excludes from the analysis. This is the inverse of -i which specifies which tables to include. The two can be used together
        Thanks to Erik Meitner for the suggestion.
        Resolved bug 1537126 - XML output fails with binary columns
        It took a while but I think I finally came up with a regular expression that'll determine if the default value is representable in XML in Unicode:
        ^[ -\uD7FF\uE000-\uFFFD\p{L}\p{M}\p{Z}\p{S}\p{N}\p{P}]*$
        Resolved bug 2218941 - No diagrams when schema only has implied relationships
        Thanks to Steven Buschman for identifying and reproducing the problem and José E. Giménez for directing me to the solution.
        Resolved bug 2433403 - Fail equal token (\=) in -desc option
        Thanks to José E. Giménez for the patch that resolved this.
        Resolved bug 2660020 - Table comments are not retrieved in SQL Server 2000
        Thanks to Frank Biedermann for the SQL that implemented this.
        Resolved an issue where an InvalidConfigurationException was thrown when using Sybase.
        Resolved bug 2820802 - ConcurrentModificationException
        This would sometimes occur while determining referential integrity-based insertion/deletion ordering with schemas that have recursive foreign key relationships.
        Thanks to Joao Carlos Batalha for reporting the problem.
        Resolved bug 2827876 - Table name not showing in XML Representation
        Check constraints would replace table entries the generated XML.
        Thanks to TomM for reporting the problem.
        Implemented feature request 2828474 - Support Informix without Schemas.
        Added a new -noschema option for database drivers that claim that they support schemas but really don't (e.g. older versions of Informix).
        Thanks to Joao Batalha for reporting the problem.
        There were scenarios where SchemaSpy had difficultly resolving the dot executable though the PATH environment variable. Added a -gv option to optionally remove the dependency on having your PATH include Graphviz's bin directory.
        Resolved bug 2393049 - SchemaSpy fails to access metadata on z/OS-based DB2 systems
        SchemaSpy now more fully supports DB2 on z/OS-based systems by specifying -type db2zos.
        Thanks to Christian Riedel for providing the z/OS-specific configuration file.
        Resolved bug 2942336 - Related table names missing from table details
        Implemented feature request 2943959 - Add deletion rule details.
        Now shows details about foreign key constraint deletion rules: cascade on delete, null on delete and restrict delete.
        Implemented feature request 2949120 - Expose object model.
        SchemaAnalyzer.analyze() now returns the gathered database metadata. This would typically be used with HTML-generation disabled. Eventually all error reporting would occur in the calling class, but that hasn't been implemented yet nor has the ability to generate no files at all.
        Thanks to Dominique De Vito for the suggestion.
        Added the ability to specify the SQL used to query basic details of tables and views. This same query can optionally return row counts or view definitions, possibly negating the need for additional trips to the database.
        Thanks to Jason Friedman for the suggestion.
        Partially implemented feature request 2712121 - Formatting SQL output.
        SchemaSpy still does no real formatting the View SQL, but it now detects that the SQL is already formatted and presents it in that form. I don't know if the databases typically retain this formatting information or not (MySQL does not).
        Thanks to Reiner Kräutle for the suggestion.
        Added the ability to specify a custom SQL formatter via the -sqlFormatter option. This formatter must implement the SqlFormatter interface and return the SQL formatted appropriately in HTML. Implementing a "good" formatter is basically a project in itself and beyond the scope of SchemaSpy, so plug in your own if desired. Use -dp to make the specified class visible to SchemaSpy's class loader.
        Now treats DB2 Materialized Query Tables (MQTs) as views per Jonas Söderström's suggestion.
        Resolved bug 2996883 - Misleading warning messages when invalid regular expressions are specified.
        Thanks to David Corlette for reporting the problem.
        Implemented feature request 2999566 - Provide verbose details of program flow.
        Added a new -logLevel option that lets you specify how verbose the logging output should be.
        The levels in descending order are:
            severe (highest - least detail)
            warning (default)
            info
            config
            fine
            finer
            finest (lowest - most detail)
        Resolved bug 3026251 - Arrowtail decoration not appearing in diagrams.
        There were minor changes made to the Graphviz dot language dealing with edge rendering that now require explicit settings.
        Thanks to Keith Clarke for reporting the problem and researching the solution.
        Implemented feature request 3021923 - Enter passwords from within SchemaSpy.
        Added a new -pfp (prompt for password) option that causes SchemaSpy to prompt the user for a password. If running in a Java6 or later environment it uses the JVM-provided classes (via reflection), while with older JVMs it attempts to emulate the same behavior.
        Thanks to st0w for the suggestion.
        Implemented feature request 3041957 - Optionally exclude database views.
        Added a new -noviews option that causes SchemaSpy exclude all views from its analysis.
        Thanks to Mattias Melin for the suggestion.
        On the tables tab you can now view tables and views separately as well as hide comments.
        Thanks to jpummill for the idea.
        The columns page with extremely large databases (e.g. 20,000 columns) was so massive that browsers were unable to render it. The new columns page has been trimmed down to help it load. There was also a performance issue where the visibility state of column comments were out of sync with the css. That resulted in the page attempting to hide 20,000+ columns on load.
        Resolved bug 3041947 - NullPointerException when unable to resolve column type.
        SQL Anywhere returns null for view column types, resulting in SchemaSpy to throw exceptions. Now those view columns will show a type of "unknown". Thanks to Mattias Melin for reporting the problem and researching the solution.
        Added two new optional SQL queries for view comments: selectViewCommentsSql and selectViewColumnCommentsSql.
        These are optional and expected to return view and view column comments when selectViewsSql, selectTableCommentsSql and selectColumnCommentsSql don't.
        Thanks to David Corlette for the idea.
##     4.1.1 - 10/20/2008
        Resolved bug 2182700 - Exception when extends directive has trailing space
        Resolved bug 2182705 - Null ID on DB2 views
##     4.1.0 - 10/17/2008
        Implemented feature request 1548661 - Add support for Ruby on Rails databases.
        Ruby on Rails databases use an "interesting" naming convention when mapping for tables (plural), foreign keys (singular form of referenced table name suffixed by _id) and primary keys (always id). This release introduces the -ror (Ruby on Rails) parameter that tells SchemaSpy to look for and render these types of relationships.
        Thanks to John Danilson for the suggestion.
        Resolved bug 2175509 - Incorrect ER notation.
        SchemaSpy was using incorrect entity relationship diagram notations in the cardinality of the foreign key side of relationships. In many cases it showed 'one' or 'one or more' when it should have shown 'zero or more'. Thanks to Dan Zingaro for noticing the incorrectness and for helping to resolve the issue.
        Added a new import directive to database configuration files to be able to reuse complex SQL (or other settings) from configurations not in the normal inheritance tree.
        Added support for MS SQL Server 2005 with jTDS driver, including column comment retrieval for all of the MS SQL databases.
        Thanks to Ernest Zapata for the configuration files.
        Implemented feature request 2175489 - Allow connection properties without file.
        Can now specify connection properties directly on the command line without having to put them in a file.
        Thanks to Ernest Zapata for the suggestion and implementation.
        Implemented feature request 2175499 - Single Sign-On Support.
        With the introduction of the -sso option running SchemaSpy against a database that supports single sign-on now no longer requires the user name to be specified on the command line. Thanks to Ernest Zapata for the suggestion and implementation.
        Resolved bug 2126345 - Unable to extract number of rows.
        Now doesn't report intermediate problems while attempting to extract number of rows when a fallback to a more basic approach is required.
##     4.0.0 - 09/19/2008
        One major enhancement of this release is the implementation of feature request 1996467 - Define Non-Foreign Key relationships.
        There have been several requests related to this topic.
        SchemaSpy now supports defining supplemental schema metadata in XML. This includes defining foreign keys, remote tables, schema/table/column comments and column exclusions.
        Implemented feature request 1643953 - Provide way to suppress columns in images.
        This feature request resulted into a re-thinking of the compact form of the overview relationships diagram. The compact form now displays only primary and foreign key columns while the non-compact form displays all columns. This is also used to indicate degrees of separation for the detailed diagrams
        Thanks to Paul B Callahan for the suggestion.
        jQuery was presented by Scott Ryan at a recent Colorado Springs Open Source Software meeting. One of the big advantages of jQuery is that it lets you separate behavior from the structure of your web pages. There are tons of other benefits to jQuery, so SchemaSpy now uses it under the MIT license.
        Added support for Derby (JavaDB) databases, both embedded and network.
        Added support for SQL Server with jTDS driver.
        Added support for SQL Server 2005.
        Added support for MaxDB.
        Renamed -cp option to -dp (driver path) to reduce confusion/conflict with the java executable's -cp option. -cp is deprecated but still valid.
        Implemented feature request 1593736 - Graph generation performance opportunity.
        This cut the number of calls to the dot executable in half, significantly reducing the time required to generate diagrams.
        Implemented feature request 1609408 - Better Unicode support to resolve some table name to URL mapping issues that show up with Japanese table names.
        Implemented feature request 1887490 - Get rowcount from metadata.
        Added MySQL-specific selectRowCountSql implementation.
        Added Oracle-specific selectRowCountSql implementation provided by Mikheil Kapanadze.
        Implemented feature request 1598195 - Sybase replacement for "select count(*) from".
        Added ability to provide a database-specific selectRowCountSql to query the number of rows in a column. For large Sybase databases this should significantly reduce processing time.
        Thanks to Klaus-Martin Hansche for the suggestion.
        Implemented feature request 1598113 - Request for font specification parameter.
        Added -font and -fontsize options for generated diagrams.
        Thanks to Tim Walters for the suggestion.
        Implemented feature request 1444337 - Document cross-schema FK with -all option.
        Implemented feature request 1562942 - Refactor to allow for dependency injection.
        Separating configuration from processing simplifies integration with Maven, Maven2, Ant, etc.
        Thanks to Tim P for the suggestion.
        Implemented feature request 1601046 - Remove System.exit calls to allow for easier integration with build tools.
        Implemented feature request 1526669 - Don't display row counts.
        Thanks to Gonzalo Mena-Mendoza for the suggestion.
        Implemented patch 2018566 - SQL Server column comment SQL.
        Resolved bug 2080062 - NullPointerException with Oracle 11g and some Advanced Queuing Tables
        Resolved bug 1571711 - Fails to correctly identify dot version on MacOS-X.
        Thanks to Jean-François Veillette for providing the fix.
        Resolved bug 1620052 - Documenting foreign keys referencing anther schema.
        Resolved bug 1687569 - Minor HTML validation errors.
        Resolved bug 1689794 - Case-sensitive table names handled incorrectly.
        Resolved bug 1841219 - Exception when running against DB2 UDB for AS/400.
        Resolved bug 1947528 - Table Column regular expression not excluded.
        Resolved bug 2040582 - Oracle - can't add Foreign Keys from tables in other schema.
        Resolved bug 2116863 - SQLException thrown if no schema matches.
##     3.1.1 - 12/18/2006
        Resolved bug 1602135 - Exception using dot version 2.9.
        Resolved bug 1571711 - Fail to use precompiled version of dot on MacOS-X.
        Resolved bug 1597609 - Quoting and Sybase ASE.
        Resolved bug 1589939 - Incorrect Sybase SQL syntax by extract the number of rows.
##     3.1.0 - 10/05/2006
        Implemented feature request 1537790 - Column name sort.
        The Columns page is now sortable by column, table, type, size, nullability, auto increment, default value, children or parents.
        Thanks to John Danilson for the suggestion.
        Implemented feature request 1479956 - Add description.
        Added a new option (-desc) to provide a description of the schema that is displayed on the non-detail pages.
        Thanks to Amihay Gonen for the suggestion.
        Implemented feature request 1570467 - No table comments option.
        Added -notablecomments option for databases like MySQL that stuff unrelated data where comments belong.
        Resolved bug 1484462 - SchemaSpy_3.0.0 and Oracle 10g problems.
        Now ignores tables associated with Oracle's recycle bin.
        Resolved bug 1558067 - OutOfMemoryError on Linux while trying to determine insertion order with recursive constraints. Thanks to Remke Rutgers for his solution.
        Resolved bug 1530945 - Incorrect variables size in DB2.
        Now uses the value from BUFFER_LENGTH if it's available.
        Resolved bug 1487385 - XML file is not a valid UTF8.
        Bernard D'Havé provided the code to resolve this one.
        Resolved bug 1507335 - Views are not filtered with the regular expressions.
        Resolved bug 1502357 - Blank Relationships tab.
        Resolved bug 1530965 - columns.html table output issues with DB2.
        Resolved bug 1487385 - XML file is not valid UTF8.
        Resolved bug 1571077 - Long comments don't wrap.
##     3.0.0 - 05/07/2006
        SchemaSpy now generates an XML representation of the database's schema.
        New -all option analyzes all schemas in the database with one command.
        Now displays database table and row comments.
        Comments are normally hidden by default, but a new option reverses that behavior.
        Added a new option to allow HTML in comments.
        You can now specify a subset of tables/views to include in the analysis with the new -i <regularExpression> option.
        Now supports Firebird.
        Now supports Microsoft SQL Server 2005 thanks to Craig Boland providing a configured mssql05.properties file.
        Now supports Informix thanks to Tom Conlin providing a configured informix.properties file.
        A new option (-maxdet) was added to help keep extremely large schemas from getting out of control.
        Some users needed to specify the character set used to connect to their database, so a new option (-connprops) was added.
        The relationships page will now always be available, even if there are no relationships or just implied relationships.
        Tables that contain only implied relationships will now show them by default.
        Resolved bug 1440650 - Oracle 10g flashback tables with bizarre illegal names cause SchemaSpy to crash.
        Added support for table names with embedded spaces (feature request 1445745) and reserved words (bug 1449203).
        Resolved bug 1481396 - NumberFormatException in view.DotFormatter.writeHeader. Now includes diagnostics to help identify Graphviz configuration issues.
        Cleaned-up the formatting of table details.
##     2.1.2 - 02/28/2006
        Now generates a relatively useful relationships page even if dot fails to build the larger (and more likely to break Graphviz's dot) images.
        Lots of minor code cleanup after cranking up Eclipse's warning levels.
##     2.1.1 - 02/05/2006
        Added a more descriptive error message when the correct version of Graphviz dot isn't installed.
        Some people were having problems by passing -cp to java and not to SchemaSpy. The classpath-related error messages now clearly specify where -cp belongs.
        Revamped the classpath-related error messages to include the classpath entries that weren't valid. This should make it easier to track down JDBC driver issues.
        Updated MySQL's configuration file to point to the latest JDBC drivers. Also gave the URL for the Connector/J drivers.
##     2.1.0 - 11/23/2005
        SchemaSpy can now analyze all of the schemas of a database with one command. This feature should allow a DBA to do a bulk analysis, documenting every user schema with one command.
        Resolution of bug 1350392 - NullPointer if FK table not in analyzed schema.
        Was missing the border along the top edge of the page.
        Luke Hutteman found a threading issue while reviewing portions of the code. Thanks Luke!
        Resolution of bug 1351460 - Tab background color mis-named.
        Resolution of bug 1351488 - Relationships tab displayed when no relationships exist.
        The layout of the tab area is now significantly cleaner.
        Added -rankdirbug option to deal with the dot bugs that invariably result from using a non-standard rankdir in .dot files. This option should not be used unless absolutely necessary.
        Refactored many of the 'formatters' to become 'pages'.
##     2.0.0 - 11/06/2005
        SchemaSpy now uses a tabbed layout that results in a significantly cleaner page.
        You now have the ability to explicitly exclude columns from the relationship analysis using a regular expression notation. This keeps those pesky tables that are related to everything from severely cluttering your view of your schema.
        Added information in the graphs about how many 'parent' and 'child' tables are related to a given table. This gives a visual clue that there are more tables beyond the outskirts of what's currently visible.
        SchemaSpy now supports HSQLDB thanks to bension providing a hsqldb.properties configuration file.
        Added support for DB2 UDB Type 4 driver.
        The graphs are now clearer due to the use of a sans serif font.
        Resolution of bug 1340880 - accent characters cause dot to fail. SchemaSpy now generates the .dot files using the UTF-8 character set, which is what the dot program expects.
##     1.7.1 - 10/20/2005
        Added support for DB2's Net (Type 4) driver.
        Resolution of bug 1325650. Was having more problems with extracting information on Oracle indexes. Due to an Oracle driver bug (Oracle bug #2686037) I inverted how the selectIndexesSql gets evaluated and significantly optimized the SQL. Thanks to Andrea for helping to figure all of this out.
        Added details of the -cp option to the diagnostic messages when unable to load database drivers.
        Fixed some minor cosmetic problems.
##     1.7.0 - 10/11/2005
        SchemaSpy now supports PostgreSQL thanks to Tomi Ollila providing a pgsql.properties.
        Added a new page that lists all of the columns (with details) in the schema. The intent of this page is to make it easier to find inconsistencies in the schema as well as make it easier to do things like search for all instances of a certain type of column (e.g. 'timestamp').
        Resolution of bug 1283622. A user from the Slovak Republic ran into problems where dot was unable to deal with some of the characters that are in this character set. Now it should handle them appropriately.
        Refactored how the links on each page get generated. It was pretty much cut and paste before. Now it's consolidated in HtmlFormatter.writeTableOfContents() so it's much more consistent and easier to change.
        Implemented feature request 1312220: Modified build.xml to include an appropriate level of debug information (source files and line numbers).
##     1.6.1 - 09/16/2005
        Fixed Utilities page to not have the 'Generated by SchemaSpy' label on each table.
        Yan-Fa Li figured out what was causing dot problems on Unix: quotes around filenames.
        Updated the legend to somewhat match the new IE diagram representation.
##     1.6.0 - 09/11/2005
        Now displays graphical relationships using Information Engineering (IE) notation. This should make it easier to understand the different types of relationships in the schema.
        Beefed-up the code that detects if dot is being executed correctly. Some users were getting output that implied that dot was working, but no images were created.
        Added version information to .dot files to help debugging.
        Anomalies page had some anomalies.
##     1.5.2 - 09/03/2005
        Implemented feature request 1280949: Many of DB2's error messages don't give table, column or constraint names but instead give their IDs. As a developer it's a pain to have to try to translate these IDs into something useful. SchemaSpy previously gave these details before I converted to the JDBC metadata approach, which doesn't return these IDs. It now displays these IDs for database types that support it and that have the appropriate SQL defined in their properties files.
##     1.5.0 - 08/30/2005
        Now displays two versions of the main relationships graph: a smaller 'compact' version and a large full-size version. The compact version should make it easier to get a 'big picture' view of the relationships.
        Now takes advantage of the GraphVis dot 2.6 ability to point to the East/West center of a node if running dot 2.6 or higher.
        Figured out a way to have the graph point to the appropriate portion of the main table in the table detail pages.
        Introduced a new anomaly: Columns whose default value is the word 'NULL' or 'null', but the SQL NULL value may have been intended.
        Realized that index sort order relates to the column, not the index. Not sure what I was thinking.
        Added some instructions on the format of connectionSpec in the .properties files.


