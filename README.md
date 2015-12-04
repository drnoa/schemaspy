# Description
SchemaSpy analyzes database metadata to reverse engineer dynamic Entity Relationship (ER) diagrams. It works with just about any JDBC-compliant database (Oracle/MySQL/DB2/SQL Server/PostgreSQL/Sybase/etc) and can identify Ruby on Rails relationships.

# About schemaspy
Schemaspy is orginally written by johncurrier (https://sites.google.com/site/johncurrier) and can be found on Sourceforge.
http://sourceforge.net/projects/schemaspy/
http://schemaspy.sourceforge.net/

During a project we startet to extend schemaspy by freemaker templates and we like to share this work here.

# Basic Idea

The extended Version of schemaspy allows the user to define the layout of the database analysis report by [Freemarker](http://freemarker.incubator.apache.org/) templates. Previously the layout was completely static and there was no way to set define a custom layout for example a company logo during the generation phase of the reports.

All customisation posibilities of the HTML reports were in post processing via a external script or tool.

Additional information can be added to the meta xml to provide properties and facts during the generation of the reports.

# Howto

## Run schemaspy 

```
java -jar schemaSpy_<verion>.jar -t <driver> -host <HOST> -port <PORT> -db <DB> -u <USER> -p <pw> -s <SCHEMA> -o ./output/ -dp <driver>.jar
```


## templating

By setting the parameter
```
-template ./tempaltefolder/
```

the defined templates can be overwritten with custom templates.

The default templates are available under and work as fallback if the requested template is not available in the passed templatefolder (-template)
```
src/main/resources/templates
```

## Add additional Metadata Meta.xml

The parameter
```
-meta 
```
allows to pass a meta.xml file into the generation. This file can contain additional information.

```
<additionalInfos>
    <additionalInfo>
       <additionalKey>additionalInfoLink</additionalKey>
       <additionalInfoValue>http://example.org/info.pdf</additionalInfoValue>
   </additionalInfo>
</additionalInfos>
```

Those additionalInfos can be defined for each table:
```
<schemaMeta xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:noNamespaceSchemaLocation="http://schemaspy.sourceforge.net/xmlschema/2011/02/05/schemaspy.meta.xsd">
   <comments>
      MY DB
   </comments>
<tables>
      <table name="TABLEXY" comments="">
         <additionalInfos>
            <additionalInfo>
               <additionalKey>additionalInfoLink</additionalKey>
               <additionalInfoValue>http://example.org/info.pdf</additionalInfoValue>
            </additionalInfo>
         </additionalInfos>
      </table>
   </tables>
</schemaMeta>
```

The additionalInfos can then be accessed in the template by the method table.getAdditionalInfo('additionalKey'):
```
<#assign additionalLink = table.getAdditionalInfo('additionalInfoLink')!>
<#if additionalLink?has_content>
      <a target="new" href="${additionalLink}">additionalLink</a>
</#if>
```

# License
GNU Library or Lesser General Public License version 2.0 (LGPLv2)
