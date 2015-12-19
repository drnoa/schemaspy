package net.sourceforge.schemaspy.integrationtest;

import net.sourceforge.schemaspy.Config;
import net.sourceforge.schemaspy.DbAnalyzer;
import net.sourceforge.schemaspy.SchemaAnalyzer;
import net.sourceforge.schemaspy.model.Database;
import net.sourceforge.schemaspy.model.Table;
import org.h2.tools.DeleteDbFiles;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 */
public class IntegrationTest {

    private static final String GENERATED_RESULT_BASE_FILE_PATH = "./target/output";
    private static final String EXPECTED_RESULT_BASE_FILE_PATH = "./src/test/resources/integrationtest/expectedResult";


    @Before
    public void setUp() throws ClassNotFoundException, SQLException {
        // remove old database Files
        DeleteDbFiles.execute("./src/test/resources/testdb", "testdb", true);

        Class.forName("org.h2.Driver");
        Connection conn = DriverManager.getConnection("jdbc:h2:file:./src/test/resources/testdb/testdb");
        Statement stat = conn.createStatement();
        // create schema and import base data
        stat.execute("runscript from 'src/test/resources/inittestdb.sql'");


        stat.close();
        conn.close();
    }

    @Test
    public void test() throws IOException, SQLException {
        // given
        SchemaAnalyzer analyzer = new SchemaAnalyzer();
        String[] argv = new String[14];
        argv[0] = "-t";
        argv[1] = "h2";
        argv[2] = "-host";
        argv[3] = "localhost";
        argv[4] = "-db";
        argv[5] = "./src/test/resources/testdb/testdb";
        argv[6] = "-u";
        argv[7] = "";
        argv[8] = "-p";
        argv[9] = "";
        argv[10] = "-o";
        argv[11] = "./target/output";
        argv[12] = "-meta";
        argv[13] = "./src/test/resources/test.meta.xml";

        // when
        Database result = analyzer.analyze(new Config(argv));

        // then
        assertNotNull(result);
        assertEquals(9, result.getTables().size());
        assertEquals(1, result.getViews().size());
        assertEquals(5, getTableByName(result.getTables(), "AUTHOR").getColumns().size());
        assertEquals("VARCHAR", getTableByName(result.getTables(), "AUTHOR").getColumn("FIRSTNAME").getTypeName());
        assertEquals(80, getTableByName(result.getTables(), "BOOK").getNumRows());

        assertEquals(1, getTableByName(result.getTables(), "AUTHOR").getAdditionalInfo().size());
        assertEquals("http://google.ch", getTableByName(result.getTables(), "AUTHOR").getAdditionalInfo().iterator().next().getValue());
        assertEquals(1, getAmountOrphanTables(result.getTables()));
        assertEquals(10, DbAnalyzer.getForeignKeyConstraints(result.getTables()).size());

        assertFile("deletionOrder.txt");
        assertFile("insertionOrder.txt");

        // TODO also check generated Files under ./src/test/resources/testdb/output

    }

    private void assertFile(String file) throws IOException {
        String expected = readFile(EXPECTED_RESULT_BASE_FILE_PATH + "/" + file);
        String generated = readFile(GENERATED_RESULT_BASE_FILE_PATH + "/" + file);
        assertNotNull(expected);
        assertNotEquals("", expected);
        assertEquals(expected, generated);
    }

    private Table getTableByName(Collection<Table> tables, String name){
        for (Table table : tables) {
            if(name.equalsIgnoreCase(table.getName())){
                return table;
            }
        }
        return null;
    }

    private int getAmountOrphanTables(Collection<Table> tables){
        int amountOrphan = 0;
        for (Table table : tables) {
            if(table.isOrphan(true)){
                amountOrphan ++;
            }
        }
        return amountOrphan;
    }

    private String readFile(String filename) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(filename));

        return new String(bytes, Charset.forName("UTF-8"));
    }
}
