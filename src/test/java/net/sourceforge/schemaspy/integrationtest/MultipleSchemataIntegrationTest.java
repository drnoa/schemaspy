package net.sourceforge.schemaspy.integrationtest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.h2.tools.DeleteDbFiles;
import org.junit.Before;
import org.junit.Test;

import net.sourceforge.schemaspy.Config;
import net.sourceforge.schemaspy.SchemaAnalyzer;

/**
 */
public class MultipleSchemataIntegrationTest {

    private static final String GENERATED_RESULT_BASE_FILE_PATH = "./target/outputMultiple";
    private static final String EXPECTED_RESULT_BASE_FILE_PATH = "./src/test/resources/integrationtest/expectedResultMultiple";


    @Before
    public void setUp() throws ClassNotFoundException, SQLException {
        // remove old database Files
        DeleteDbFiles.execute("./src/test/resources/testdb", "testdbmultiple", true);

        Class.forName("org.h2.Driver");
        Connection conn = DriverManager.getConnection("jdbc:h2:file:./src/test/resources/testdb/testdbmultiple");
        Statement stat = conn.createStatement();
        // create schema and import base data
        stat.execute("runscript from 'src/test/resources/inittestdb.sql'");
        stat.execute("runscript from 'src/test/resources/inittestdbSecondSchema.sql'");


        stat.close();
        conn.close();
    }

    @Test
    public void test() throws IOException, SQLException {
        // given
        SchemaAnalyzer analyzer = new SchemaAnalyzer();
        String[] argv = new String[16];
        argv[0] = "-t";
        argv[1] = "h2";
        argv[2] = "-host";
        argv[3] = "localhost";
        argv[4] = "-db";
        argv[5] = "./src/test/resources/testdb/testdbmultiple";
        argv[6] = "-u";
        argv[7] = "";
        argv[8] = "-p";
        argv[9] = "";
        argv[10] = "-o";
        argv[11] = GENERATED_RESULT_BASE_FILE_PATH;
        argv[12] = "-meta";
        argv[13] = "./src/test/resources/test.meta.xml";
        argv[14] = "-schemas";
        argv[15] = "PUBLIC,SECONDSCHEMA";

        // when
        analyzer.analyze(new Config(argv));

        // then
        assertFile("PUBLIC/deletionOrder.txt");
        assertFile("PUBLIC/insertionOrder.txt");
        assertFile("secondSchema/deletionOrder.txt");
        assertFile("secondSchema/insertionOrder.txt");
    }

    private void assertFile(String file) throws IOException {
        String expected = readFile(EXPECTED_RESULT_BASE_FILE_PATH + "/" + file);
        String generated = readFile(GENERATED_RESULT_BASE_FILE_PATH + "/" + file);
        assertNotNull(expected);
        assertNotEquals("", expected);
        assertEquals(expected, generated);
    }

    private String readFile(String filename) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(filename));

        return new String(bytes, Charset.forName("UTF-8"));
    }
}
